package com.inn.cafe.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.cafe.POJO.Category;

public interface CategoryService  {
    
   ResponseEntity<String> addNewCategory(Map<String,String> requestmap);
   ResponseEntity<List<Category>> getAllCategory(String filterValue);
   ResponseEntity<String> updateCategory(Map<String,String> requestMap);
}
