package com.tyut.travel.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.ProductMapper;
import com.tyut.travel.pojo.Member;
import com.tyut.travel.pojo.Product;
import com.tyut.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/add")
    public String add(Integer pid, HttpSession session, @RequestHeader("Referer") String referer){
        Member member = (Member) session.getAttribute("member");
        favoriteService.addFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }

    @RequestMapping("/del")
    public String del(Integer pid, HttpSession session, @RequestHeader("Referer") String referer){
        Member member = (Member) session.getAttribute("member");
        favoriteService.delFavorite(pid,member.getMid());
        return "redirect:"+referer;
    }

    @RequestMapping("/myFavorite")
    public ModelAndView myFavorite(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        Page<Product> myFavoritePage = favoriteService.findMyFavorite(member.getMid(), page, size);
        modelAndView.addObject("myFavoritePage",myFavoritePage);
        modelAndView.setViewName("/frontdesk/my_favorite");
        return modelAndView;
    }
}
