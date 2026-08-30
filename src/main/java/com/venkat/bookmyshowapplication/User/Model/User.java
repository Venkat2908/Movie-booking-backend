package com.venkat.bookmyshowapplication.User.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name ="Users")
public class User extends BaseModel {
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one number, one special character, and minimum 8 characters"
    )
    private String Password;
    @Enumerated(EnumType.STRING)
    private UserResponseStatus status;
    private boolean verifed;

}
