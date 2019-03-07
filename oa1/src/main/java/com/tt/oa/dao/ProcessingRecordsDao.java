package com.tt.oa.dao;

import com.tt.oa.entity.ProcessingRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessingRecordsDao {
    List<ProcessingRecords> listProcessingRecordsById(Integer receiptId);
    void addProcessingRecords(@Param("processingRecords") ProcessingRecords processingRecords);
    void updateProcessingRecords(@Param("processingRecords") ProcessingRecords processingRecords);
}
