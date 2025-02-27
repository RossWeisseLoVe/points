package com.dragon.flow.config.modeler;

import lombok.Data;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

import java.util.Map;

@Data
public class KieUtilClass {

    private KieContainer kieContainer;

    private Map<String, Class<?>> classMap;

}
