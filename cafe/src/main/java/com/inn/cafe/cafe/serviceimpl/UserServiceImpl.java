package com.inn.cafe.cafe.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.cafe.cafe.JWT.CustomerUsersDetailsService;
import com.inn.cafe.cafe.JWT.jwtUtil;
import com.inn.cafe.cafe.JWT.JwtFilter;
import com.inn.cafe.cafe.POJO.User;
import com.inn.cafe.cafe.constants.Cafeconstants;
import com.inn.cafe.cafe.dao.UserDao;
import com.inn.cafe.cafe.service.UserService;
import com.inn.cafe.cafe.utils.CafeUtils;
import com.inn.cafe.cafe.utils.EmailUtils;
import com.inn.cafe.cafe.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userdao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    jwtUtil jwtUtil;

    @Autowired
    JwtFilter JwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        // TODO Auto-generated method stub

        log.info("Inside signup {}", requestmap);

        try {
            if (validateSignupMap(requestmap)) {

                User user = userdao.findbyEmailId(requestmap.get("email"));

                if (Objects.isNull(user)) {
                    userdao.save(getUserfromMap(requestmap));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponseEntity("Email alreay exists", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponseEntity(Cafeconstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignupMap(Map<String, String> requestMap) {

        if (requestMap.containsKey("name") && requestMap.containsKey("contactnumber") && requestMap.containsKey("email")
                && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserfromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactnumber(requestMap.get("contactnumber"));
        user.setEmail(requestMap.get("email"));
        // user.setPassword(requestMap.get("password"));

        String encodedPassword = new BCryptPasswordEncoder().encode(requestMap.get("password"));
        user.setPassword(encodedPassword);
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestmap) {
        // TODO Auto-generated method stub
        log.info("Inside Login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestmap.get("email"), requestmap.get("password")));

            if (auth.isAuthenticated()) {
                if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {

                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole())
                            + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex.getMessage());
        }

        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials" + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        // TODO Auto-generated method stub
        try {
            if (JwtFilter.isAdmin()) {
                return new ResponseEntity<>(userdao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try{
            if(JwtFilter.isAdmin()){
                java.util.Optional<User> optional =  userdao.findById(Integer.parseInt(requestMap.get("id")));

                if(!optional.isEmpty()){
                    userdao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));

                    sendMailToUser(requestMap.get("status"),optional.get().getEmail(),userdao.getAllAdmin());

                    return CafeUtils.getResponseEntity("User status Updated successfully", HttpStatus.OK);

                }
                else{
                   return CafeUtils.getResponseEntity("User id doesnot exist", HttpStatus.OK);
                }
            }else{
                return CafeUtils.getResponseEntity(Cafeconstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToUser(String status, String user, List<String> allAdmin) {

        allAdmin.remove(JwtFilter.getCurrentUser());

        if(status!=null && status.equalsIgnoreCase("true")){
             emailUtils.sendSimpleMessage(JwtFilter.getCurrentUser(), "Account approved", "User:- "+user+" \n is approved by \nADMIN:-"+JwtFilter.getCurrentUser() , allAdmin);
        }
        else{
             emailUtils.sendSimpleMessage(JwtFilter.getCurrentUser(), "Account Disabled", "User:- "+user+" \n is disabled by \nADMIN:-"+JwtFilter.getCurrentUser() , allAdmin);

        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        // TODO Auto-generated method stub
       CafeUtils.getResponseEntity("true", HttpStatus.OK);


       return null;
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
       try{

        User userobj = userdao.findByEmail(JwtFilter.getCurrentUser());

        if(!userobj.equals(null)){
             if(userobj.getPassword().equals(requestMap.get("oldPassword"))){
                 userobj.setPassword(requestMap.get("newPassword"));
                 userdao.save(userobj);
                 return CafeUtils.getResponseEntity("Password Updated successfully", HttpStatus.OK);
             }
             return CafeUtils.getResponseEntity("Incorrect old Password",HttpStatus.BAD_REQUEST);
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
       }catch(Exception ex){
        ex.printStackTrace();
       }


       return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        // TODO Auto-generated method stub
        try{
         User userr =  userdao.findByEmail(requestMap.get("email"));
         if(!Objects.isNull(userr) && !Strings.isNullOrEmpty(userr.getEmail()))
            emailUtils.forgetMail(userr.getEmail(), "Credentials by Cafe Management ", userr.getPassword());
         return CafeUtils.getResponseEntity("Check your mail for credentials", HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(Cafeconstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    } 

}
