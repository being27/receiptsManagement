package com.tt.oa.service;

import com.tt.oa.entity.Receipts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceiptsService {
    void addReceipts(Receipts receipts);
    List<Receipts> getMyReceipts(String createPersonId);
    List<Receipts> getMyPendingReceipts(String pendingPersonId);
    Receipts getReceiptsDetail(Integer id);
    void updateReceipts(Receipts receipts);
}
