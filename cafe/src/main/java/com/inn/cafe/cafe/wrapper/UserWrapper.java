package com.inn.cafe.cafe.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {
    private Integer id;

    private String name;

    private String email;

    private String contactnumber;

    private String status;


    public UserWrapper(Integer id, String name, String email, String contactnumber, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactnumber = contactnumber;
        this.status = status;
    }



}
