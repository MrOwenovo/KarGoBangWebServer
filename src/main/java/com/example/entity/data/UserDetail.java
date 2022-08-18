package com.example.entity.data;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NonNull
public class UserDetail implements Serializable {
    int id;
    String username;
    String password;
    String role;
}
