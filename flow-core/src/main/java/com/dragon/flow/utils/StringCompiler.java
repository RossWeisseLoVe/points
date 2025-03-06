package com.dragon.flow.utils;

import javax.tools.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;

/////////////////////////////////////////////
// 步骤1：添加自定义编译器工具类（直接放在当前类中或独立文件）
/////////////////////////////////////////////
public class StringCompiler {
    public static Map<String, byte[]> compile(String fullClassName, String sourceCode) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("JDK编译器不可用，请使用JDK运行");
        }

        // 构建类路径（关键！需包含所有依赖）
        String classpath = buildClasspath();

        // 配置编译选项
        List<String> options = new ArrayList<>();
        options.add("-classpath");
        options.add(classpath);

        // 内存文件管理器
        MemoryFileManager manager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));

        // 包装源代码
        JavaFileObject source = new MemoryJavaFileObject(fullClassName, sourceCode);

        // 执行编译
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                manager,
                null,
                options,
                null,
                Collections.singletonList(source)
        );

        if (!task.call()) {
            throw new RuntimeException("编译失败：" + manager.getDiagnostics());
        }

        return manager.getClassBytes();
    }

    private static String buildClasspath() {
        // 获取当前项目的类路径（Maven/Gradle项目需要包含target/classes）
        String defaultClasspath = System.getProperty("java.class.path");
        // 如果依赖不在默认路径，手动添加（示例）：
        // defaultClasspath += ":/path/to/spring-core.jar";
        return defaultClasspath;
    }

    // 内存文件对象
    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        protected MemoryJavaFileObject(String className, String code) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    // 内存文件管理器
    private static class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private final Map<String, byte[]> classBytes = new HashMap<>();
        private final List<Diagnostic<?>> diagnostics = new ArrayList<>();

        protected MemoryFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            return new SimpleJavaFileObject(URI.create(className), kind) {
                @Override
                public OutputStream openOutputStream() {
                    return new ByteArrayOutputStream() {
                        @Override
                        public void close() throws IOException {
                            classBytes.put(className, toByteArray());
                        }
                    };
                }
            };
        }

        @Override
        public boolean isSameFile(FileObject a, FileObject b) {
            return true; // 简化处理
        }

        public Map<String, byte[]> getClassBytes() {
            return classBytes;
        }

        public String getDiagnostics() {
            return diagnostics.stream()
                    .map(d -> d.getMessage(Locale.ENGLISH))
                    .collect(Collectors.joining("\n"));
        }
    }
}