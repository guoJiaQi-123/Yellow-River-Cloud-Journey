package com.tyut.travel.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.pojo.Member;
import com.tyut.travel.pojo.Popular;
import com.tyut.travel.pojo.Product;
import com.tyut.travel.service.FavoriteService;
import com.tyut.travel.service.PopularService;
import com.tyut.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/frontdesk/product")
public class FrontDeskProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private PopularService popularService;

    @RequestMapping("/routeList")
    public ModelAndView routeList(Integer cid, String productName,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        List<Popular> populars = popularService.findPopular();
        Page<Product> productPage = productService.findTravelList(cid, productName, page, size);
        modelAndView.addObject("populars",populars);
        modelAndView.addObject("productPage",productPage);
        modelAndView.addObject("cid",cid);
        modelAndView.addObject("productName",productName);
        modelAndView.setViewName("frontdesk/route_list");
        return modelAndView;
    }

    @RequestMapping("/productDetail")
    public ModelAndView findDetail(Integer pid, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        if(member==null){
            modelAndView.addObject("favorite",false);
        }else {
            boolean favorite = favoriteService.isFavorite(pid, member.getMid());
            if(favorite){
                modelAndView.addObject("favorite",true);
            }else {
                modelAndView.addObject("favorite",false);
            }
        }
        Product product = productService.findOne(pid);

        modelAndView.addObject("product",product);
        modelAndView.setViewName("frontdesk/route_detail");
        return modelAndView;
    }
}
