package com.tyut.travel.controller.frontdesk;

import com.tyut.travel.pojo.Member;
import com.tyut.travel.pojo.Result;
import com.tyut.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/register")
    public ModelAndView register(Member member, String checkCode, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        //1.验证验证码是否正确
        //获取后台生成的验证码
        String sessionCheckCode = (String) session.getAttribute("checkCode");
        //将后台生成的验证码与前端表单中的验证码进行一个对比
        if (!checkCode.equalsIgnoreCase(sessionCheckCode)) {
            modelAndView.addObject("message", "验证码错误");
            modelAndView.setViewName("/frontdesk/register");
            return modelAndView;
        }
        //2.注册用户
        Result register = memberService.register(member);
        if (!register.isFlag()) {//注册失败
            modelAndView.addObject("message", register.getMessage());
            modelAndView.setViewName("/frontdesk/register");
        } else {//注册成功
            modelAndView.setViewName("/frontdesk/register_ok");
        }
        return modelAndView;
    }

    @RequestMapping("/active")
    public ModelAndView active(String activeCode) {
        ModelAndView modelAndView = new ModelAndView();
        String active = memberService.active(activeCode);
        modelAndView.addObject("message", active);
        modelAndView.setViewName("/frontdesk/active_result");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(String name, String password, HttpSession session) {
        Result loginResult = memberService.login(name, password);
        ModelAndView modelAndView = new ModelAndView();
        if (!loginResult.isFlag()) {// 登录失败
            modelAndView.addObject("message", loginResult.getMessage());
            modelAndView.setViewName("/frontdesk/login");

        } else {// 登录成功
            session.setAttribute("member", loginResult.getData());// 将用户信息存入session
            modelAndView.setViewName("redirect:/frontdesk/index");
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("member");
        return "redirect:/frontdesk/index";
    }
}
