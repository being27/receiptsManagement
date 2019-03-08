package com.tt.oa.service.impl;

import com.tt.oa.dao.ReceiptsDao;
import com.tt.oa.dao.ReceiptsDetailDao;
import com.tt.oa.entity.ProcessingRecords;
import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;
import com.tt.oa.entity.Staff;
import com.tt.oa.global.Content;
import com.tt.oa.service.ProcessingRecordsService;
import com.tt.oa.service.ReceiptsDetailService;
import com.tt.oa.service.ReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private ReceiptsDetailService receiptsDetailService;
    @Autowired
    private ProcessingRecordsService processingRecordsService;
    @Autowired
    private HttpSession session;

    public void addReceipts(Receipts receipts) {
        //设置创建状态
        receipts.setCreateTime(new Date());
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        //设置创建人
        receipts.setCreatePersonId(staff.getId());
        //设置下一个处理人：创建的时候下一个处理人就是自己
        receipts.setPendingPersonId(staff.getId());
        //设置报销单状态，创建时已创建状态
        receipts.setState(Content.CLAIMVOUCHER_CREATED);
        //执行完插入操作后receipts就获取了自增主键id
        receiptsDao.addReceipts(receipts);
        //设置每一个报销单明细的报销单id
        List<ReceiptsDetails> receiptsDetails = receipts.getReceiptsDetails();
        for (ReceiptsDetails receiptsDetail : receiptsDetails){
            receiptsDetail.setReceiptsId(receipts.getId());
        }
        receiptsDetailService.addReceiptsDetails(receiptsDetails);

        //创建处理记录
        ProcessingRecords processingRecords = createProcessingRecords(receipts, Content.DEAL_CREATE, Content.CLAIMVOUCHER_CREATED); //创建类型：新建  创建结果：已创建
        processingRecordsService.addProcessingRecords(processingRecords);
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
        /**
         * 要进行一次判定，如果receiptsDetail的id为null则执行插入操作，不为空则修改操作
         * 修改成功后则添加一条报销单处理记录
         */
        receipts.setState(Content.CLAIMVOUCHER_CREATED);
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
        //逻辑：修改detail表，并且添加一条该报销单的处理流程
        ProcessingRecords processingRecords = createProcessingRecords(receipts, Content.DEAL_UPDATE, Content.CLAIMVOUCHER_CREATED);
        processingRecordsService.addProcessingRecords(processingRecords);
    }

    public void updateReceiptsOnly(Receipts receipts, ProcessingRecords processingRecords){
        //有两个逻辑：部门经理提交小于5000，直接变为已审核状态，总经理提交直接变为已审核状态

        receiptsDao.updateReceipts(receipts);
        processingRecordsService.addProcessingRecords(processingRecords);
    }

    private ProcessingRecords createProcessingRecords(Receipts receipts, String processingType, String processingResult){
        ProcessingRecords processingRecords = new ProcessingRecords();
        processingRecords.setReceiptsId(receipts.getId());
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        processingRecords.setProcessingPersonId(staff.getId());
        processingRecords.setProcessingTime(new Date());
        processingRecords.setProcessingType(processingType);
        processingRecords.setProcessingResult(processingResult);
        return processingRecords;
    }
}
