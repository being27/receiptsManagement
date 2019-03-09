package com.tt.oa.controller;

import com.tt.oa.service.StaffService;
import com.tt.oa.entity.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/doLogin")
    public String doLogin(@Param("id")String id, @Param("password")String password){
        Staff staff = staffService.login(id, password);
        if (staff == null){
            return "redirect:/init/toLoginPage";
        }
        request.getSession().setAttribute("staff", staff);
        return "staff";
    }
}
