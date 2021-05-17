package com.chuyashkou.library.dao;

import com.chuyashkou.library.models.Book;
import com.chuyashkou.library.models.PrintBook;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    boolean createBook(Book book) throws SQLException;

    boolean deleteBook(Long id) throws SQLException;

    boolean editBook(Long id, int choice, String newName) throws SQLException;

    List<PrintBook> getAllBooks() throws SQLException;

    List<PrintBook> getBook(Long id) throws SQLException;

    boolean addBooksFromXml(String xml) throws SQLException, IOException, SAXException, ParserConfigurationException;

}
