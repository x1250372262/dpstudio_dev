package com.dpstudio.dev.security.exception;


import net.ymate.platform.webmvc.annotation.ExceptionProcessor;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-17.
 * @Time: 16:21.
 * @Description:
 */
@ExceptionProcessor(code = -4, msg = "您还没有此操作的权限")
public class PermissionException extends RuntimeException {
}
