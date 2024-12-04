package models;

import lombok.Data;

import java.util.List;
@Data
public class AddBookToProfileRequestModel {
    
    String userId;
    List<IsbnModel> isbnModel;

    public AddBookToProfileRequestModel(String userId, List<IsbnModel> isbnModel) {
    }
}
