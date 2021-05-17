package com.chuyashkou.library.services;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public interface BookService {

    void createBook() throws SQLException;

    void deleteBook() throws SQLException;

    void editBook() throws SQLException;

    void getBooks() throws SQLException;

    void addBooksFromXml() throws SQLException, ParserConfigurationException, SAXException, IOException;

}
