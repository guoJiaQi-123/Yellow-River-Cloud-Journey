package com.tyut.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.pojo.Category;
import com.tyut.travel.pojo.Popular;
import com.tyut.travel.pojo.Product;
import com.tyut.travel.service.CategoryService;
import com.tyut.travel.service.PopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/popular")
public class PopularController {


    @Autowired
    private PopularService popularService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "5") Integer size) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Popular> popularPage = popularService.FindPage(page, size);
        modelAndView.addObject("popularPage", popularPage);
        modelAndView.setViewName("backstage/popular_all");
        return modelAndView;

    }

    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        List<Category> allCategory = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allCategory", allCategory);
        modelAndView.setViewName("backstage/popular_add");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Popular popular){
        popularService.add(popular);
        return "redirect:/backstage/popular/all";
    }

    @RequestMapping("/updateStatus")
    public String updateStatus(Integer pid){
        popularService.updateStatus(pid);
        return "redirect:/backstage/popular/all";
    }

    @RequestMapping("/delete")
    public String delete(Integer pid){
        popularService.delete(pid);
        return "redirect:/backstage/popular/all";
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid){
        ModelAndView modelAndView = new ModelAndView();
        List<Category> allCategory = categoryService.findAll();
        modelAndView.addObject("allCategory", allCategory);
        Popular popular = popularService.findById(pid);
        modelAndView.addObject("popular",popular);
        modelAndView.setViewName("backstage/popular_edit2");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Popular popular){
        popularService.update(popular);
        return "redirect:/backstage/popular/all";
    }
}
