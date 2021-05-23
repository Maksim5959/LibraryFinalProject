package com.chuyashkou.library.dao;

import java.sql.SQLException;

public interface GenreDao {

    boolean createGenre(String genre) throws SQLException;

}
