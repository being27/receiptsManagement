package com.tt.oa.service.impl;

import com.tt.oa.dao.ReceiptsDetailDao;
import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;
import com.tt.oa.service.ReceiptsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptsDetailServiceImpl implements ReceiptsDetailService {
    @Autowired
    private ReceiptsDetailDao receiptsDetailDao;
    public void addReceiptsDetails(List<ReceiptsDetails> receiptsDetails) {
        receiptsDetailDao.addReceiptsDetails(receiptsDetails);
    }

    public void addReceiptsDetail(ReceiptsDetails receiptsDetails) {
        receiptsDetailDao.addReceiptsDetail(receiptsDetails);
    }

    public List<Receipts> listReceiptsDetailById(Integer receiptsId) {
        return receiptsDetailDao.listReceiptsDetailById(receiptsId);
    }

    public void updateReceiptsDetail(ReceiptsDetails receiptsDetails) {
        receiptsDetailDao.updateReceiptsDetail(receiptsDetails);
    }
}
