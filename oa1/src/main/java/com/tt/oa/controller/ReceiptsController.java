package com.tt.oa.controller;

import com.tt.oa.dao.StaffDao;
import com.tt.oa.entity.ProcessingRecords;
import com.tt.oa.entity.Receipts;
import com.tt.oa.entity.ReceiptsDetails;
import com.tt.oa.entity.Staff;
import com.tt.oa.global.Content;
import com.tt.oa.service.ProcessingRecordsService;
import com.tt.oa.service.ReceiptsDetailService;
import com.tt.oa.service.StaffService;
import com.tt.oa.service.impl.ReceiptsServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/receipts")
public class ReceiptsController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ReceiptsServiceImpl receiptsService;
    @Autowired
    private ProcessingRecordsService processingRecordsService;
    @Autowired
    private HttpSession session;
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffDao staffDao;
    @RequestMapping("/to_add")
    public String toAddReceipts(Map<String, Object> map){
        //花销类型
        map.put("typeList", Content.getItems());
        map.put("receipts", new Receipts());
        return "receiptsadd";
    }

    @RequestMapping("/addreceipts")
    public String addReceipts(Receipts receipts){
        //这个Receipts的Id是添加到数据库之后才得到的
        receiptsService.addReceipts(receipts);
        return "redirect:to_selfreceipts?id=" + receipts.getCreatePersonId();
    }

    @RequestMapping("/to_selfreceipts")
    public String toSelfReceipts(@Param("id")String id, Map<String, Object> map){
        map.put("myReceipts", receiptsService.getMyReceipts(id));
        return "selfreceipts";
    }

    @RequestMapping("/detail")
    public String detailReceipts(@Param("receiptsId")Integer receiptsId, Map<String, Object> map){
        map.put("receipts", receiptsService.getReceiptsDetail(receiptsId));
        return "receiptsdetail";
    }

    @RequestMapping("/deal")
    public String dealReceipts(@Param("id")String id, Map<String, Object> map){
        map.put("toDealReceipts", receiptsService.getMyPendingReceipts(id));
        return "receiptstodeal";
    }

    @RequestMapping("/to_update")
    public String toUpdateReceipts(@Param("id")Integer id, Map<String, Object> map){
        map.put("receipts", receiptsService.getReceiptsDetail(id));
        map.put("costTypes", Content.getItems());
        return "receiptsupdate";
    }

    @RequestMapping("/update")
    public String updateReceipts(Receipts receipts){
        receiptsService.updateReceipts(receipts);
        Staff staff = (Staff) session.getAttribute("staff");
        return "redirect:deal?id=" + staff.getId();
    }

    @RequestMapping("/submit")
    public String submitReceipts(@Param("id")Integer id){
        Receipts receipts = receiptsService.getReceiptsDetail(id);
        ProcessingRecords processingRecords = null;
        if ("10001".equals(receipts.getCreatePersonId())){
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
            processingRecords = createProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_APPROVED);
        }else if("10003".equals(receipts.getCreatePersonId()) && receipts.getTotalMoney() <= 5000){
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
            processingRecords = createProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_APPROVED);
        }else if ("10003".equals(receipts.getCreatePersonId()) && receipts.getTotalMoney() > 5000){
            receipts.setState(Content.CLAIMVOUCHER_RECHECK);
            receipts.setPendingPersonId("10001");
            processingRecords = createProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_RECHECK);
        }else {
            receipts.setPendingPersonId("10003");
            receipts.setState(Content.CLAIMVOUCHER_SUBMIT);
            processingRecords = createProcessingRecords(receipts, Content.DEAL_SUBMIT, Content.CLAIMVOUCHER_SUBMIT);
        }
        receiptsService.updateReceiptsOnly(receiptsService.getReceiptsDetail(id), processingRecords);
        Staff staff = (Staff) session.getAttribute("staff");
        return "redirect:deal?id=" + staff.getId();
    }

    @RequestMapping("/tocheck")
    public String toCheckReceipts(@Param("id")Integer id, Map<String, Object> map){
        Receipts receipts = receiptsService.getReceiptsDetail(id);
        Staff createStaff = staffDao.getStaffById(receipts.getCreatePersonId());
        Staff pendingPerson = staffDao.getStaffById(receipts.getPendingPersonId());
        map.put("receipts", receipts);
        map.put("pendingPerson", pendingPerson);
        map.put("createStaff", createStaff);
        ProcessingRecords processingRecords = new ProcessingRecords();
        processingRecords.setReceiptsId(receipts.getId());
        map.put("processingRecords", processingRecords);
        return "receiptscheck";
    }

    @RequestMapping("/check")
    public String checkReceipts(ProcessingRecords processingRecords){   //包装了receipts.id 和 remarks、processingType
        Receipts receipts = receiptsService.getReceiptsDetail(processingRecords.getReceiptsId());
        Staff my = (Staff) session.getAttribute("staff");
        Staff staff = staffDao.getStaffById(processingRecords.getProcessingPersonId());
        processingRecords.setProcessingTime(new Date());
        if (Content.DEAL_SUBMIT.equals(processingRecords.getProcessingType()) && Content.POST_GM.equals(staff.getDuty())){    //以总经理的身份审核则直接通过
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_APPROVED);
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
        }else if (Content.DEAL_BACK.equals(processingRecords.getProcessingType())){ //不论是谁打回都直接设置打回状态，并且下一个处理人变为创建者
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_BACK);
            receipts.setState(Content.CLAIMVOUCHER_BACK);
            receipts.setPendingPersonId(receipts.getCreatePersonId());
        }else if (Content.DEAL_REJECT.equals(processingRecords.getProcessingType())){   //如果是拒绝，也统一排个报销单创建者
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_TERMINATED);
            receipts.setPendingPersonId(receipts.getCreatePersonId());
            receipts.setState(Content.CLAIMVOUCHER_TERMINATED);
        }else if (!Content.POST_GM.equals(staff.getDuty()) && Content.DEAL_PASS.equals(processingRecords.getProcessingType()) && receipts.getTotalMoney() > Content.LIMIT_CHECK){
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_RECHECK);
            receipts.setState(Content.CLAIMVOUCHER_RECHECK);
            receipts.setPendingPersonId("10001");
        }else if (Content.DEAL_PAID.equals(processingRecords.getProcessingType())){ //财务打款
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_PAID);
            receipts.setPendingPersonId(receipts.getCreatePersonId());
            receipts.setState(Content.CLAIMVOUCHER_PAID);
        }else {
            processingRecords.setProcessingResult(Content.CLAIMVOUCHER_APPROVED);
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
        }
        receiptsService.updateReceiptsOnly(receipts, processingRecords);
        return "redirect:deal?id=" + my.getId();
    }

    public ProcessingRecords createProcessingRecords(Receipts receipts, String processingType, String processingResult){
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
