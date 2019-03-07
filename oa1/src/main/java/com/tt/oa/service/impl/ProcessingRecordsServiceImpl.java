package com.tt.oa.service.impl;

import com.tt.oa.dao.ProcessingRecordsDao;
import com.tt.oa.entity.ProcessingRecords;
import com.tt.oa.service.ProcessingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessingRecordsServiceImpl implements ProcessingRecordsService {
    @Autowired
    private ProcessingRecordsDao processingRecordsDao;

    public List<ProcessingRecords> listProcessingRecordsById(Integer receiptId) {
        return processingRecordsDao.listProcessingRecordsById(receiptId);
    }

    public void addProcessingRecords(ProcessingRecords processingRecords) {
        processingRecordsDao.addProcessingRecords(processingRecords);
    }
}
