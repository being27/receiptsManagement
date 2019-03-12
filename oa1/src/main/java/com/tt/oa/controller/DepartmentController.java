package com.tt.oa.controller;

import com.tt.oa.entity.Department;
import com.tt.oa.service.DepartmentService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/list")
    /**
     * 功能：查询数据库中所有的Department，并将list集合传递给前端页面
     */
    public String departmentList(Map<String, Object> map){
        List<Department> departmentList = departmentService.listDepartment();
        map.put("departments", departmentList);
        return "departmentlist";
    }

    @RequestMapping("/to_update")
    /**
     * 去编辑部门的页面，因此需要携带待编辑部门的信息，并显示在页面对应输入框中
     */
    public String toUpdateDepartment(@Param("id")String id, Map<String, Object> map){
        map.put("department", departmentService.getDepartment(id));
        return "departmentupdate";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public List<Department> removeDepartment(@Param("id")String id){
        System.out.println(id);
        departmentService.deleteDepartment(id);
        return departmentService.listDepartment();
    }

    @RequestMapping("/update")
    /**
     * 对部门进行修改，前端输入的信息会自动的绑定到参数对象中去
     */
    public String updateDepartment(Department department){
        departmentService.updateDepartment(department);
        return "redirect:list";
    }

    @RequestMapping("/to_add")
    public String toAddDepartment(Map<String, Object> map){
        Department department = new Department();
        map.put("department", department);
        return "departmentadd";
    }

    @RequestMapping("addDepartment")
    //在方法参数中申明department，则前台的数据会自动绑定到参数的Department对象中去
    public String addDepartment(Department department){
        departmentService.addDepartment(department);
        return "redirect:list";
    }
}
