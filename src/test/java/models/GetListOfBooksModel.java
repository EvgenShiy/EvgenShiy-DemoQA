package models;

import lombok.Data;

import java.util.List;

@Data
public class GetListOfBooksModel {
    private List<BookModel> books;
}
