package com.tt.oa.service;

import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;

import java.util.List;

public interface ReceiptsDetailService {
    void addReceiptsDetails(List<ReceiptsDetails> receiptsDetails);
    void addReceiptsDetail(ReceiptsDetails receiptsDetails);
    List<Receipts> listReceiptsDetailById(Integer receiptsId);
    void updateReceiptsDetail(ReceiptsDetails receiptsDetails);
}
