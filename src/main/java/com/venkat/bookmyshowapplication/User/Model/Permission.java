package com.venkat.bookmyshowapplication.User.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Permission extends  BaseModel {
    @Enumerated(EnumType.STRING)
    private PermissionName name;

}
