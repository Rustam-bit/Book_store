import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

class Book {
    String author;
    String title;
    String publisher;
    int year;
    double price;

    public Book(String author, String title, String publisher, int year, double price) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Книга: " + title + " (Автор: " + author + ", Издательство: " + publisher +
                ", Год: " + year + ", Цена: " + price + " руб.)";
    }
}

class Bookstore {
    private List<Book> books;
    private List<Order> orders;

    public Bookstore() {
        this.books = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public void processOrders() {
        for (Order order : orders) {
            List<Book> availableBooks = findBooks(order.getAuthor(), order.getTitle());

            if (!availableBooks.isEmpty()) {
                System.out.println("Книги найдены для заказа:");
                for (Book book : availableBooks) {
                    System.out.println(book);
                }
            } else {
                System.out.println("Книги недоступны. Оповестим вас, когда они поступят в магазин.");
            }
        }
        orders.clear(); // Очистим список заказов после обработки
    }

    public List<Book> findBooks(String author, String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ((author == null || book.author.equalsIgnoreCase(author)) &&
                    (title == null || book.title.equalsIgnoreCase(title))) {
                result.add(book);
            }
        }
        return result;
    }
}

class Order {
    private String author;
    private String title;

    public Order(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}

public class BookstoreGUI extends Application {
    Bookstore bookstore = new Bookstore();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        class BookstoreGUI extends Application {
            public static void main(String[] args) {
                launch(args);
            }

            @Override
            public void start(Stage primaryStage) {
                // Добавьте следующие строки:
                String javaVersion = System.getProperty("java.version");
                System.out.println("Java version: " + javaVersion);

                String javafxVersion = System.getProperty("javafx.version");
                System.out.println("JavaFX version: " + javafxVersion);

                {
                    primaryStage.setTitle("Книжный магазин");

                    GridPane grid = new GridPane();
                    grid.setPadding(new Insets(10, 10, 10, 10));
                    grid.setVgap(8);
                    grid.setHgap(10);

                    // Создаем элементы управления
                    Label titleLabel = new Label("Название:");
                    GridPane.setConstraints(titleLabel, 0, 0);

                    TextField titleInput = new TextField();
                    GridPane.setConstraints(titleInput, 1, 0);

                    Label authorLabel = new Label("Автор:");
                    GridPane.setConstraints(authorLabel, 0, 1);

                    TextField authorInput = new TextField();
                    GridPane.setConstraints(authorInput, 1, 1);

                    Button searchButton = new Button("Поиск");
                    GridPane.setConstraints(searchButton, 1, 2);
                    searchButton.setOnAction(e -> searchBooks(titleInput.getText(), authorInput.getText()));

                    TextArea resultArea = new TextArea();
                    resultArea.setEditable(false);
                    GridPane.setConstraints(resultArea, 0, 3, 2, 1);

                    // Добавляем элементы на сцену
                    grid.getChildren().addAll(titleLabel, titleInput, authorLabel, authorInput, searchButton, resultArea);

                    Scene scene = new Scene(grid, 300, 200);
                    primaryStage.setScene(scene);

                    primaryStage.show();
                }
            }

            private void searchBooks(String title, String author) {
                List<Book> result = bookstore.findBooks(title.isEmpty() ? null : title, author.isEmpty() ? null : author);

                if (result.isEmpty()) {
                    updateResultArea("Книги не найдены.");
                } else {
                    StringBuilder resultText = new StringBuilder("Результаты поиска:\n");
                    for (Book book : result) {
                        resultText.append("Книга: ").append(book.title)
                                .append(" (Автор: ").append(book.author)
                                .append(", Издательство: ").append(book.publisher)
                                .append(", Год: ").append(book.year)
                                .append(", Цена: ").append(book.price).append(" руб.)\n");
                    }
                    updateResultArea(resultText.toString());
                }
            }

            private void updateResultArea(String text) {
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Результат поиска");

                TextArea textArea = new TextArea(text);
                textArea.setEditable(false);

                Scene scene = new Scene(textArea, 300, 200);
                dialogStage.setScene(scene);

                dialogStage.show();
            }
        }
    }
}

