package com.chuyashkou.library;

import com.chuyashkou.library.app.LibraryApp;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException, IOException, SAXException, ParserConfigurationException {

        LibraryApp.startLibrary();

    }
}
