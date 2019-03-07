package com.tt.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/init")
public class InitController {
    @RequestMapping("/toLoginPage")
    public String toInitPage(){
        return "login";
    }
}
