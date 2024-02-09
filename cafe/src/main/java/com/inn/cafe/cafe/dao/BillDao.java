package com.inn.cafe.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.inn.cafe.cafe.POJO.Bill;

public interface BillDao  extends JpaRepository<Bill,Integer> {

    List<Bill> getAllBills();

    List<Bill> getBillByUsername(@Param("username") String username);
    
}
