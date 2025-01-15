package models;

import lombok.Data;

@Data
public class UserProfileModel {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;


    @Override
    public String toString() {
        return "UserProfileModel{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
