package com.tyut.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.bean.WangEditorResult;
import com.tyut.travel.pojo.Category;
import com.tyut.travel.pojo.Product;
import com.tyut.travel.service.CategoryService;
import com.tyut.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/backstage/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size) {
        Page<Product> productPage = productService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productPage", productPage);
        modelAndView.setViewName("backstage/product_all");
        return modelAndView;
    }

    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        List<Category> allCategory = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("allCategory", allCategory);
        modelAndView.setViewName("backstage/product_add");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Product product) {
        productService.addProduct(product);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid) {
        List<Category> allCategory = categoryService.findAll();
        Product product = productService.findOne(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.addObject("allCategory", allCategory);
        modelAndView.setViewName("backstage/product_edit2");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Product product) {
        productService.updateProduct(product);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping(value = "/upload")
    @ResponseBody//直接将WangEditorResult返回为一段JSON字符串
    public WangEditorResult upload(MultipartFile file, HttpServletRequest request) throws IOException {
        //创建文件夹，存放上传的资源路径
        //1.设置上传文件夹的真实路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        //2.判断该文件夹是否存在，如果不存在，则创建文件夹
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //拿到上传文件名
        String filename = file.getOriginalFilename();
        //给上传文件名加上随机字符串，防止上传同一文件时重名
        filename = UUID.randomUUID() + filename;
        //创建空文件     在dir文件夹下创建一个名为filename的文件
        File newFile = new File(dir, filename);
        //将上传的文件写入空文件中
        file.transferTo(newFile);

        //根据wangEditor富文本编辑器的要求，返回一段JSON字符串，添加@ResponseBody自动将WangEditorResult对象解析为JSON字符串返回给前端
        WangEditorResult wangEditorResult = new WangEditorResult();
        wangEditorResult.setErrno(0);
        String[] data = {"/upload/" + filename};
        wangEditorResult.setData(data);
        return wangEditorResult;
    }

    @RequestMapping("/delete")
    public String delete(Integer pid) {
        productService.deleteProduct(pid);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping("/updateStatus")
    public String updateStatus(Integer pid, @RequestHeader("Referer") String referer) {
        productService.updateStatus(pid);
        return "redirect:" + referer;
    }
}
