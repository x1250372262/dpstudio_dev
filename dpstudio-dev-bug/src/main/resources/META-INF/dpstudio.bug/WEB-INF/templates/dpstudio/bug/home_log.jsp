<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@taglib uri="http://www.ymate.net/ymweb_core" prefix="ymp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ymp:ui src="dpstudio/bug/base/base">
    <ymp:property name="title">问题列表日志</ymp:property>
    <ymp:property name="css">
        <link href="/admin/css/list.min.css" rel="stylesheet">
    </ymp:property>
    <ymp:layout src="dpstudio/bug/base/nav">
        <ymp:property name="pageTitle">
            问题列表日志
        </ymp:property>
        <ymp:property name="home">
            active open
        </ymp:property>
    </ymp:layout>
    <ymp:layout>
        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">


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
                                                <span for="">创建人：</span>
                                                <input type="text" name="handlerUser" placeholder="请输入创建人">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table id="tableAjaxId" class="table table-bordered"
                                           listurl="/dpstudio/bug/log/list?bugId=${param.id}">
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
        <script type="text/javascript" src="/admin/js/table.js"></script>
        <script type="text/javascript" src="/admin/js/public/request.js"></script>
        <script type="text/javascript">

            $(function () {
                Table.init();
            });

            columns = [
                {
                    checkbox: true, // 显示一个勾选框
                    align: 'center' // 居中显示
                }, {
                    field: 'handlerUser', // 返回json数据中的name
                    title: '创建人', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'action', // 返回json数据中的name
                    title: '动作', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'handlerTime',
                    title: '创建时间',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        return Utils.changeDateToString(new Date(value));
                    }
                }
            ]

        </script>
    </ymp:property>
</ymp:ui>



