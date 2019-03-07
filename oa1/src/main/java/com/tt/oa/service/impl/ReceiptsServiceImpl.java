package com.tt.oa.service.impl;

import com.tt.oa.dao.ReceiptsDao;
import com.tt.oa.dao.ReceiptsDetailDao;
import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;
import com.tt.oa.entity.Staff;
import com.tt.oa.global.Content;
import com.tt.oa.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptsServiceImpl implements ReceiptsService {
    @Autowired
    private ReceiptsDao receiptsDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ReceiptsDetailDao receiptsDetailDao;

    public void addReceipts(Receipts receipts) {
        receipts.setCreateTime(new Date());
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        receipts.setCreatePersonId(staff.getId());
        receipts.setPendingPersonId(staff.getId());
        receipts.setState(Content.CLAIMVOUCHER_CREATED);
        receiptsDao.addReceipts(receipts);
    }

    public List<Receipts> getMyReceipts(String createPersonId) {
        return receiptsDao.getMyReceipts(createPersonId);
    }

    public List<Receipts> getMyPendingReceipts(String pendingPersonId) {
        return receiptsDao.getMyPendingReceipts(pendingPersonId);
    }

    public Receipts getReceiptsDetail(Integer id) {
        return receiptsDao.getReceiptsDetail(id);
    }

    public void updateReceipts(Receipts receipts) {
        //要进行一次判定，如果receiptsDetail的id为null则执行插入操作，不为空则修改操作

        receiptsDao.updateReceipts(receipts);
        List<ReceiptsDetails> receiptsDetails = receipts.getReceiptsDetails();
        for (ReceiptsDetails receiptsDetails1 : receiptsDetails){
            if (receiptsDetails1.getId() == null){
                receiptsDetails1.setReceiptsId(receipts.getId());
                receiptsDetailDao.addReceiptsDetail(receiptsDetails1);
            }else {
                receiptsDetailDao.updateReceiptsDetail(receiptsDetails1);
            }
        }
    }

    public void updateReceiptsOnly(Receipts receipts){
        receiptsDao.updateReceipts(receipts);
    }
}
