<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 修改报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <form:form id="admin-form" name="addForm" action="/receipts/update" method="post" modelAttribute="receipts">
                    <form:hidden path="id"/>
                    <form:hidden path="pendingPersonId"/>
                    <%--<form:hidden path="createPersonId"/>--%>
                    <%--<form:hidden path="createTime"/>--%>
                    <%--<form:hidden path="state"/>--%>
                    <div class="panel-body bg-light">
                        <div class="section-divider mt20 mb40">
                            <span> 基本信息 </span>
                        </div>
                        <div class="section">
                            <label for="cause" class="field prepend-icon">
                                <form:input path="cause" class="gui-input" placeholder="事由..." type="text" value="${receipts.cause}"/>
                                <label for="cause" class="field-icon">
                                    <i class="fa fa-lock"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 费用明细 </span>
                        </div>
                        <div class="section row" id="receiptsDetails">

                            <c:forEach items="${receipts.receiptsDetails}" var="detail" varStatus="det" >
                                <c:set var="index" value="${det.index}"/>
                                <div>
                                    <div class="col-md-3">
                                        <label for="receiptsDetails[${index}].costType" class="field prepend-icon">
                                            <form:hidden path="receiptsDetails[${index}].id"/>
                                            <form:hidden path="receiptsDetails[${index}].receiptsId"/>
                                            <%--<form:input path="receiptsDetails" cssClass="gui-input" placeholder="备注..." type="text"/>--%>
                                            <%--<form:select path="receiptsDetails" items="${receipts.receiptsDetails}" itemLabel="costType" itemValue="receiptsId" cssClass="gui-input" />--%>
                                            <form:select path="receiptsDetails[${index}].costType" class="gui-input" placeholder="花销类型..." items="${costTypes}" />
                                        </label>
                                    </div>
                                    <div class="col-md-3">
                                        <label class="field prepend-icon">
                                            <%--<input type="text" name="money" id="money" value="${detail.money}" class="gui-input" placeholder="金额...">--%>
                                            <form:input path="receiptsDetails[${index}].money" class="gui-input money" placeholder="金额..." type="text"/>
                                            <label for="receiptsDetails" class="field-icon">
                                                <i class="fa fa-lock"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-5">
                                        <label for="receiptsDetails" class="field prepend-icon">
                                            <form:input path="receiptsDetails[${index}].detail" class="gui-input" placeholder="备注..." type="text"/>
                                            <label for="receiptsDetails" class="field-icon">
                                                <i class="fa fa-lock"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-1" style="text-align:right;">
                                        <button type="button" class="button"> X </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="section row">
                            <div class="col-md-3">
                                <label for="totalMoney" class="field prepend-icon">
                                    <form:input path="totalMoney" class="gui-input" placeholder="总金额..." readonly="true"/>
                                    <label for="totalMoney" class="field-icon">
                                        <i class="fa fa-user"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="section" style="text-align:right;">
                                <div class="col-md-9">
                                    <button type="button" class="button" id="addReceiptsDetailsButton"> + </button>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存 </button>
                            <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>
