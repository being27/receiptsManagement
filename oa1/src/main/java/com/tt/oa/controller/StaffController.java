package com.tt.oa.controller;


import com.tt.oa.dao.StaffDao;
import com.tt.oa.entity.Department;
import com.tt.oa.entity.Staff;
import com.tt.oa.global.Content;
import com.tt.oa.service.DepartmentService;
import com.tt.oa.service.StaffService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StaffDao staffDao;
    @RequestMapping("/list")
    public ModelAndView toStaff(){
        ModelAndView modelAndView = new ModelAndView();
        List<Staff> staffList = staffService.listStaff();
        modelAndView.addObject("staffList", staffList);
        modelAndView.setViewName("employeelist");

        return modelAndView;
    }

    @RequestMapping("/to_add")
    public ModelAndView toAddStaff(){
        //不需要携带当前员工的信息
//        Staff staff = (Staff) request.getSession().getAttribute("staff");
        Staff staff = new Staff();
        List<String> duties = Content.getPosts();
        List<Department> departmentList = departmentService.listDepartment();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("duties", duties);
        modelAndView.addObject("staff", staff);
        modelAndView.addObject("departmentList", departmentList);
        modelAndView.setViewName("employeeadd");
        return modelAndView;
    }

    @RequestMapping("/addStaff")
    //通过bean的方式接收form表单参数
    public String addStaff(@Param("name")String name, @Param("duty")String duty){
        Staff staff = new Staff(name, duty);
        String departmentId = request.getParameter("department");
        String staffId = request.getParameter("id");
        Department department = new Department();
        department.setId(departmentId);
        staff.setId(staffId);
        staff.setDepartment(department);
        staffService.addStaff(staff);
        return "redirect:list";
    }

    @RequestMapping("/delete")
    public String deleteStaff(String id){
        staffService.deleteStaff(id);
        return "redirect:list";
    }

    @RequestMapping("/toupdate")
    public ModelAndView toUpdateStaff(String id, String password){
        ModelAndView modelAndView = new ModelAndView();
        List<Department> departmentList = departmentService.listDepartment();
        Staff staff = staffDao.getStaffById(id);
        List<String> duties = Content.getPosts();
        modelAndView.addObject("departmentList", departmentList);
        modelAndView.addObject("staff", staff);
        modelAndView.addObject("duties", duties);
        modelAndView.setViewName("employeeupdate");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String updateStuff(@Param("name")String name, @Param("duty")String duty, @Param("password")String password){
        String departmentId = request.getParameter("department");
        String id = request.getParameter("id");
        Department department = new Department();
        department.setId(departmentId);
        Staff staff = new Staff(password, name, duty);
        staff.setId(id);
        staff.setDepartment(department);
        System.out.println(staff);
        staffService.updateStaff(staff);
        return "redirect:list";
    }
}
