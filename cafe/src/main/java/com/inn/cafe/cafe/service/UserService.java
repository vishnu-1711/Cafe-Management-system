package com.inn.cafe.cafe.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.cafe.wrapper.UserWrapper;

import java.util.*;


public interface UserService {
    

    ResponseEntity<String> signup(Map<String,String> requestmap);
    ResponseEntity<String> login(Map<String,String> requestmap);
    ResponseEntity<List<UserWrapper>> getAllUser();
    ResponseEntity<String> update(Map<String,String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassword(Map<String,String> requestMap);
    ResponseEntity<String> forgotPassword(Map<String,String> requestMap);
}
