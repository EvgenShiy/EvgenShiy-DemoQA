package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsbnModel {
    private String isbn;

    public static String getIsbn(String isbn) {

        return isbn;
    }
}
