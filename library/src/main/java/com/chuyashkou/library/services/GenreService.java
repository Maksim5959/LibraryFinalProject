package com.chuyashkou.library.services;

import com.chuyashkou.library.models.Genre;

import java.sql.SQLException;

public interface GenreService {

    Genre createGenre() throws SQLException;

}
