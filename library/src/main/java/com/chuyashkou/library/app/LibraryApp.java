package com.chuyashkou.library.app;

import com.chuyashkou.library.services.impl.BookServiceImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class LibraryApp {

    public static void startLibrary() {

        BookServiceImpl bookService = new BookServiceImpl();

        Scanner scanner = new Scanner(System.in);

        boolean exit = true;

        while (exit) {

            System.out.println("Добро пожаловать в библиотеку. ");

            while (exit) {
                System.out.println("Выберите действие:\n" +
                        "1. Добавить книгу.\n" +
                        "2. Добавить несколько книг из xml.\n" +
                        "3. Удалить книгу по ID.\n" +
                        "4. Вывести книгу(и).\n" +
                        "5. Редактировать книгу.\n" + "6. Выход.");
                String choiceStr = scanner.nextLine();

                try {

                    int choice = Integer.parseInt(choiceStr);

                    switch (choice) {
                        case 1:
                            bookService.createBook();
                            break;
                        case 2:
                            bookService.addBooksFromXml();
                            break;
                        case 3:
                            bookService.deleteBook();
                            break;
                        case 4:
                            bookService.getBooks();
                            break;
                        case 5:
                            bookService.editBook();
                            break;
                        case 6:
                            exit = false;
                            break;
                    }
                    if (choice < 1 || choice > 6) {
                        System.out.println("Выбор неверный, попробуйте еще раз: ");
                    }

                } catch (NumberFormatException | SQLException e) {
                    System.out.println("Неверный формат, попробуйте еще раз: ");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("Повторите ввод: ");
                    try {
                        bookService.getBooks();
                    } catch (SQLException e1) {
                    }
                }
            }
            System.out.println("До свидания. ");
        }
    }
}
