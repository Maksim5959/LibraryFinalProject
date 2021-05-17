package com.chuyashkou.library.services.impl;

import com.chuyashkou.library.dao.AuthorDao;
import com.chuyashkou.library.dao.impl.AuthorDaoImpl;
import com.chuyashkou.library.models.Author;
import com.chuyashkou.library.services.AuthorService;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthorServiceImpl implements AuthorService {

    AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public Author createAuthor() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя автора: ");
        String authorName = scanner.nextLine();
        Author author = new Author(authorName);
        authorDao.createAuthor(author);
        return author;
    }

}
