package com.tt.oa.controller;

import com.tt.oa.entity.Department;
import com.tt.oa.service.DepartmentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/list")
    public ModelAndView departmentList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Department> departmentList = departmentService.listDepartment();
        modelAndView.addObject("departments", departmentList);
        modelAndView.setViewName("departmentlist");
        return modelAndView;
    }

    @RequestMapping("/to_update")
    public ModelAndView toUpdateDepartment(@Param("id")String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("department", departmentService.getDepartment(id));
        modelAndView.setViewName("departmentupdate");
        return modelAndView;
    }

    @RequestMapping("/remove")
    public String removeDepartment(@Param("id")String id){
//        ModelAndView modelAndView = new ModelAndView();
        departmentService.deleteDepartment(id);
        return "redirect:list";

//        List<Department> departments = departmentService.listDepartment();
//        modelAndView.addObject("departments", departments);
//        modelAndView.setViewName("departmentlist");
//        return modelAndView;
    }

    @RequestMapping("/update")
    public String updateDepartment(@Param("departmentName")String departmentName, @Param("departmentPosition")String departmentPosition){
        String id = request.getParameter("id");
//        ModelAndView modelAndView = new ModelAndView();
        Department department = new Department(id, departmentName, departmentPosition);
        departmentService.updateDepartment(department);
        return "redirect:list";

//        List<Department> departments = departmentService.listDepartment();
//        modelAndView.addObject("departments", departments);
//        modelAndView.setViewName("departmentlist");
//        return modelAndView;
    }

    @RequestMapping("/to_add")
    public ModelAndView toAddDepartment(){
        Department department = new Department();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("department", department);
        modelAndView.setViewName("departmentadd");
        return modelAndView;
    }

    @RequestMapping("addDepartment")
    public String addDepartment(){
//        ModelAndView modelAndView = new ModelAndView();
        String id = request.getParameter("id");
        String departmentName = request.getParameter("departmentName");
        String departmentPosition = request.getParameter("departmentPosition");
        Department department = new Department(id, departmentName, departmentPosition);
        departmentService.addDepartment(department);
        return "redirect:list";

//        List<Department> departments = departmentService.listDepartment();
//        modelAndView.addObject("departments", departments);
//        modelAndView.setViewName("departmentlist");
//        return modelAndView;
    }
}
