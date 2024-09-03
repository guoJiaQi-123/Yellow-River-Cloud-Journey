package com.tyut.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.PermissionWithStatus;
import com.tyut.travel.pojo.Role;
import com.tyut.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/role/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<Role> rolePage = roleService.findRole(page, size);
        modelAndView.addObject("rolePage",rolePage);
        modelAndView.setViewName("/backstage/role_all");
        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority('/role/add')")
    @RequestMapping("/add")
    public String add(Role role){
        roleService.insertRole(role);
        return "redirect:/backstage/role/all";
    }
    @PreAuthorize("hasAnyAuthority('/role/edit')")
    @RequestMapping("/edit")
    public ModelAndView edit(int rid){
        Role role = roleService.selectById(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role",role);
        modelAndView.setViewName("/backstage/role_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Role role){
        roleService.updateRole(role);
        return "redirect:/backstage/role/all";
    }
    @PreAuthorize("hasAnyAuthority('/role/delete')")
    @RequestMapping("/delete")
    public String delete(Integer rid){
        roleService.deleteRole(rid);
        return "redirect:/backstage/role/all";
    }

    @RequestMapping("/findPermission")
    public ModelAndView findPermission(Integer rid){
        List<PermissionWithStatus> permissions = roleService.permissionWithStatuses(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissions",permissions);
        modelAndView.addObject("rid",rid);
        modelAndView.setViewName("/backstage/role_permission");
        return modelAndView;
    }

    @RequestMapping("/updatePermission")
    public String updatePermission(int rid,int[] ids){
        roleService.updatePermission(rid,ids);
        return "redirect:/backstage/role/all";
    }
}
