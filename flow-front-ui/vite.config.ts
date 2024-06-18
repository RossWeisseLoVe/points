import { defineApplicationConfig } from '@vben/vite-config';

export default defineApplicationConfig({
  overrides: {
    optimizeDeps: {
      include: [
        'echarts/core',
        'echarts/charts',
        'echarts/components',
        'echarts/renderers',
        'qrcode',
        '@iconify/iconify',
        'ant-design-vue/es/locale/zh_CN',
        'ant-design-vue/es/locale/en_US',
      ],
    },
    server: {
      proxy: {
        '/flow-front-api': {
          target: 'http://localhost:8990',
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/flow-front-api`), ''),
          // only https
          // secure: false
        },
        '/upload': {
          target: 'http://localhost:8990/front/flow/uploadFile',
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/upload`), ''),
        },
      },
      warmup: {
        clientFiles: ['./index.html', './src/{views,components}/*'],
      },
    },
    build: {
      emptyOutDir: true,
      outDir:'../flow-front-rest/src/main/resources/static/'
    },
  },
});
