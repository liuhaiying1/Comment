package com.comment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/home")
public class HomeController {

    /**
     * 用户登录之后的页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView("/home/index");
        return modelAndView;
    }

    /**
     * 登陆之后的iframe页面
     * @return
     */
    @GetMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView=new ModelAndView("/home/main");
        return modelAndView;
    }
}
