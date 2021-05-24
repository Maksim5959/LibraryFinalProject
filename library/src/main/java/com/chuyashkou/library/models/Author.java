package com.chuyashkou.library.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Author {

    private Long id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
