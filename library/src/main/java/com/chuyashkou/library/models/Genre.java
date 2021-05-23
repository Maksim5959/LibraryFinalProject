package com.chuyashkou.library.models;

import lombok.Getter;


@Getter
public enum Genre {

    Fantasy("Фантастика"),

    Romance("Роман"),

    Dystopian("Антиутопия"),

    Mystery("Мистика"),

    Horror("Ужасы"),

    Thriller("Триллер"),

    Paranormal("Паранормальные"),

    History("История"),

    Travel("Путешествия"),

    Humor("Юмор");

    private String name;

    private Long id;

    Genre(String name) {
        this.name = name;
    }
}
