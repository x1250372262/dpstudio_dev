<%@taglib uri="http://www.ymate.net/ymweb_core" prefix="ymp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>bug系统登录</title>
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="/admin/css/materialdesignicons.min.css" rel="stylesheet">
    <link href="/admin/css/style.min.css" rel="stylesheet">
    <style>
        .lyear-wrapper {
            position: relative;
        }

        .lyear-login {
            display: flex !important;
            min-height: 100vh;
            align-items: center !important;
            justify-content: center !important;
        }

        .login-center {
            background: #fff;
            min-width: 38.25rem;
            padding: 2.14286em 3.57143em;
            border-radius: 5px;
            margin: 2.85714em 0;
        }

        .login-header {
            margin-bottom: 1.5rem !important;
        }

        .login-center .has-feedback.feedback-left .form-control {
            padding-left: 38px;
            padding-right: 12px;
        }

        .login-center .has-feedback.feedback-left .form-control-feedback {
            left: 0;
            right: auto;
            width: 38px;
            height: 38px;
            line-height: 38px;
            z-index: 4;
            color: #dcdcdc;
        }

        .login-center .has-feedback.feedback-left.row .form-control-feedback {
            left: 15px;
        }
    </style>
</head>

<body>
<div class="row lyear-wrapper">
    <div class="lyear-login ">
        <div class="login-center">
            <div class="login-header text-center">
                <img src="/admin/images/logo-sidebar.png" title="LightYear" alt="LightYear"/>
            </div>
            <form id="loginForm" action="/dpstudio/bug/user/login" method="post">
                <div class="form-group has-feedback feedback-left">
                    <input type="text" placeholder="请输入您的用户名" class="form-control" name="userName" id="userName"/>
                    <span class="mdi mdi-account form-control-feedback" aria-hidden="true"></span>
                </div>
                <div class="form-group">
                    <button class="btn btn-block btn-primary" id="loginButton" type="submit">立即登录
                    </button>
                </div>
            </form>
            <hr>
            <footer class="col-sm-12 text-center">
                <p class="m-b-0">Copyright © 2019 <a href="http://jc2014.com" target="_blank">隽铖网络</a>. All right
                    reserved</p>
            </footer>
        </div>
    </div>
</div>
<script type="text/javascript" src="/admin/js/jquery.min.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/admin/js/jquery.cokie.min.js"></script>
<script type="text/javascript" src="/admin/plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<!--消息提示-->
<script type="text/javascript" src="/admin/js/bootstrap-notify.min.js"></script>
<script type="text/javascript" src="/admin/js/lightyear.js"></script>
<script type="text/javascript" src="/admin/js/md5.js"></script>
<script type="text/javascript" src="/admin/js/form.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        getCookie();
        function setCookie() { //设置cookie
            var userName = $("#userName").val(); //获取用户名信息
            $.cookie("userName", userName);//调用jquery.cookie.js中的方法设置cookie中的用户名
        }

        function getCookie() { //获取cookie
            $.cookie("userName"); //获取cookie中的用户名
        }

        $('#loginForm')
            .bootstrapValidator({
                message: '请输入正确内容',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    userName: {
                        message: '请输入正确的用户名',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            }
                        }
                    }
                }
            })
            .on('success.form.bv', function (e) {
                lightyear.loading('show');
                // Prevent form submission
                e.preventDefault();
                // Get the form instance
                var $form = $(e.target);
                // Get the BootstrapValidator instance
                var bv = $form.data('bootstrapValidator');
                // Use Ajax to submit form data
                var data = FORM.getValues($('#loginForm'));
                $.post($form.attr('action'), data, function (result) {
                    lightyear.loading('hide');
                    if (result.ret == 0) {
                        lightyear.notify('登录成功，页面即将自动跳转~', 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                        setTimeout(function () {
                            setCookie();
                            window.location.href = "/dpstudio/bug/home";
                        }, 1000)
                    } else if (result.ret == -1) {
                        $('#loginForm').bootstrapValidator('disableSubmitButtons', false);
                        $.each(result.data, function (item) {
                            lightyear.notify(result.data[item] != null ? result.data[item] : "登录失败", 'danger');
                            return false;
                        });
                    } else {
                        $('#loginForm').bootstrapValidator('disableSubmitButtons', false);
                        lightyear.notify(result.msg != null ? result.msg : "登录失败", 'danger');
                    }

                }, 'json');
            });
    });
</script>
</body>
</html>