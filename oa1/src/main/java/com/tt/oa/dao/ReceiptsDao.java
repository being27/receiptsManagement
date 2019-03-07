package com.tt.oa.dao;

import com.tt.oa.entity.Receipts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceiptsDao {
    Receipts getReceiptsDetail(Integer id);
    List<Receipts> getMyReceipts(@Param("createPersonId")String createPersonId);
    List<Receipts> getMyPendingReceipts(@Param("pendingPersonId")String pendingPersonId);
    void addReceipts(@Param("receipts") Receipts receipts);
    void updateReceipts(Receipts receipts);
}
