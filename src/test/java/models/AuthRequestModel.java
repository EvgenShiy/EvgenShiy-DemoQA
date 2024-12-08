package models;

import lombok.Data;

@Data
public class AuthRequestModel {
    private String userName;
    private String password;

    public AuthRequestModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
