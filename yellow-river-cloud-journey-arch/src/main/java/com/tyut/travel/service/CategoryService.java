package com.tyut.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyut.travel.mapper.CategoryMapper;
import com.tyut.travel.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    //分页查询
    public Page<Category> findPage(int page,int size){
        return categoryMapper.selectPage(new Page<>(page, size), null);
    }

    //新增方法
    public void add(Category category){
        categoryMapper.insert(category);
    }

    //删除方法
    public void delete(int cid){
        categoryMapper.deleteById(cid);
    }

    //修改方法
    public void update(Category category){
        categoryMapper.updateById(category);
    }

    //查询方法，根据id
    public Category findById(Integer cid){
        return categoryMapper.selectById(cid);
    }

    //查询所有
    public List<Category> findAll(){
        return categoryMapper.selectList(null);
    }
}
