package com.dpstudio.dev.support.qrcode;

import com.dpstudio.dev.support.qrcode.bean.QrCodeResult;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.ymate.platform.commons.QRCodeHelper;
import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.core.YMP;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/26.
 * @Time: 9:29 上午.
 * @Description:
 */
public class QrCodeFactory {

    private final String defaultCharset = "UTF-8";
    private final ErrorCorrectionLevel defaultLevel = ErrorCorrectionLevel.L;
    private final boolean createCode;
    private String fileUrl = YMP.get().getParam("dpstudio.qr_code.filepath");

    private QrCodeFactory(boolean createCode) {
        this.createCode = createCode;
        this.fileUrl = StringUtils.defaultIfBlank(this.fileUrl, RuntimeUtils.getRootPath().concat("/dpstudio_qrcode"));
    }

    public static QrCodeFactory init() {
        return new QrCodeFactory(false);
    }

    public static QrCodeFactory init(boolean createCode) {
        return new QrCodeFactory(createCode);
    }


    public QrCodeResult create(String content, String characterSet, int width, int height, int margin, ErrorCorrectionLevel level, String format) throws Exception {
        if (StringUtils.isBlank(content)) {
            throw new NullArgumentException("二维码内容不能为空");
        }
        characterSet = StringUtils.defaultIfBlank(characterSet, defaultCharset);
        width = width > 0 ? width : 300;
        height = height > 0 ? height : 300;
        margin = margin > 0 ? margin : 1;
        format = StringUtils.defaultIfBlank(format, "png");
        level = level != null ? level : defaultLevel;
        String fileName = content.concat(characterSet)
                .concat(BlurObject.bind(width).toStringValue())
                .concat(BlurObject.bind(height).toStringValue())
                .concat(BlurObject.bind(margin).toStringValue())
                .concat(level.name()).concat(format);
        fileName = DigestUtils.md5Hex(fileName);
        File qrCodeFile = new File(this.fileUrl.concat("/"));
        if (!qrCodeFile.exists()) {
            qrCodeFile.mkdir();
        }
        qrCodeFile = new File(qrCodeFile.getPath(), fileName.concat(".").concat(format));

        if (!qrCodeFile.exists()) {
            QRCodeHelper.create(content, characterSet, width, height, margin, level).setFormat(format).writeToFile(qrCodeFile);
        } else {
            if (createCode) {
                QRCodeHelper.create(content, characterSet, width, height, margin, level).setFormat(format).writeToFile(qrCodeFile);
            }
        }
        QrCodeResult qrCodeResult = new QrCodeResult();
        qrCodeResult.setQrCodeFile(qrCodeFile);
        String qrCodeUrl = WebUtils.baseUrl(WebContext.getRequest()).concat("dpstudio/qrcode/show/" + fileName + "/" + format + "");
        qrCodeResult.setQrCodeUrl(qrCodeUrl);
        return qrCodeResult;
    }

    public QrCodeResult create(String content, int width, int height, int margin, ErrorCorrectionLevel level, String format) throws Exception {
        return create(content, defaultCharset, width, height, margin, level, format);
    }

    public QrCodeResult create(String content, int width, int height, int margin, String format) throws Exception {
        return create(content, width, height, margin, null, format);
    }

    public QrCodeResult create(String content, int width, int height, int margin) throws Exception {
        return create(content, width, height, margin, null);
    }

    public QrCodeResult create(String content, int width, int height) throws Exception {
        return create(content, width, height, 0);
    }

    public QrCodeResult create(String content, int width) throws Exception {
        return create(content, width, 0);
    }

    public QrCodeResult create(String content) throws Exception {
        return create(content, 0);
    }
}
