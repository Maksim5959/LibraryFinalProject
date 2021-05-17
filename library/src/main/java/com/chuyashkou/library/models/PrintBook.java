package com.chuyashkou.library.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrintBook {

    Long id;

    String title;

    String genre;

    String author;

    Long isbn;

    @Override
    public String toString() {
        return "[ * ID Книги ---> " + id + " * Название книги ---> " + title +
                " * Жанр книги ---> " + genre + " * Автор книги ---> " + author +
                " * ISBN книги ---> " + isbn + " * ]";
    }
}
