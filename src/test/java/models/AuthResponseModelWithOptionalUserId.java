package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseModelWithOptionalUserId {

    @JsonProperty("userID")  // Маппинг поля userID в JSON
    private String userId;

    private String username;
    private String password;
    private String token;
    private String expires;

    @JsonProperty("created_date")
    private String createdDate;

    private Boolean isActive;

    // Метод для получения UserId
    public String getUserId() {
        return userId != null ? userId : username;  // Если userId отсутствует, возвращаем username
    }
}