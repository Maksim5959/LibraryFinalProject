package com.chuyashkou.library.services.impl;

import com.chuyashkou.library.dao.BookDao;
import com.chuyashkou.library.dao.impl.BookDaoImpl;
import com.chuyashkou.library.models.Author;
import com.chuyashkou.library.models.Book;
import com.chuyashkou.library.models.Genre;
import com.chuyashkou.library.models.PrintBook;
import com.chuyashkou.library.services.BookService;
import com.chuyashkou.library.xml.BooksValidator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public void createBook() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название книги: ");
        String title = scanner.nextLine();

        //Выбор жанра
        GenreServiceImpl genreService = new GenreServiceImpl();
        Genre genre = genreService.createGenre();

        //Ввод автора
        AuthorServiceImpl authorService = new AuthorServiceImpl();
        Author author = authorService.createAuthor();

        Book book = new Book(title, genre, author);

        bookDao.createBook(book);
    }

    @Override
    public void deleteBook() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id книги: ");
        String strId = scanner.nextLine();
        Long id;
        while (true) {
            try {
                id = Long.parseLong(strId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ввод неверный, попробуйте еще раз: ");
            }
        }
        bookDao.deleteBook(id);
    }

    @Override
    public void editBook() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id книги: ");
        String strId = scanner.nextLine();
        Long id;
        while (true) {
            try {
                id = Long.parseLong(strId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ввод неверный, попробуйте еще раз: ");
            }
        }
        System.out.println("Что вы хотите изменить?\n1.Имя книги.\n2.Имя автора.\n3.Жанр.\nВыберите пункт изменения.");
        String strChoice = scanner.nextLine();
        try {
            int choice = Integer.parseInt(strChoice);
            switch (choice) {
                case 1:
                    System.out.println("Введите новое имя книги: ");
                    String nameBook = scanner.nextLine();
                    bookDao.editBook(id, choice, nameBook);
                    break;
                case 2:
                    System.out.println("Введите новое имя автора: ");
                    String nameAuthor = scanner.nextLine();
                    bookDao.editBook(id, choice, nameAuthor);
                    break;
                case 3:
                    System.out.println("Выберите новый жанр: ");
                    GenreServiceImpl genreService = new GenreServiceImpl();
                    String nameGenre = genreService.createGenre().getName();
                    bookDao.editBook(id, choice, nameGenre);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод, попробуйте еще раз.");
        }
    }

    @Override
    public void getBooks() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Сделайте выбор:\n1.Вернуть книгу по id.\n2.Вернуть все книги.");


        while (true) {
            String strChoice = scanner.nextLine();
            try {
                int choice = Integer.parseInt(strChoice);
                switch (choice) {
                    case 1:
                        System.out.println("Введите id книги: ");

                        String strId = scanner.nextLine();

                        Long id;

                        while (true) {
                            try {
                                id = Long.parseLong(strId);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Ввод неверный, попробуйте еще раз: ");
                            }
                        }
                        List<PrintBook> book = bookDao.getBook(id);
                        for (PrintBook book1 : book) {
                            System.out.println(book1);
                        }
                        break;
                    case 2:
                        List<PrintBook> allBooks = bookDao.getAllBooks();
                        for (PrintBook books : allBooks) {
                            System.out.println(books.toString());
                        }
                        break;
                }
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Неверный ввод, попробуйте еще раз: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод, попробуйте еще раз: ");
            }
        }
    }

    @Override
    public void addBooksFromXml() throws SQLException, ParserConfigurationException, SAXException, IOException {
        String xml = "C:\\ReserveFile\\FreeIT\\library\\src\\main\\java\\com\\chuyashkou\\library\\files\\books.xml";
        String xsd = "C:\\ReserveFile\\FreeIT\\library\\src\\main\\java\\com\\chuyashkou\\library\\files\\validatorSchema.xsd";

        BooksValidator booksValidator = new BooksValidator();

        if (booksValidator.validateXML(xml, xsd)) {
            bookDao.addBooksFromXml(xml);
        } else {
            System.out.println("Файл не соответствует. ");
        }


    }
}
