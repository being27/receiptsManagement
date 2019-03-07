package com.tt.oa.dao;

import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceiptsDetailDao {
    ReceiptsDetails getReceiptsDetails();
    List<Receipts> listReceiptsDetailById(Integer receiptsId);
    void addReceiptsDetails(@Param("receiptsDetails")List<ReceiptsDetails> receiptsDetails);
    void addReceiptsDetail(@Param("receiptsDetail") ReceiptsDetails receiptsDetails);
    void updateReceiptsDetail(ReceiptsDetails receiptsDetails);
}
