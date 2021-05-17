package com.chuyashkou.library.dao.impl;

import com.chuyashkou.library.dao.AuthorDao;
import com.chuyashkou.library.models.Author;
import com.chuyashkou.library.util.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDaoImpl implements AuthorDao {

    DataSourceUtil dataSourceUtil = new DataSourceUtil();
    Connection connection = dataSourceUtil.getConnection();

    @Override
    public boolean createAuthor(Author author) throws SQLException {


        //Проверка, есть ли уже такой автор в базе данных
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select name_author from authors where name_author = ?");
            preparedStatement1.setString(1, author.getName());
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Добавление автора
        PreparedStatement preparedStatement2 = connection.prepareStatement("insert authors (name_author) values (?) ");
        preparedStatement2.setString(1, author.getName());

        int i = preparedStatement2.executeUpdate();

//        dataSourceUtil.closeConnection(connection);

        if (i > 0) {
            return true;
        }
        return false;
    }
}
