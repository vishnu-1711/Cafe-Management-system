package com.inn.cafe.cafe.restimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.cafe.POJO.Category;
import com.inn.cafe.cafe.constants.Cafeconstants;
import com.inn.cafe.cafe.rest.CategoryRest;
import com.inn.cafe.cafe.service.CategoryService;
import com.inn.cafe.cafe.utils.CafeUtils;


@RestController
public class CategoryRestImpl implements CategoryRest {

  @Autowired
  CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try{
              return categoryService.addNewCategory(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filtervalue) {
        // TODO Auto-generated method stub
       
        try{
          return categoryService.getAllCategory(filtervalue);
        }catch(Exception ex){
            ex.printStackTrace();
        }
          return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
       
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
      // TODO Auto-generated method stub
      try{

        return categoryService.updateCategory(requestMap);
      }catch(Exception ex){
        ex.printStackTrace();
      }

      return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
