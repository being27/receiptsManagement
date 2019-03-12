<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 部门列表 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel  heading-border">
                <div class="panel-menu">
                    <div class="row">
                        <div class="hidden-xs hidden-sm col-md-3">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-refresh"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-plus" onclick="javascript:window.location.href='/department/to_add';"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-9 text-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-chevron-left"></i>
                                </button>
                                <button type="button" class="btn btn-default light">
                                    <i class="fa fa-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body pn">
                    <table id="message-table" class="table admin-form theme-warning tc-checkbox-1">
                        <thead>
                        <tr class="">
                            <th class="text-center hidden-xs">Select</th>
                            <th class="hidden-xs">部门编号</th>
                            <th class="hidden-xs">部门名称</th>
                            <th class="hidden-xs">地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        <c:forEach items="${departments}" var="department">
                        <tr class="message-unread" id="td->tr">
                            <td class="hidden-xs">
                                <label class="option block mn">
                                    <input type="checkbox" name="mobileos" value="FR">
                                    <span class="checkbox mn"></span>
                                </label>
                            </td>
                            <td>${department.id}</td>
                            <td>${department.departmentName}</td>
                            <td>${department.departmentPosition}</td>
                            <td>
                                <a href="/department/to_update?id=${department.id}">编辑</a>
                                <input type="button" onclick="getId(this)" value="删除" class="button">
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<style>
    /* demo page styles */
    body {
        min-height: 2300px;
    }

    .content-header b,
    .admin-form .panel.heading-border:before,
    .admin-form .panel .heading-border:before {
        transition: all 0.7s ease;
    }

    /* responsive demo styles */
    @media (max-width: 800px) {
        .admin-form .panel-body {
            padding: 18px 12px;
        }
    }
</style>

<style>
    .ui-datepicker select.ui-datepicker-month,
    .ui-datepicker select.ui-datepicker-year {
        width: 48%;
        margin-top: 0;
        margin-bottom: 0;

        line-height: 25px;
        text-indent: 3px;

        color: #888;
        border-color: #DDD;
        background-color: #FDFDFD;

        -webkit-appearance: none; /*Optionally disable dropdown arrow*/
    }
</style>
<script src="../../vendor/jquery/jquery-1.4.2.js"></script>
<script src="../../vendor/jquery/jquery_ui/jquery-1.7.1.min.js"></script>
<script src="../../assets/admin-tools/admin-forms/js/jquery.validate.min.js"></script>
<script src="../../assets/admin-tools/admin-forms/js/additional-methods.min.js"></script>
<script src="../../assets/admin-tools/admin-forms/js/jquery-ui-datepicker.min.js"></script>
<script src="../../assets/js/utility/utility.js"></script>
<script src="../../assets/js/demo/demo.js"></script>
<script src="../../assets/js/main.js"></script>
<script type="text/javascript" src="../../js/pages.js"></script>
<script>
    var id;
    function getId(obj) {
        //获取当前的tr标签下的td标签数组
        var td = $(obj).parents("tr").children("td");
        id = td.eq(1).text();
    }

    $(".button").live("click", function () {
        $.ajax({
            url:"/department/remove",
            data:{"id":id},
            dataType:"json",
            type:"post",
            cache:false,
            sync:false,
            success:function (result) {
                console.log(id);
                var content = "";
                for (var i in result){
                    content += "<tr class=\"message-unread\" id=\"td->tr\"><td class=\"hidden-xs\">\n" +
                        "                                <label class=\"option block mn\">\n" +
                        "                                    <input type=\"checkbox\" name=\"mobileos\" value=\"FR\">\n" +
                        "                                    <span class=\"checkbox mn\"></span>\n" +
                        "                                </label>\n" +
                        "                            </td>\n" +
                        "                            <td>" + result[i].id + "</td>\n" +
                        "                            <td>" + result[i].departmentName + "</td>\n" +
                        "                            <td>" + result[i].departmentPosition + "</td>\n" +
                        "                            <td>\n" +
                        "                                <a href=\"/department/to_update?id=" + result[i].id + "\">编辑</a>\n" +
                        "                                <input type=\"button\" onclick=\"getId(this)\" value=\"删除\" class=\"button\">\n" +
                        "                            </td>\n" +
                        "                        </tr>";
                    $("#td->tr").remove();
                }
                console.log(content);
                $("#tbody").html(content);
            }
        })
    });
</script>
</body>
</html>

