package com.dpstudio.dev.mimiprogram.web;

import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.BinaryView;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/30.
 * @Time: 1:36 下午.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/wx/mimiprogram/qrcode")
public class WxMimiProgramQRCodeController {

    @RequestMapping("/show/{fileName}/{format}")
    public IView show(@VRequired(msg = "参数错误")
                      @PathVariable(value = "fileName") String fileName,
                      @PathVariable(value = "format") String format,
                      @RequestParam(defaultValue = "false") boolean attach) throws Exception {

        String qrPath = WxMimiProgram.get().getModuleCfg().qrCodePath();
        if (StringUtils.isBlank(qrPath)) {
            throw new NullPointerException("qrcode_path");
        }
        format = StringUtils.defaultIfBlank(format, WxMimiProgram.get().getModuleCfg().qrCodeFormat());
        File qrCodeFile = new File(qrPath, fileName.concat(".").concat(format));
        if (!qrCodeFile.exists() || qrCodeFile.length() == 0) {
            return View.httpStatusView(404, "文件未找到");
        }
        BinaryView binaryView = BinaryView.bind(qrCodeFile);
        if (attach) {
            binaryView.useAttachment(fileName.concat(".").concat(format));
        }
        return binaryView;

    }
}
