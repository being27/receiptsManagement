package com.tt.oa.service;

import com.tt.oa.entity.ProcessingRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessingRecordsService {
    List<ProcessingRecords> listProcessingRecordsById(Integer receiptId);
    void addProcessingRecords(ProcessingRecords processingRecords);
}
