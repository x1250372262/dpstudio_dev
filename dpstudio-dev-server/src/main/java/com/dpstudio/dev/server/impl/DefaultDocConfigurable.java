package com.dpstudio.dev.server.impl;

import com.dpstudio.dev.server.IServer;
import com.dpstudio.dev.server.IServerConfig;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurable;

public final class DefaultDocConfigurable extends DefaultModuleConfigurable {

    public static Builder builder() {
        return new Builder();
    }

    private DefaultDocConfigurable() {
        super(IServer.MODULE_NAME);
    }

    public static final class Builder {

        private final DefaultDocConfigurable configurable = new DefaultDocConfigurable();

        private Builder() {
        }


        /**
         * tomcat配置
         */

        public Builder port(int port) {
            configurable.addConfig(IServerConfig.PORT, String.valueOf(port));
            return this;
        }

        public Builder type(String type) {
            configurable.addConfig(IServerConfig.TYPE, type);
            return this;
        }

        public Builder workName(String workName) {
            configurable.addConfig(IServerConfig.WORK_NAME, workName);
            return this;
        }

        public Builder hostName(String hostName) {
            configurable.addConfig(IServerConfig.HOST_NAME, hostName);
            return this;
        }

        /**
         * tomcat配置
         */
        public Builder maxThreads(int maxThreads) {
            configurable.addConfig(IServerConfig.MAX_THREADS, String.valueOf(maxThreads));
            return this;
        }

        public Builder minSpareThreads(int minSpareThreads) {
            configurable.addConfig(IServerConfig.MIN_SPARE_THREADS, String.valueOf(minSpareThreads));
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            configurable.addConfig(IServerConfig.CONNECTION_TIMEOUT, String.valueOf(connectionTimeout));
            return this;
        }

        public Builder maxConnections(int maxConnections) {
            configurable.addConfig(IServerConfig.MAX_CONNECTIONS, String.valueOf(maxConnections));
            return this;
        }

        public Builder acceptCount(int acceptCount) {
            configurable.addConfig(IServerConfig.ACCEPT_COUNT, String.valueOf(acceptCount));
            return this;
        }

        public Builder shutDownPort(int shutDownPort) {
            configurable.addConfig(IServerConfig.SHUTDOWN_PORT, String.valueOf(shutDownPort));
            return this;
        }

        public Builder webProject(boolean webProject) {
            configurable.addConfig(IServerConfig.IS_WEB, String.valueOf(webProject));
            return this;
        }

        public Builder workHome(String workHome) {
            configurable.addConfig(IServerConfig.WORK_HOME, workHome);
            return this;
        }

        public IModuleConfigurer build() {
            return configurable.toModuleConfigurer();
        }
    }
}