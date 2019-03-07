package com.tt.oa.entity;

import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Receipts {
    private Integer id;
    private String cause;   //事由
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Double totalMoney;
    private String state;
    private String pendingPersonId;    //待处理人id
    private String createPersonId;     //创建人id

    //创建人暂时不需要应该
//    private Staff staff;
    //这里用到了springMVC的数据绑定功能
    private List<ReceiptsDetails> receiptsDetails;  //一个报销单对应多个明细

    //我新创建一个报销单就要首先对处理记录进行记录，包括处理时间、处理类型、处理结果等
    private List<ProcessingRecords> processingRecords;  //一个报销单被处理多次

    public String getPendingPersonId() {
        return pendingPersonId;
    }

    public void setPendingPersonId(String pendingPersonId) {
        this.pendingPersonId = pendingPersonId;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }
    //    public Staff getStaff() {
//        return staff;
//    }
//
//    public void setStaff(Staff staff) {
//        this.staff = staff;
//    }

    public List<ProcessingRecords> getProcessingRecords() {
        return processingRecords;
    }

    public void setProcessingRecords(List<ProcessingRecords> processingRecords) {
        this.processingRecords = processingRecords;
    }

    public List<ReceiptsDetails> getReceiptsDetails() {
        return receiptsDetails;
    }

    public void setReceiptsDetails(List<ReceiptsDetails> receiptsDetails) {
        this.receiptsDetails = receiptsDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Receipts{" +
                "id=" + id +
                ", cause='" + cause + '\'' +
                ", createTime=" + createTime +
                ", totalMoney=" + totalMoney +
                ", state='" + state + '\'' +
                ", pendingPersonId='" + pendingPersonId + '\'' +
                ", createPersonId='" + createPersonId + '\'' +
                ", receiptsDetails=" + receiptsDetails +
                ", processingRecords=" + processingRecords +
                '}';
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
