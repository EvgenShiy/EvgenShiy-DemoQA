package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookModel {
    String isbn;
    String title;
    String subTitle;
    String author;

    @JsonProperty("publish_date")
    LocalDate publishDate;

    String publisher;
    String description;
    String website;

    int pages;
}
