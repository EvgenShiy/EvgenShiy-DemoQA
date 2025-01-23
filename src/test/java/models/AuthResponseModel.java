package models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseModel {
    @JsonProperty("userID")
    @JsonAlias("userId") // Позволяет использовать как "userID", так и "userId"
    private String userId;
    private String username;
    private String password;
    private String token;
    private String expires;

    @JsonProperty("created_date")
    private String createdDate;

    private Boolean isActive;
}
