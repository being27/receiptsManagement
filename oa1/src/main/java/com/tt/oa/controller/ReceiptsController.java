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

@Controller
@RequestMapping("/receipts")
public class ReceiptsController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ReceiptsDetailService receiptsDetailService;
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
    public ModelAndView toAddReceipts(){
        ModelAndView modelAndView = new ModelAndView();
        Receipts receipts = new Receipts();
        //花销类型
        List<String> typeList = Content.getItems();
        modelAndView.addObject("typeList", typeList);
        modelAndView.addObject("receipts", receipts);
        modelAndView.setViewName("receiptsadd");
        return modelAndView;
    }

    @RequestMapping("/addreceipts")
    public String addReceipts(Receipts receipts){
        receiptsService.addReceipts(receipts);
        List<ReceiptsDetails> receiptsDetails = receipts.getReceiptsDetails();
        for (ReceiptsDetails receiptsDetail : receiptsDetails){
            receiptsDetail.setReceiptsId(receipts.getId());
        }
        ProcessingRecords processingRecords = createProcessingRecords(request.getSession(), receipts);
        processingRecords.setProcessingType(Content.DEAL_CREATE);
        processingRecords.setProcessingResult(Content.CLAIMVOUCHER_CREATED);
        receiptsDetailService.addReceiptsDetails(receiptsDetails);
        processingRecordsService.addProcessingRecords(processingRecords);
//        List<ReceiptsDetails> receiptsDetailsList = request.getParameter();
//        System.out.println(totalMoney);
        return "redirect:to_selfreceipts?id=" + receipts.getCreatePersonId();
    }

    private ProcessingRecords createProcessingRecords(HttpSession session, Receipts receipts){
        ProcessingRecords processingRecords = new ProcessingRecords();
        processingRecords.setReceiptsId(receipts.getId());
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        processingRecords.setProcessingPersonId(staff.getId());
        processingRecords.setProcessingTime(new Date());
        return processingRecords;
    }

    @RequestMapping("/to_selfreceipts")
    public ModelAndView toSelfReceipts(@Param("id")String id){
        ModelAndView modelAndView = new ModelAndView();
        List<Receipts> receipts = receiptsService.getMyReceipts(id);
        modelAndView.addObject("myReceipts", receipts);
        modelAndView.setViewName("selfreceipts");
        return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView detailReceipts(@Param("receiptsId")Integer receiptsId){
        ModelAndView modelAndView = new ModelAndView();
        Receipts receipts = receiptsService.getReceiptsDetail(receiptsId);
        modelAndView.addObject("receipts", receipts);
        modelAndView.setViewName("receiptsdetail");
        return modelAndView;
    }

    @RequestMapping("/deal")
    public ModelAndView dealReceipts(@Param("id")String id){
        ModelAndView modelAndView = new ModelAndView();
        List<Receipts> toDealReceipts = receiptsService.getMyPendingReceipts(id);
        modelAndView.addObject("toDealReceipts", toDealReceipts);
        modelAndView.setViewName("receiptstodeal");
        return modelAndView;
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdateReceipts(@Param("id")Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Receipts receipts = receiptsService.getReceiptsDetail(id);
        List<String> costTypes = Content.getItems();
//        System.out.println(receipts);
        modelAndView.addObject("receipts", receipts);
        modelAndView.addObject("costTypes", costTypes);
        modelAndView.setViewName("receiptsupdate");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String updateReceipts(Receipts receipts){
        //逻辑：修改detail表，并且添加一条该报销单的处理流程
        System.out.println(receipts);
        ProcessingRecords processingRecords = createProcessingRecords(request.getSession(), receipts);
        processingRecords.setProcessingType(Content.DEAL_UPDATE);
        processingRecords.setProcessingResult(Content.CLAIMVOUCHER_CREATED);
        receiptsService.updateReceipts(receipts);
        processingRecordsService.addProcessingRecords(processingRecords);
        Staff staff = (Staff) session.getAttribute("staff");
        return "redirect:deal?id=" + staff.getId();
    }

    @RequestMapping("/submit")
    public String submitReceipts(@Param("id")Integer id){
        //有两个逻辑：部门经理提交小于5000，直接变为已审核状态，总经理提交直接变为已审核状态
        Receipts receipts = receiptsService.getReceiptsDetail(id);
        if ("10001".equals(receipts.getCreatePersonId())){
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
            addProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_APPROVED);
        }else if("10003".equals(receipts.getCreatePersonId()) && receipts.getTotalMoney() <= 5000){
            receipts.setState(Content.CLAIMVOUCHER_APPROVED);
            receipts.setPendingPersonId("10002");
            addProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_APPROVED);
        }else if ("10003".equals(receipts.getCreatePersonId()) && receipts.getTotalMoney() > 5000){
            receipts.setState(Content.CLAIMVOUCHER_RECHECK);
            receipts.setPendingPersonId("10001");
            addProcessingRecords(receipts, Content.DEAL_CHECK, Content.CLAIMVOUCHER_RECHECK);
        }else {
            receipts.setPendingPersonId("10003");
            receipts.setState(Content.CLAIMVOUCHER_SUBMIT);
            addProcessingRecords(receipts, Content.DEAL_SUBMIT, Content.CLAIMVOUCHER_SUBMIT);
        }

        receiptsService.updateReceiptsOnly(receipts);
        Staff staff = (Staff) session.getAttribute("staff");
        //添加处理记录
//        addProcessingRecords(receipts, Content.DEAL_SUBMIT, Content.CLAIMVOUCHER_SUBMIT);
//        ProcessingRecords processingRecords = createProcessingRecords(request.getSession(), receipts);
//        processingRecords.setProcessingType(Content.DEAL_SUBMIT);
//        processingRecords.setProcessingResult(Content.CLAIMVOUCHER_SUBMIT);
        return "redirect:deal?id=" + staff.getId();
    }

    private void addProcessingRecords(Receipts receipts, String type, String result){
        ProcessingRecords processingRecords = createProcessingRecords(request.getSession(), receipts);
        processingRecords.setProcessingType(type);
        processingRecords.setProcessingResult(result);
        processingRecordsService.addProcessingRecords(processingRecords);
    }

    @RequestMapping("/tocheck")
    public ModelAndView toCheckReceipts(@Param("id")Integer id){
        //一个新的处理记录
        Receipts receipts = receiptsService.getReceiptsDetail(id);
//        System.out.println(receipts);
        ModelAndView modelAndView = new ModelAndView();
        Staff staff = staffDao.getStaffById(receipts.getCreatePersonId());
        Staff pendingPerson = staffDao.getStaffById(receipts.getPendingPersonId());
        modelAndView.addObject("receipts", receipts);
        modelAndView.addObject("pendingPerson", pendingPerson);
        modelAndView.addObject("staff", staff);
        ProcessingRecords processingRecords = createProcessingRecords(session, receipts);   //还差三个属性
        modelAndView.addObject("processingRecords", processingRecords);
        modelAndView.setViewName("receiptscheck");
        return modelAndView;
    }

    @RequestMapping("/check")
    public String checkReceipts(ProcessingRecords processingRecords){
        System.out.println(processingRecords);
        Receipts receipts = receiptsService.getReceiptsDetail(processingRecords.getReceiptsId());
        Staff my = (Staff) session.getAttribute("staff");
        Staff staff = staffDao.getStaffById(processingRecords.getProcessingPersonId());
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
        processingRecordsService.addProcessingRecords(processingRecords);
        receiptsService.updateReceiptsOnly(receipts);
        System.out.println(receipts);
        System.out.println(processingRecords);
        return "redirect:deal?id=" + my.getId();
    }
}
