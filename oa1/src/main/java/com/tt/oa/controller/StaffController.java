package com.tt.oa.controller;

import com.tt.oa.entity.Staff;
import com.tt.oa.global.Content;
import com.tt.oa.service.DepartmentService;
import com.tt.oa.service.StaffService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String toStaff(Map<String, Object> map){
        map.put("staffList", staffService.listStaff());
        return "employeelist";
    }

    @RequestMapping("/to_add")
    /**
     * 由于前端有select标签，因此需要传递集合类型的已添加的部门和员工职责集合
     */
    public String toAddStaff(Map<String, Object> map){
        map.put("departmentList", departmentService.listDepartment());
        map.put("duties", Content.getPosts());
        map.put("staff", new Staff());
        return "employeeadd";
    }

    @RequestMapping("/addStaff")
    //通过bean的方式接收form表单参数
    public String addStaff(Staff staff){
        staffService.addStaff(staff);
        return "redirect:list";
    }

    @RequestMapping("/delete")
    public String deleteStaff(String id){
        staffService.deleteStaff(id);
        return "redirect:list";
    }

    @RequestMapping("/toupdate")
    public String toUpdateStaff(@Param("id")String id, Map<String, Object> map){
        map.put("departmentList", departmentService.listDepartment());
        map.put("staff", staffService.getStaffById(id));
        map.put("duties", Content.getPosts());
        return "employeeupdate";
    }

    @RequestMapping("/update")
    public String updateStuff(Staff staff){
        staffService.updateStaff(staff);
        return "redirect:list";
    }
}
