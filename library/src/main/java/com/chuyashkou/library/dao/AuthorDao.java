package com.chuyashkou.library.dao;

import com.chuyashkou.library.models.Author;

import java.sql.SQLException;

public interface AuthorDao {

    boolean createAuthor(Author author) throws SQLException;

}
