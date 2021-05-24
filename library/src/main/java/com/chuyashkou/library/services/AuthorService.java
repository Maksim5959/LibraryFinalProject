package com.chuyashkou.library.services;

import com.chuyashkou.library.models.Author;

import java.sql.SQLException;

public interface AuthorService {

    Author createAuthor() throws SQLException;

}
