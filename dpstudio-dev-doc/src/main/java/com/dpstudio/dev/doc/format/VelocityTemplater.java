package com.dpstudio.dev.doc.format;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author mengxiang
 * @Date 2018/08/09.
 * @Time: 14:00.
 * @Description:
 */
public class VelocityTemplater {

    public static final String ENCODING = "UTF-8";

    private static final VelocityEngine VELOCITY_ENGINE = new VelocityEngine();

    static {
        VELOCITY_ENGINE.setProperty(Velocity.RESOURCE_LOADER, "class");
        VELOCITY_ENGINE.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VELOCITY_ENGINE.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
        VELOCITY_ENGINE.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_CACHE, false);
        VELOCITY_ENGINE.init();
    }

    private String path;

    public VelocityTemplater(String path) {
        this.path = path;
    }

    public String parse(Map<String, Object> param) {

        Template template = VELOCITY_ENGINE.getTemplate(path, ENCODING);

        VelocityContext velocityContext = new VelocityContext();

        for (Map.Entry<String, Object> entry : param.entrySet()) {
            velocityContext.put(entry.getKey(), entry.getValue());
        }

        StringWriter sw = new StringWriter();
        template.merge(velocityContext, sw);

        return sw.toString();
    }

}
