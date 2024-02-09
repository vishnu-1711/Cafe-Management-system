package com.inn.cafe.cafe.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtils {
    
@Autowired
private JavaMailSender emailSender;

public void sendSimpleMessage(String to,String subject,String text,List<String> list){
    
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("vishnukar91@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);

    if(list!=null && list.size() > 0)
        message.setCc(getCcArray(list));
    emailSender.send(message);   
}


private String[] getCcArray(List<String> ccList){
    String[] cc = new String[ccList.size()];

    for(int i = 0; i < ccList.size();i++){
        cc[i] = ccList.get(i);
    }
    return cc;

}

public void forgetMail(String to,String subject,String Password) throws MessagingException{
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,true);
    helper.setFrom("vishnukar91@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);
    String htmlMsg = "<p><b> Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + "<br><b>Password: </b> " + Password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
    message.setContent(htmlMsg,"text/html");
    emailSender.send(message);
}
}
