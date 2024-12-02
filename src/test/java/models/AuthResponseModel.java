package models;

import lombok.Data;

@Data
public class AuthResponseModel {
    private String userId;
    private String username;
    private String token;
    private String expires;
    private String createdDate;
    private boolean isActive;
}
