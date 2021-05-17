package com.chuyashkou.library.dao.impl;

import com.chuyashkou.library.dao.BookDao;
import com.chuyashkou.library.models.Book;
import com.chuyashkou.library.models.PrintBook;
import com.chuyashkou.library.util.DataSourceUtil;
import com.chuyashkou.library.xml.BooksParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    DataSourceUtil dataSourceUtil = new DataSourceUtil();
    Connection connection = dataSourceUtil.getConnection();

    @Override
    public boolean createBook(Book book) throws SQLException {
        try {
            PreparedStatement preparedStatement5 = connection.prepareStatement(
                    "select id from connections " +
                            "where book_id = (select id from books where title = ?) " +
                            "and genre_id = (select id from genres where name_genre = ?) " +
                            "and author_id = (select id from  authors where name_author = ?)");
            preparedStatement5.setString(1, book.getTitle());
            preparedStatement5.setString(2, book.getGenre().getName());
            preparedStatement5.setString(3, book.getAuthor().getName());

            ResultSet resultSet = preparedStatement5.executeQuery();

            if (resultSet.next()) {
                System.out.println("Книга уже добавлена. ");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Проверка, есть ли книга с таким именем в базе данных
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select title from books where title = ?");
            preparedStatement1.setString(1, book.getTitle());
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {

                //Добавление в базу связей книги с авторами
                PreparedStatement preparedStatement4 = connection.prepareStatement(
                        "insert connections (book_id, genre_id, author_id) " +
                                "values ((select id from books where title = ?), " +
                                "(select id from genres where name_genre = ?), " +
                                "(select id from authors where name_author = ?))");
                preparedStatement4.setString(1, book.getTitle());
                preparedStatement4.setString(2, book.getGenre().getName());
                preparedStatement4.setString(3, book.getAuthor().getName());


                int i3 = preparedStatement4.executeUpdate();

                if (i3 > 0) {
                    System.out.println("Книга создана успешно. ");
                    return true;
                }
                System.out.println("Создание книги невозможно. ");
                return false;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Добавление книги в базу данных
        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "insert books(title, isbn) " +
                        "values ((?), (?))");
        preparedStatement2.setString(1, book.getTitle());
        preparedStatement2.setString(2, book.getIsbn());

        int i = preparedStatement2.executeUpdate();

        //Добавление в базу связей книги с авторами
        PreparedStatement preparedStatement3 = connection.prepareStatement(
                "insert connections (book_id, genre_id, author_id) " +
                        "values ((select id from books where title = ?), " +
                        "(select id from genres where name_genre = ?), " +
                        "(select id from authors where name_author = ?))");
        preparedStatement3.setString(1, book.getTitle());
        preparedStatement3.setString(2, book.getGenre().getName());
        preparedStatement3.setString(3, book.getAuthor().getName());

        int i1 = preparedStatement3.executeUpdate();

        if (i > 0 && i1 > 0) {
            System.out.println("Книга создана успешно. ");
            return true;
        }
        System.out.println("Создание книги невозможно. ");
        return false;
    }

    @Override
    public boolean addBooksFromXml(String xml) throws SQLException, IOException, SAXException, ParserConfigurationException {

        BooksParser booksParser = new BooksParser();

        File file = new File(xml);

        ArrayList<Book> books = booksParser.getBooks(file);

        GenreDaoImpl genreDao = new GenreDaoImpl();

        AuthorDaoImpl authorDao = new AuthorDaoImpl();

        boolean genre = false;

        boolean author = false;

        boolean book1 = false;

        for (Book book : books) {
            genre = genreDao.createGenre(book.getGenre() + "");
        }

        for (Book book : books) {
            author = authorDao.createAuthor(book.getAuthor());
        }

        for (Book book : books) {
            book1 = createBook(book);
        }
        if (genre && author && book1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteBook(Long id) throws SQLException {


        //Проверка, есть ли книга с таким id в базе данных

        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select id from books where id = ?");
            preparedStatement1.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Книги с таким id нет в базе данных. ");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Удаление книги из базы данных
        PreparedStatement preparedStatement2 = connection.prepareStatement("delete from connections where book_id = ? ");
        preparedStatement2.setString(1, String.valueOf(id));
        int i = preparedStatement2.executeUpdate();

        PreparedStatement preparedStatement3 = connection.prepareStatement("delete from books where id = ? ");
        preparedStatement3.setString(1, String.valueOf(id));
        int i1 = preparedStatement3.executeUpdate();

        if (i > 0 && i1 > 0) {
            System.out.println("Книга удалена успешно. ");
            return true;
        }
        System.out.println("Книга не удалена. ");
        return false;
    }

    @Override
    public boolean editBook(Long id, int choice, String newName) throws SQLException {
        int i = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select id from books where id = ?");
            preparedStatement1.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Книги с таким id нет в базе данных. ");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 1. Меняем имя книги, 2. Меняем имя автора, 3. Меняем жанр
        if (choice == 1) {
            try {
                PreparedStatement preparedStatement1 = connection.prepareStatement("select title from books where title = ?");
                preparedStatement1.setString(1, newName);
                ResultSet resultSet = preparedStatement1.executeQuery();
                if (resultSet.next()) {
                    System.out.println("Книга с таким именем уже существует. ");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Редактирование названия книги
            PreparedStatement preparedStatement2 = connection.prepareStatement("update books set title = ? where id = ?");
            preparedStatement2.setString(1, newName);
            preparedStatement2.setString(2, String.valueOf(id));
            i = preparedStatement2.executeUpdate();


            if (i > 0) {
                System.out.println("Книга изменена. ");
                return true;
            }
            return false;
        } else if (choice == 2) {
            try {
                PreparedStatement preparedStatement3 = connection.prepareStatement("select name_author from authors where name_author = ?");
                preparedStatement3.setString(1, newName);
                ResultSet resultSet = preparedStatement3.executeQuery();
                if (resultSet.next()) {
                    //Редактирование автора
                    PreparedStatement preparedStatement4 = connection.prepareStatement("update connections set author_id = " +
                            "(select id from authors where name_author = ?) where book_id = ?");
                    preparedStatement4.setString(1, newName);
                    preparedStatement4.setString(2, String.valueOf(id));
                    i = preparedStatement4.executeUpdate();
                } else {
                    //Добавление автора
                    PreparedStatement preparedStatement5 = connection.prepareStatement("insert authors (name_author) values (?) ");
                    preparedStatement5.setString(1, newName);
                    i = preparedStatement5.executeUpdate();

                    PreparedStatement preparedStatement6 = connection.prepareStatement("update connections set author_id = " +
                            "(select id from authors where name_author = ?) where book_id = ?");
                    preparedStatement6.setString(1, newName);
                    preparedStatement6.setString(2, String.valueOf(id));
                    i = preparedStatement6.executeUpdate();
                }
                if (i > 0) {
                    System.out.println("Книга изменена. ");
                    return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (choice == 3) {
            try {
                PreparedStatement preparedStatement7 = connection.prepareStatement("select name_genre from genres where name_genre = ?");
                preparedStatement7.setString(1, newName);
                ResultSet resultSet = preparedStatement7.executeQuery();
                if (resultSet.next()) {
                    //Редактирование жанра
                    PreparedStatement preparedStatement8 = connection.prepareStatement("update connections set genre_id = " +
                            "(select id from genres where name_genre = ?) where book_id = ?");
                    preparedStatement8.setString(1, newName);
                    preparedStatement8.setString(2, String.valueOf(id));
                    i = preparedStatement8.executeUpdate();
                } else {
                    PreparedStatement preparedStatement9 = connection.prepareStatement("insert genres (name_genre) values (?) ");
                    preparedStatement9.setString(1, newName);
                    i = preparedStatement9.executeUpdate();

                    PreparedStatement preparedStatement10 = connection.prepareStatement("update connections set genre_id = " +
                            "(select id from genres where name_genre = ?) where book_id = ?");
                    preparedStatement10.setString(1, newName);
                    preparedStatement10.setString(2, String.valueOf(id));
                    i = preparedStatement10.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (i > 0) {
            System.out.println("Книга изменена. ");
            return true;
        }
        return false;
    }

    @Override
    public List<PrintBook> getBook(Long id) throws SQLException {
        int i = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select id from connections where book_id = ? ");
            preparedStatement1.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement1.executeQuery();

            List<Long> connectionsId = getConnectionsId(resultSet, connection);

            List<PrintBook> printBooks = new ArrayList<>();

            for (Long id1 : connectionsId) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select " +
                        "books.id, books.title, books.isbn, genres.name_genre, authors.name_author from books, genres, authors " +
                        "where books.id = (select book_id from connections where id = ?) " +
                        "and genres.id = (select genre_id from connections where id = ?) " +
                        "and authors.id = (select author_id from connections where id = ?)");

                preparedStatement2.setString(1, String.valueOf(id1));
                preparedStatement2.setString(2, String.valueOf(id1));
                preparedStatement2.setString(3, String.valueOf(id1));

                resultSet = preparedStatement2.executeQuery();
                PrintBook book = printBook(resultSet, connection);
                printBooks.add(book);
            }

            return printBooks;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrintBook> getAllBooks() throws SQLException {
        int i = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("select id from connections ");
            ResultSet resultSet = preparedStatement1.executeQuery();

            List<Long> connectionsId = getConnectionsId(resultSet, connection);

            List<PrintBook> printBooks = new ArrayList<>();

            for (Long id : connectionsId) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("select " +
                        "books.id, books.title, books.isbn, genres.name_genre, authors.name_author from books, genres, authors " +
                        "where books.id = (select book_id from connections where id = ?) " +
                        "and genres.id = (select genre_id from connections where id = ?) " +
                        "and authors.id = (select author_id from connections where id = ?)");
                preparedStatement2.setString(1, String.valueOf(id));
                preparedStatement2.setString(2, String.valueOf(id));
                preparedStatement2.setString(3, String.valueOf(id));
                resultSet = preparedStatement2.executeQuery();
                PrintBook book = printBook(resultSet, connection);
                printBooks.add(book);
            }

            return printBooks;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrintBook printBook(ResultSet rs, Connection connection) throws SQLException {
        PrintBook book = new PrintBook();
        while (rs.next()) {
            book.setId(rs.getLong(1));
            book.setTitle(rs.getString(2));
            book.setIsbn(rs.getLong(3));
            book.setGenre(rs.getString(4));
            book.setAuthor(rs.getString(5));

        }

        return book;
    }

    private List<PrintBook> printBooks(ResultSet rs, Connection connection) throws SQLException {
        List<PrintBook> books = new ArrayList<>();

        PrintBook book = new PrintBook();
        while (rs.next()) {
            book.setId(rs.getLong(1));
            book.setTitle(rs.getString(2));
            book.setIsbn(rs.getLong(3));
            book.setGenre(rs.getString(4));
            book.setAuthor(rs.getString(5));

            books.add(book);
        }

        return books;
    }

    private List<Long> getConnectionsId(ResultSet rs, Connection connection) throws SQLException {

        Long id = null;

        List<Long> idList = new ArrayList<>();

        while (rs.next()) {

            id = rs.getLong(1);

            idList.add(id);
        }

        return idList;
    }


}
