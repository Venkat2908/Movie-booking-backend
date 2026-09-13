package com.venkat.bookmyshowapplication.User.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "User_auth_provider")
public class UserAuthProvider extends  BaseModel {
@ManyToOne
@JoinColumn(name = "user_id")
    private User user;
@Enumerated(EnumType.STRING)
@Column(unique = true)
    private Authprovider provider;
    private  String providerUserId;
}
