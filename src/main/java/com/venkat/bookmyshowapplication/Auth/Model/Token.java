package com.venkat.bookmyshowapplication.Auth.Model;

import com.venkat.bookmyshowapplication.User.Model.BaseModel;
import com.venkat.bookmyshowapplication.User.Model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name ="Token_table")
public class Token extends BaseModel {
    @ManyToOne
    private User user_details;
    private String accesstoken;
    private String refreshtoken;
    private String accessType;
    private long expriy_time;
}
