package models;

import lombok.Data;

import java.util.List;

@Data
public class GetListOfBooksModel {
    String userId;

    List<BookModel> books;
}
