package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDataModel {  // TODO NEVER USED
    private String userName;
    private String password;
    @JsonProperty("userID")
    private String userId;
}
