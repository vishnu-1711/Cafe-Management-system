package com.inn.cafe.cafe.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.cafe.dao.BillDao;
import com.inn.cafe.cafe.dao.CategoryDao;
import com.inn.cafe.cafe.dao.ProductDao;
import com.inn.cafe.cafe.service.DashboardService;

@Service
public class DashboardServiceImpl  implements DashboardService{
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;


    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        // TODO Auto-generated method stub
      Map<String,Object> map = new HashMap<>();

      map.put("category", categoryDao.count());
      map.put("product", productDao.count());
      map.put("bill", billDao.count());
      return new ResponseEntity<>(map,HttpStatus.OK);
    }
    
}
