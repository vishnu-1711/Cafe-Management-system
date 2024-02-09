package com.inn.cafe.cafe.restimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.cafe.constants.Cafeconstants;
import com.inn.cafe.cafe.rest.UserRest;
import com.inn.cafe.cafe.service.UserService;
import com.inn.cafe.cafe.utils.CafeUtils;
import com.inn.cafe.cafe.wrapper.UserWrapper;


@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        // TODO Auto-generated method stub
        try{
            return userService.signup(requestmap);
        }catch(Exception e){
            e.printStackTrace();
        }
       return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
       try{
        return userService.login(requestMap);
       }catch(Exception ex){
         ex.printStackTrace();
       }

       return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        // TODO Auto-generated method stub
        try{
            return userService.getAllUser();

        }catch(Exception ex){
                 ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requesMap) {
        // TODO Auto-generated method stub
        try{

            return userService.update(requesMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        // TODO Auto-generated method stub
        try{
            
            return userService.checkToken();

        }catch(Exception ex){
            ex.printStackTrace();
        }


         return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try{

            return userService.changePassword(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try{

            return userService.forgotPassword(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
