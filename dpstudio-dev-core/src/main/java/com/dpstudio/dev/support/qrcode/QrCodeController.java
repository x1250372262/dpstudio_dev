package com.dpstudio.dev.support.qrcode;

import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.core.YMP;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.BinaryView;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/26.
 * @Time: 9:44 上午.¬
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/qrcode/")
public class QrCodeController {

    /**
     * 显示二维码
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @RequestMapping("/show/{fileName}/{format}")
    public IView show(@VRequired(msg = "参数错误")
                      @PathVariable(value = "fileName") String fileName,
                      @PathVariable(value = "format") String format,
                      @RequestParam(defaultValue = "false") boolean attach) throws Exception {
        String fileUrl = YMP.get().getParam("dpstudio.qr_code.filepath");
        fileUrl = StringUtils.defaultIfBlank(fileUrl, RuntimeUtils.getRootPath().concat("/dpstudio_qrcode"));
        File qrCodeFile = new File(fileUrl, fileName.concat(".").concat(StringUtils.defaultIfBlank(format, "png")));
        if (!qrCodeFile.exists() || qrCodeFile.length() == 0) {
            return View.httpStatusView(404, "文件未找到");
        }
        BinaryView binaryView = BinaryView.bind(qrCodeFile);
        if (attach) {
            binaryView.useAttachment(fileName.concat(".").concat(StringUtils.defaultIfBlank(format, "png")));
        }
        return binaryView;
    }
}
