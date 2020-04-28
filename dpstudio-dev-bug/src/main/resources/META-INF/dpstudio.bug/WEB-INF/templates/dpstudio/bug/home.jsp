<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://www.ymate.net/ymweb_core" prefix="ymp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ymp:ui src="dpstudio/bug/base/base">
    <ymp:property name="title">问题列表</ymp:property>
    <ymp:property name="css">
        <link href="/admin/css/list.min.css" rel="stylesheet">
        <link href="/dpstudio/bug/layer/theme/default/layer.css" rel="stylesheet">
    </ymp:property>
    <ymp:layout src="dpstudio/bug/base/nav">
        <ymp:property name="pageTitle">
            问题列表
        </ymp:property>
        <ymp:property name="home">
            active open
        </ymp:property>
    </ymp:layout>
    <ymp:layout>
        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">


                <div class="modal fade bs-example-modal-lg" id="commonDiv" tabindex="-1" role="dialog"
                     aria-labelledby="exampleModalLabel"
                     data-backdrop="static">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="exampleModalLabel">添加</h4>
                            </div>

                            <form action="" id="commonForm">
                                <div class="form-group col-md-12" style="margin-top: 10px">
                                    <label>标题</label>
                                    <input type="hidden" name="id" value="">
                                    <textarea class="form-control" id="title" name="title"
                                              style="height: 100px;"></textarea>
                                </div>
                                <div class="form-group col-md-12">
                                    <label>类型</label>
                                    <select class="form-control select-init" id="type"
                                            name="type">
                                        <option value="">请选择</option>
                                        <option value="0">后端</option>
                                        <option value="1">前端</option>
                                        <option value="2">综合</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-12">
                                    <label>优先级</label>
                                    <select class="form-control select-init" id="level"
                                            name="level">
                                        <option value="">请选择</option>
                                        <option value="0">正常</option>
                                        <option value="1">低</option>
                                        <option value="2">高</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-12">
                                    <label>内容</label>
                                    <div class="editor" id="editor" name="content"
                                         style="width: 100%;height: 300px;"></div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="button" class="btn btn-primary" id="add" isClick="0">保存</button>
                                </div>
                            </form>


                        </div>
                    </div>
                </div>


                <div class="modal fade bs-example-modal-lg" id="detailDiv" tabindex="-1" role="dialog"
                     aria-labelledby="exampleModalLabel"
                     data-backdrop="static">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="">详情</h4>
                            </div>
                            <div class="modal-body">

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary" id="add" isClick="0">保存</button>
                            </div>


                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-toolbar clearfix">
                                <div class="border">
                                    <div class="menufix borderNone">
                                        <div class="menufix_title">筛选</div>
                                        <div class="toolbar-btn-action">
                                            <a class="btn btn-danger" id="resetButton">重置</a>
                                            <a class="btn btn-primary m-r-5" id="searchButton">查询</a>
                                        </div>
                                    </div>
                                    <!-- 输入框 -->
                                    <form action="" id="searchForm">
                                        <div class="section_flex">
                                            <div class="select_flex">
                                                <span for="">标题：</span>
                                                <input type="text" name="title" placeholder="请输入标题">
                                            </div>

                                            <div class="select_flex">
                                                <span for="">状态：</span>
                                                <select class="form-control status" name="status">
                                                    <option value="">请选择</option>
                                                    <option value="0">待处理</option>
                                                    <option value="1">已处理</option>
                                                </select>
                                            </div>

                                            <div class="select_flex">
                                                <span for="">类型：</span>
                                                <select class="form-control status" name="type">
                                                    <option value="">请选择</option>
                                                    <option value="0">后端</option>
                                                    <option value="1">前端</option>
                                                    <option value="2">综合</option>
                                                </select>
                                            </div>

                                            <div class="select_flex">
                                                <span for="">优先级：</span>
                                                <select class="form-control status" name="level">
                                                    <option value="">请选择</option>
                                                    <option value="0">正常</option>
                                                    <option value="1">低</option>
                                                    <option value="2">高</option>
                                                </select>
                                            </div>

                                            <div class="select_flex">
                                                <span for="">提出人：</span>
                                                <select class="select-init" id="createUser"
                                                        name="createUser"
                                                        listurl="/dpstudio/bug/user/select" isDefault="1">
                                                    <option value="">请选择</option>
                                                </select>
                                            </div>

                                            <div class="select_flex">
                                                <span for="">处理人：</span>
                                                <select class="select-init" id="handlerUser"
                                                        name="handlerUser"
                                                        listurl="/dpstudio/bug/user/select" isDefault="1">
                                                    <option value="">请选择</option>
                                                </select>
                                            </div>

                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="card-toolbar clearfix">
                                <div class="menufix">
                                    <div class="menufix_title">操作</div>
                                    <div class="toolbar-btn-action">
                                        <a class="btn btn-primary m-r-5 creates" href="#!" data-toggle="modal"
                                           data-target="#commonDiv" data-whatever="@mdo"><i class="mdi mdi-plus"></i> 新增</a>

                                    </div>
                                </div>
                            </div>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table id="tableAjaxId" class="table table-bordered"
                                           listurl="/dpstudio/bug/list" addUrl="/dpstudio/bug/create"
                                           editUrl="/dpstudio/bug/update" detailUrl="/dpstudio/bug/detail"
                                           statusUrl="/dpstudio/bug/status">
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
        </div>
        </div>
    </ymp:layout>
    <!--图表插件-->
    <ymp:property name="script">
        <!--编辑器-->
        <script type="text/javascript" charset="utf-8" src="/admin/plugins/ueditor/ueditor.config.js"></script>
        <script type="text/javascript" charset="utf-8" src="/admin/plugins/ueditor/ueditor.all.js"></script>
        <script type="text/javascript" src="/admin/js/table.js"></script>
        <script type="text/javascript" src="/admin/js/public/request.js"></script>
        <script src="/admin/js/select.js"></script>
        <script src="/dpstudio/bug/layer/layer.js"></script>
        <script type="text/javascript">

            $(function () {
                Table.init();
                SELECT.init($("#searchForm"))
                <!--编辑器-->
                $('.editor').each(function () {
                    var id = $(this).attr("id");
                    editors[id] = UE.getEditor(id, {});
                });
                UE.getEditor("editor").addListener('focus', function () {
                    $("#editor").next().remove();
                })

                $("#add").click(function () {
                    lightyear.loading('show');
                    var data = FORM.getValues($('#commonForm'));
                    console.log(data);
                    jQuery.axpost($("#add"), $("#commonForm").attr('action'), data, function (e) {
                        if (e.ret == 0) {
                            lightyear.notify(e.msg ? e.msg : '操作成功', 'success', 1000, 'mdi mdi-emoticon-happy', 'top', 'center');
                            setTimeout(function () {
                                $("#tableAjaxId").bootstrapTable('refresh');
                                $('#commonDiv').find(".close").trigger("click");
                            }, 1000)
                        } else {
                            lightyear.notify(e.msg != null ? e.msg : "操作失败", 'danger');
                        }
                    })
                });
            });

            /**
             * 点击图片 可以放大
             */
            $(document).on("click", ".bugTitle", function () {
                var id = $(this).attr("id");
                if (id != null && id !== '' && id !== undefined) {
                    jQuery.axget(null, "/dpstudio/bug/detail", {"id":id}, function (e) {
                        if (e.ret == 0) {
                            console.log(e.data.content)
                            layer.open({
                                type: 1,
                                skin: 'layui-layer-rim', //加上边框
                                area: ['800px', '800px'], //宽高
                                content: e.data.content
                            });
                        } else {
                            lightyear.notify(e.msg != null ? e.msg : "数据信息错误", 'danger');
                        }
                    })
                }  else {
                    lightyear.notify(e.msg != null ? e.msg : "数据信息错误", 'danger');
                }
            });

            columns = [
                {
                    checkbox: true, // 显示一个勾选框
                    align: 'center' // 居中显示
                }, {
                    field: 'title', // 返回json数据中的name
                    title: '标题', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle', // 上下居中
                    width: '20%'
                }, {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        return value == 1 ? "已处理" : "待处理";
                    }, cellStyle: function (value, row, index) {

                        if (value == 1) {

                            return {css: {"color": "green"}}

                        } else {

                            return {css: {"color": "red"}}

                        }

                    }
                }, {
                    field: 'type',
                    title: '类型',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        if (value === 1) {
                            return "前端";
                        } else if (value === 2) {
                            return "综合";
                        } else if (value === 0) {
                            return "后端";
                        } else {
                            return "未知";
                        }
                    }
                }, {
                    field: 'level',
                    title: '优先级',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        if (value === 1) {
                            return "低";
                        } else if (value === 2) {
                            return "高";
                        } else if (value === 0) {
                            return "正常";
                        } else {
                            return "未知";
                        }
                    }, cellStyle: function (value, row, index) {

                        if (value == 1) {

                            return {css: {"color": "#97FFFF"}}

                        } else if (value == 2) {

                            return {css: {"color": "red"}}

                        } else {

                            return {css: {"color": "black"}}

                        }

                    }
                }, {
                    field: 'cName', // 返回json数据中的name
                    title: '提出人', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'create_time',
                    title: '提出时间',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        return Utils.changeDateToString(new Date(value));
                    }
                }, {
                    field: 'hName', // 返回json数据中的name
                    title: '处理人', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'handler_time',
                    title: '处理时间',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        return Utils.changeDateToString(new Date(value));
                    }
                }, {
                    field: 'mName', // 返回json数据中的name
                    title: '最后修改人', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'last_modify_time',
                    title: '最后修改时间',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        return Utils.changeDateToString(new Date(value));
                    }
                }, {
                    title: "操作",
                    align: 'center',
                    valign: 'middle',
                    width: 160, // 定义列的宽度，单位为像素px
                    formatter: option
                }
            ]

            function option(value, row, index) {
                if (row.status == 0) {
                    return ' <a class="btn btn-xs btn-default edits" data-toggle="modal" data-target="#commonDiv" dataId="' + row.id + '" title="编辑" ><i class="mdi mdi-pencil"></i></a>' +
                        '<a class="btn btn-xs btn-default statusa" data-toggle="modal" data-target=".statusDio"  status="' + row.status + '" text0="待处理" text1="确认处理" dataId="' + row.id + '"  title="状态" ><i class="mdi mdi-marker-check"></i></a>' +
                        '<a class="btn btn-xs btn-default bugTitle" id="' + row.id + '" title="详情" ><i class="mdi mdi-search-web"></i></a>' +
                        '<a class="btn btn-xs btn-default " href="/dpstudio/bug/home_log?id=' + row.id + '" title="日志"><i class="mdi mdi-comment-alert"></i></a>';
                } else {
                    return ' <a class="btn btn-xs btn-default edits" data-toggle="modal" data-target="#commonDiv" dataId="' + row.id + '" title="编辑" ><i class="mdi mdi-pencil"></i></a>' +
                        '<a class="btn btn-xs btn-default bugTitle"  id="' + row.id + '" title="详情" ><i class="mdi mdi-search-web"></i></a>' +
                        '<a class="btn btn-xs btn-default " href="/dpstudio/bug/home_log?id=' + row.id + '" title="日志"><i class="mdi mdi-comment-alert"></i></a>';
                }
            }

        </script>
    </ymp:property>
</ymp:ui>



