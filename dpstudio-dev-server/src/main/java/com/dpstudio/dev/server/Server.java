package com.dpstudio.dev.server;

import com.dpstudio.dev.server.impl.DefaultServerConfig;
import net.ymate.platform.core.*;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurer;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:27 下午.
 * @Description:
 */
public class Server  implements IModule, IServer{


    private static volatile IServer instance;

    private IApplication owner;

    private IServerConfig config;

    private boolean initialized;

    public static IServer get() {
        IServer inst = instance;
        if (inst == null) {
            synchronized (Server.class) {
                inst = instance;
                if (inst == null) {
                    instance = inst = YMP.get().getModuleManager().getModule(Server.class);
                }
            }
        }
        return inst;
    }

//    public static IServer get() {
//        String type = ServerConfig.getType();
//        switch (type) {
//            case "tomcat":
//                return new TomcatServer();
//            case "undertow":
//                return new UndertowServer();
//            default:
//                throw new IllegalStateException("不支持的服务类型: " + type);
//        }
//    }

    @Override
    public void startUp() {

    }

    @Override
    public IApplication getOwner() {
        return owner;
    }

    @Override
    public IServerConfig getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void close() throws Exception {
        if (initialized) {
            initialized = false;
            //
            if (config.isEnabled()) {
                // TODO What to do?
            }
            //
            config = null;
            owner = null;
        }
    }

    @Override
    public void initialize(IApplication owner) throws Exception {
        if (!initialized) {
            //
            YMP.showVersion("Initializing dpstudio-dev-server-${version}", new Version(1, 0, 0, Server.class, Version.VersionType.Alpha));
            //
            this.owner = owner;
            if (config == null) {
                IApplicationConfigureFactory configureFactory = owner.getConfigureFactory();
                if (configureFactory != null) {
                    IApplicationConfigurer configurer = configureFactory.getConfigurer();
                    IModuleConfigurer moduleConfigurer = configurer == null ? null : configurer.getModuleConfigurer(MODULE_NAME);
                    if (moduleConfigurer != null) {
                        config = DefaultServerConfig.create(configureFactory.getMainClass(), moduleConfigurer);
                    } else {
                        config = DefaultServerConfig.create(configureFactory.getMainClass(), DefaultModuleConfigurer.createEmpty(MODULE_NAME));
                    }
                }
                if (config == null) {
                    config = DefaultServerConfig.defaultConfig();
                }
            }
            if (!config.isInitialized()) {
                config.initialize(this);
            }
            if (config.isEnabled()) {
//                TagConverterRegister.me().register();
//                SourceFileManager.me().registerFile(config);
//                ApiHelper.me().createDoc(config);
            }
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
