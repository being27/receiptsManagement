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
@RequestMapping("/staff")
public class LoginController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/doLogin")
    @ResponseBody
    public ModelAndView doLogin(@Param("id")String id, @Param("password")String password){
        ModelAndView modelAndView = new ModelAndView();
        //输入为空
        if ("".equals(id) || "".equals(password)){
            modelAndView.addObject("success", false);
            modelAndView.addObject("errMsg", "输入不能为空!");
            modelAndView.setViewName("login");
            return modelAndView;
        }
        Staff staff = staffService.getStaff(id, password);
        //没有注册过该账号
        if (staff == null){
            modelAndView.addObject("success", false);
            modelAndView.addObject("errMsg", "没有注册过该账号!");
        }else {
            request.getSession().setAttribute("staff", staff);
            modelAndView.addObject("success", true);

            modelAndView.setViewName("staff");
        }
        return modelAndView;
    }
}
