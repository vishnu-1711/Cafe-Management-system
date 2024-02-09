package com.inn.cafe.cafe.JWT;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inn.cafe.cafe.POJO.User;
import com.inn.cafe.cafe.dao.UserDao;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService{


    @Autowired
    UserDao userDao;

    private User userdetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        log.info("Inside loaduserbyusername {{}}" , username);
       userdetail = userDao.findbyEmailId(username);
       if(!java.util.Objects.isNull(userdetail)){
        return new org.springframework.security.core.userdetails.User(userdetail.getEmail(),userdetail.getPassword(),new ArrayList<>());
       }
       else{
        throw new UsernameNotFoundException("user not found");
       }
    }
    
    public User getUserDetail(){
        return userdetail;
    }
}
