package com.tt.oa.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//处理记录
public class ProcessingRecords {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processingTime;
    private String processingType;
    private String processingResult;
    private String remarks; //备注

    private Integer receiptsId; //报销单id
    private String processingPersonId;  //处理人id

    public Integer getReceiptsId() {
        return receiptsId;
    }

    public void setReceiptsId(Integer receiptsId) {
        this.receiptsId = receiptsId;
    }

    public String getProcessingPersonId() {
        return processingPersonId;
    }

    public void setProcessingPersonId(String processingPersonId) {
        this.processingPersonId = processingPersonId;
    }

    public Integer getProcessingRecordsId() {
        return id;
    }

    public void setProcessingRecordsId(Integer processingRecordsId) {
        this.id = processingRecordsId;
    }

    public Date getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Date processingTime) {
        this.processingTime = processingTime;
    }

    public String getProcessingType() {
        return processingType;
    }

    public void setProcessingType(String processingType) {
        this.processingType = processingType;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
