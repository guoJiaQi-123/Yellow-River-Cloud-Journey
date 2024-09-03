package com.tyut.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.pojo.Permission;
import com.tyut.travel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasAnyAuthority('/permission/all')")
    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1")int page,
                            @RequestParam(defaultValue = "10")int size){

        Page<Permission> permissionPage = permissionService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissionPage",permissionPage);
        modelAndView.setViewName("/backstage/permission_all");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('/permission/add')")
    @RequestMapping("/add")
    public String add(Permission permission){
        permissionService.insertPermission(permission);
        return "redirect:/backstage/permission/all";
    }

    @PreAuthorize("hasAnyAuthority('/permission/edit')")
    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid){
        Permission permission = permissionService.findPermissionByPid(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permission",permission);
        modelAndView.addObject("pid",pid);
        modelAndView.setViewName("/backstage/permission_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.updatePermission(permission);
        return "redirect:/backstage/permission/all";
    }

    @PreAuthorize("hasAnyAuthority('/permission/delete')")
    @RequestMapping("/delete")
    public String delete(int pid){
        permissionService.delete(pid);
        return "redirect:/backstage/permission/all";
    }
}
