package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileModel {
    private String userId;
    @JsonProperty("username")
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private List<String> books;

//    @Override
//    public String toString() {
//        return "UserProfileModel{" +
//                "userId='" + userId + '\'' +
//                ", userName='" + userName + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                '}';
//    }
}