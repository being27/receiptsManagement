<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="top.jsp"/>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 报销单详情 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">事由</div>
                        <div class="col-md-6">${receipts.cause}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">创建人</div>
                        <div class="col-md-4">${sessionScope.staff.name}</div>
                        <div class="col-md-2">创建时间</div>
                        <div class="col-md-4"><spring:eval expression="receipts.createTime"/></div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">待处理人</div>
                        <div class="col-md-4">${receipts.pendingPersonId}</div>
                        <div class="col-md-2">状态</div>
                        <div class="col-md-4">${receipts.state}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 费用明细 </span>
                    </div>
                    <div class="section row">
                        <c:forEach items="${receipts.receiptsDetails}" var="receiptsDetails">
                            <div class="col-md-3">${receiptsDetails.costType}</div>
                            <div class="col-md-3">${receiptsDetails.money}</div>
                            <div class="col-md-5">${receiptsDetails.detail}</div>
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
                        <c:forEach items="${receipts.processingRecords}" var="processingRecords">
                            <div class="col-md-1">${processingRecords.processingPersonId}</div>
                            <div class="col-md-3"><spring:eval expression="processingRecords.processingTime"/></div>
                            <div class="col-md-1">${processingRecords.processingType}</div>
                            <div class="col-md-2">${processingRecords.processingResult}</div>
                            <div class="col-md-5">${processingRecords.remarks}</div>
                            <br>
                        </c:forEach>
                    </div>
                    <div class="panel-footer text-right">
                        <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>