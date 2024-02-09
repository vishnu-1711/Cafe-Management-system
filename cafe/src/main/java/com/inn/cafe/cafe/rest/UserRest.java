package com.inn.cafe.cafe.rest;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.cafe.wrapper.UserWrapper;

@RequestMapping(path = "/user")
public interface UserRest {
    
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody(required = true)Map<String,String> requestmap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true)Map<String,String>requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser(); 

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true)Map<String,String> requesMap);
    


    //This api is used to check whether an authorized user is accessing or routing to somewhere in the page
    @GetMapping(path = "/checktoken")
    ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);


    @PostMapping(path = "forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);


    
    
}
