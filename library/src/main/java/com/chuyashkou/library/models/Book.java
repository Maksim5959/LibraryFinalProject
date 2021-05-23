package com.chuyashkou.library.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    private Long id;

    private String title;

    private Genre genre;

    private Author author;

    private String isbn;

    public Book(String title, Genre genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.isbn = Math.abs(title.hashCode()) + "";
    }
}
