package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AddBookToProfileRequestModel {
    private String userId;
    private List<IsbnModel> isbnModel;

//    // Конструктор по умолчанию для Lombok (не обязательно, если вы используете AllArgsConstructor)
//    public AddBookToProfileRequestModel() {}
}
