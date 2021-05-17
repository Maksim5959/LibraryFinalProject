package com.chuyashkou.library.dao.impl;

import com.chuyashkou.library.dao.GenreDao;
import com.chuyashkou.library.models.Genre;
import com.chuyashkou.library.util.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDaoImpl implements GenreDao {

    DataSourceUtil dataSourceUtil = new DataSourceUtil();

    @Override
    public boolean createGenre(String genre) throws SQLException {

        Connection connection = dataSourceUtil.getConnection();

        //Проверка, есть ли уже такой жанр в базе данных
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select name_genre from genres where name_genre = ?");
            preparedStatement1.setString(1, Genre.valueOf(genre).getName());
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Добавление жанра в базу данных
        PreparedStatement preparedStatement2 = connection.prepareStatement("insert genres (name_genre) values (?) ");
        preparedStatement2.setString(1, Genre.valueOf(genre).getName());

        int i = preparedStatement2.executeUpdate();

        dataSourceUtil.closeConnection(connection);

        if (i > 0) {
            return true;
        }
        return false;
    }
}
