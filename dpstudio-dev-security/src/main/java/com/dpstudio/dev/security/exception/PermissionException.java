package com.dpstudio.dev.security.exception;

import net.ymate.platform.webmvc.annotation.ExceptionProcessor;
import net.ymate.platform.webmvc.util.ErrorCode;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-17.
 * @Time: 16:21.
 * @Description:
 */
@ExceptionProcessor(code = ErrorCode.REQUEST_RESOURCE_UNAUTHORIZED, msg = ErrorCode.MSG_REQUEST_RESOURCE_UNAUTHORIZED)
public class PermissionException extends RuntimeException {
}
