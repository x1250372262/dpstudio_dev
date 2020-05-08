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
    String DATA_HANDLER_DEFAULT_TYPE = "data_handler_default_by_databases";
    //二维码存放位置
    String QRCODE_PATH = "qrcode_path";
    //二维码图片后缀
    String QRCODE_FORMAT = "qrcode_format";
    //支付处理
    String PAY_HANDLER_CLASS = "pay_handler_class";

    boolean defaultHandlerByDatabase();

    IWxMimiProgramHandler iWxMimiProgramHandler();

    String appId();

    String appSecret();

    String qrCodePath();

    String qrCodeFormat();

    IWxMimiProgramPayHandler iWxMimiProgramPayHandler();

}
