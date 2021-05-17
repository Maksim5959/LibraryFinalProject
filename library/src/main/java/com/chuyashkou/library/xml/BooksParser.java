package com.chuyashkou.library.xml;

import com.chuyashkou.library.models.Author;
import com.chuyashkou.library.models.Book;
import com.chuyashkou.library.models.Genre;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BooksParser {

    private static Document document;

    public ArrayList<Book> getBooks(File file) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(file);

        NodeList bookNodeList = document.getElementsByTagName("book");

        ArrayList<Book> bookList = new ArrayList<>();

        for (int i = 0; i < bookNodeList.getLength(); i++) {
            Element bookElement = (Element) bookNodeList.item(i);

            Book book = new Book();

            NodeList childNodes = bookElement.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) childNodes.item(j);

                    switch (childElement.getNodeName()) {
                        case "title":
                            book.setTitle(childElement.getTextContent());
                            break;
                        case "author":
                            book.setAuthor(new Author(childElement.getTextContent()));
                            break;
                        case "genre":
                            String genre = childElement.getTextContent().trim();
                            book.setGenre(Genre.valueOf(genre));
                            break;
                        case "isbn":
                            book.setIsbn(childElement.getTextContent());
                            break;
                    }
                }
            }
            bookList.add(book);
        }
        return bookList;
    }
}
