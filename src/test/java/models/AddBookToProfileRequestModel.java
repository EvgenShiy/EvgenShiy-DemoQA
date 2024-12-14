package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddBookToProfileRequestModel {
    private String userId;
    private List<IsbnModel> collectionOfIsbns;

}
