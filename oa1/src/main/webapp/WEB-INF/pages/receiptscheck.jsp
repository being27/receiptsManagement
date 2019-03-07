<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="top.jsp"/>
<%@page import="com.tt.oa.global.Content" %>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 处理报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-6">${receipts.cause}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">创建人</div>
                        <div class="col-md-4">${staff.name}</div>
                        <div class="col-md-2">创建时间</div>
                        <fmt:formatDate value="${receipts.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">待处理人</div>
                        <div class="col-md-4">${pendingPerson.name}</div>
                        <div class="col-md-2">状态</div>
                        <div class="col-md-4">${receipts.state}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 费用明细 </span>
                    </div>
                    <div class="section row">
                        <c:forEach items="${receipts.receiptsDetails}" var="receiptsDetail">
                            <div class="col-md-3">${receiptsDetail.costType}</div>
                            <div class="col-md-3">${receiptsDetail.money}</div>
                            <div class="col-md-5">${receiptsDetail.detail}</div>
                        </c:forEach>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">总金额</div>
                        <div class="col-md-6">${receipts.totalMoney}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 处理流程 </span>
                    </div>
                    <div class="section row">
                        <c:forEach items="${receipts.processingRecords}" var="processingRecord">
                            <div class="col-md-1">${processingRecord.processingPersonId}</div>
                            <%--<spring:eval expression="processingRecord.processingTime"/>--%>
                            <%--<div class="col-md-3"><spring ${processingRecord.processingTime}</div>--%>
                            <div class="col-md-2"><fmt:formatDate value="${processingRecord.processingTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                            <div class="col-md-2">${processingRecord.processingType}</div>
                            <div class="col-md-2">${processingRecord.processingResult}</div>
                            <div class="col-md-5">${processingRecord.remarks}</div>
                            <br>
                        </c:forEach>
                    </div>

                    <form:form id="admin-form" name="addForm" modelAttribute="processingRecords" action="/receipts/check" method="post">
                        <div class="panel-body bg-light">
                            <form:hidden path="receiptsId"/>
                            <form:hidden path="processingPersonId"/>
                            <form:hidden path="processingTime"/>
                            <div class="section">
                                <label for="remarks" class="field prepend-icon">
                                    <input id="remarks" name="remarks" class="gui-input" placeholder="备注..." type="text" value=""/>
                                    <label for="remarks" class="field-icon">
                                        <i class="fa fa-lock"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="panel-footer text-right">
                                <c:choose>
                                    <c:when test="${sessionScope.staff.duty == Content.POST_FM || sessionScope.staff.duty == Content.POST_GM}">
                                        <button type="submit" class="button" name="processingType" value="${Content.DEAL_PASS}">${Content.DEAL_PASS}</button>
                                        <button type="submit" class="button" name="processingType" value="${Content.DEAL_BACK}">${Content.DEAL_BACK}</button>
                                        <button type="submit" class="button" name="processingType" value="${Content.DEAL_REJECT}">${Content.DEAL_REJECT}</button>
                                    </c:when>
                                    <c:when test="${sessionScope.staff.duty == Content.POST_CASHIER}">
                                        <button type="submit" class="button" name="processingType" value="${Content.DEAL_PAID}">${Content.DEAL_PAID}</button>
                                    </c:when>
                                </c:choose>
                                <button type="submit" class="button" onclick="javascript:window.history.go(-1);"> 返回</button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>


<%--<input id="claimVoucherId" name="claimVoucherId" type="hidden" value="8"/>--%>
<%--<input id="dealWay" name="dealWay" type="hidden" value=""/>--%>
<%--<div class="panel-body bg-light">--%>
<%--<div class="section-divider mt20 mb40">--%>
<%--<span> 基本信息 </span>--%>
<%--</div>--%>
<%--<div class="section">--%>
<%--<label for="comment" class="field prepend-icon">--%>
<%--<input id="comment" name="comment" class="gui-input" placeholder="备注..." type="text" value=""/>--%>
<%--<label for="comment" class="field-icon">--%>
<%--<i class="fa fa-lock"></i>--%>
<%--</label>--%>
<%--</label>--%>
<%--</div>--%>