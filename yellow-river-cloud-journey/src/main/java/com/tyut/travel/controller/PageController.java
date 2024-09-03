package com.tyut.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面跳转控制层
 */
@Controller
public class PageController {

    //后台页面访问
    @RequestMapping("/backstage/{page}")
    public String showBackStagePage(@PathVariable String page) {
        return "/backstage/" + page;
    }

    //前台页面访问
    @RequestMapping("/frontdesk/{page}")
    public String showFrontDeskPage(@PathVariable String page) {
        return "/frontdesk/" + page;
    }

    // 忽略访问项目logo
    @RequestMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {}

}
