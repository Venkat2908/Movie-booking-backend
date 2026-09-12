package com.venkat.bookmyshowapplication.User.Dto;

import com.venkat.bookmyshowapplication.User.Model.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenGenerateDto {
    private User user;
    @Column(name = "subsequent",nullable = false, columnDefinition = "boolean default false" )
    private Boolean subsequent = false;
}
