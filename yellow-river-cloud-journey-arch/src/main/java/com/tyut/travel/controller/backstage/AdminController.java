package com.tyut.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.RoleWithStatus;
import com.tyut.travel.pojo.Admin;
import com.tyut.travel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/admin/all')")
    public ModelAndView findAdminAll(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size){
        Page<Admin> adminPage = adminService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("adminPage",adminPage);
        modelAndView.setViewName("/backstage/admin_all");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('/admin/add')")
    @RequestMapping("/add")
    public String add(Admin admin){
        adminService.add(admin);
        return "redirect:/backstage/admin/all";
    }


    @PreAuthorize("hasAnyAuthority('/admin/edit')")
    @RequestMapping("/edit")
    public ModelAndView edit(Integer aid){
        Admin admin = adminService.findById(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.updateById(admin);
        return "redirect:/backstage/admin/all";
    }
    @PreAuthorize("hasAnyAuthority('/admin/desc')")
    @RequestMapping("/desc")
    public ModelAndView findDesc(Integer aid){
        Admin admin = adminService.findDesc(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_desc");
        return modelAndView;
    }

    @RequestMapping("/findRole")
    public ModelAndView findRole(Integer aid){
        List<RoleWithStatus> roles = adminService.findRole(aid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles",roles);
        modelAndView.addObject("aid",aid);
        modelAndView.setViewName("/backstage/admin_role");
        return modelAndView;
    }

    @RequestMapping("/updateRole")
    public String updateRole(Integer aid,Integer[] ids){
        adminService.updateRole(aid,ids);
        return "redirect:/backstage/admin/all";
    }
    @PreAuthorize("hasAnyAuthority('/admin/updateStatus')")
    @RequestMapping("/updateStatus")
    public String updateStatus(Integer aid){
        adminService.updateStatus(aid);
        return "redirect:/backstage/admin/all";
    }

    @PreAuthorize("hasAnyAuthority('/admin/delete')")
    @RequestMapping("/delete")
    public String delete(Integer aid){
       adminService.delete(aid);
        return "redirect:/backstage/admin/all";
    }

}
