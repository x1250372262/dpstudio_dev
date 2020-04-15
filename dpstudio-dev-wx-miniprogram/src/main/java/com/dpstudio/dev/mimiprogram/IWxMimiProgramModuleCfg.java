package com.dpstudio.dev.mimiprogram;


/**
 * @author 徐建鹏
 * @Date 2020.01.09.
 * @Time: 16:30.
 * @Description: rpc模块参数
 */
public interface IWxMimiProgramModuleCfg {


    String APP_ID = "app_id";

    String APP_SECRET = "app_secret";

    //数据处理实现
    String DATA_HANDLER_CLASS = "data_handler_class";
    //默认实现是否存储到数据库 默认flase
    String DATA_HANDLER_DEFAULT_TYPE = "data_handler_default_by_databases;";

    boolean defaultHandlerByDatabase();

    IWxMimiProgramHandler handler();

    String appId();

    String appSecret();

}
