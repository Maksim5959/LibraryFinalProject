package com.chuyashkou.library.services.impl;

import com.chuyashkou.library.dao.GenreDao;
import com.chuyashkou.library.dao.impl.GenreDaoImpl;
import com.chuyashkou.library.models.Genre;
import com.chuyashkou.library.services.GenreService;

import java.sql.SQLException;
import java.util.Scanner;

public class GenreServiceImpl implements GenreService {

    GenreDao genreDao = new GenreDaoImpl();

    @Override
    public Genre createGenre() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String choice;
        String genreName = null;
        System.out.println("Нажмите клавишу: \n" +
                "'1', что бы выбрать жанр Фантастика\n" +
                "'2', что бы выбрать жанр Роман\n" +
                "'3', что бы выбрать жанр Антиутопия\n" +
                "'4', что бы выбрать жанр Мистика\n" +
                "'5', что бы выбрать жанр Ужасы\n" +
                "'6', что бы выбрать жанр Триллер\n" +
                "'7', что бы выбрать жанр Паронормальные\n" +
                "'8', что бы выбрать жанр История\n" +
                "'9', что бы выбрать жанр Путешествия\n" +
                "'10', что бы выбрать жанр Юмор");
        while (true) {
            choice = scanner.nextLine();
            try {
                switch (Integer.parseInt(choice)) {
                    case 1:
                        genreName = "Fantasy";
                        break;
                    case 2:
                        genreName = "Romance";
                        break;
                    case 3:
                        genreName = "Dystopian";
                        break;
                    case 4:
                        genreName = "Mystery";
                        break;
                    case 5:
                        genreName = "Horror";
                        break;
                    case 6:
                        genreName = "Thriller";
                        break;
                    case 7:
                        genreName = "Paranormal";
                        break;
                    case 8:
                        genreName = "History";
                        break;
                    case 9:
                        genreName = "Travel";
                        break;
                    case 10:
                        genreName = "Humor";
                        break;
                }
                if (genreName != null) {
                    genreDao.createGenre(genreName);
                    break;
                } else {
                    System.out.println("Неверный выбор, попробуйте еще раз: ");
                }
            } catch (Exception e) {
                System.out.println("Неверный выбор, попробуйте еще раз: ");
            }
        }

        return Genre.valueOf(genreName);

    }
}
