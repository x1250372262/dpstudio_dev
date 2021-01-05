package com.dpstudio.dev.server.impl;

import com.dpstudio.dev.server.IServerConfig;
import net.ymate.platform.core.configuration.IConfigReader;
import net.ymate.platform.core.module.IModuleConfigurer;

public final class DefaultServerConfig implements IServerConfig {

    private static int port;

    private static String type;

    private static String workName;

    private static String hostName;

    /**
     * tomcat配置
     */
    private static int maxThreads;

    private static int minSpareThreads;

    private static int connectionTimeout;

    private static int maxConnections;

    private static int acceptCount;

    private static int shutDownPort;

    private static String workHome;

    private static boolean webProject;

    private boolean initialized;

    public static DefaultServerConfig defaultConfig() {
        return builder().build();
    }

    public static DefaultServerConfig create(IModuleConfigurer moduleConfigurer) {
        return new DefaultServerConfig(null, moduleConfigurer);
    }

    public static DefaultServerConfig create(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        return new DefaultServerConfig(mainClass, moduleConfigurer);
    }

    public static Builder builder() {
        return new Builder();
    }

    private DefaultServerConfig() {
    }

    private DefaultServerConfig(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        IConfigReader configReader = moduleConfigurer.getConfigReader();
        //
        DocConf confAnn = mainClass == null ? null : mainClass.getAnnotation(DocConf.class);
        enabled = configReader.getBoolean(ENABLED, confAnn == null || confAnn.enabled());
        mockEnabled = configReader.getBoolean(MOCK_ENABLED, confAnn == null || confAnn.mockEnabled());
        sdkEnabled = configReader.getBoolean(SDK_ENABLED, confAnn == null || confAnn.sdkEnabled());
        sourcePath = configReader.getString(SOURCE_PATH, confAnn != null ? confAnn.sourcePath() : "");
        fileName = configReader.getString(FILE_NAME, confAnn != null ? confAnn.fileName() : "");
        version = configReader.getString(VERSION, confAnn != null ? confAnn.version() : "1.0.0");
        title = configReader.getString(TITLE, confAnn != null ? confAnn.title() : "梦祥文档");
        host = configReader.getString(HOST, confAnn != null ? confAnn.host() : "");
    }

    @Override
    public void initialize(IDoc owner) throws Exception {
        if (!initialized) {
            if (enabled) {
                // TODO What to do?
            }
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isMockEnabled() {
        return mockEnabled;
    }

    @Override
    public boolean isSdkEnabled() {
        return sdkEnabled;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String sourcePath() {
        return sourcePath;
    }

    @Override
    public String fileName() {
        return fileName;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String host() {
        return host;
    }

    public void setTitle(String title) {
        if (!initialized) {
            this.title = title;
        }
    }

    public void setSourcePath(String sourcePath) {
        if (!initialized) {
            this.sourcePath = sourcePath;
        }
    }

    public void setFileName(String fileName) {
        if (!initialized) {
            this.fileName = fileName;
        }
    }

    public void setVersion(String version) {
        if (!initialized) {
            this.version = version;
        }
    }

    public void setHost(String host) {
        if (!initialized) {
            this.host = host;
        }
    }

    public void setEnabled(boolean enabled) {
        if (!initialized) {
            this.enabled = enabled;
        }
    }

    public void setSdkEnabled(boolean sdkEnabled) {
        if (!initialized) {
            this.sdkEnabled = sdkEnabled;
        }
    }
    public void setMockEnabled(boolean mockEnabled) {
        if (!initialized) {
            this.mockEnabled = mockEnabled;
        }
    }

    public static final class Builder {

        private final DefaultServerConfig config = new DefaultServerConfig();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            config.setEnabled(enabled);
            return this;
        }

        public Builder mockEnabled(boolean mockEnabled) {
            config.setMockEnabled(mockEnabled);
            return this;
        }

        public Builder sdkEnabled(boolean sdkEnabled) {
            config.setSdkEnabled(sdkEnabled);
            return this;
        }

        public Builder title(String title) {
            config.setTitle(title);
            return this;
        }

        public Builder sourcePath(String sourcePath) {
            config.setSourcePath(sourcePath);
            return this;
        }

        public Builder fileName(String fileName) {
            config.setFileName(fileName);
            return this;
        }

        public Builder version(String version) {
            config.setVersion(version);
            return this;
        }

        public Builder host(String host) {
            config.setHost(host);
            return this;
        }

        public DefaultServerConfig build() {
            return config;
        }
    }
}