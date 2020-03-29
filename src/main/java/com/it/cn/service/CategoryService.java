package com.it.cn.service;

import com.it.cn.base.BaseService;
import com.it.cn.dao.CategoryDao;
import com.it.cn.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryService extends BaseService<CategoryDao,Category> {

}
