import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private double price;
    private String ISBN;
    private int publishedYear;
    private boolean isNewArrival;

    public Book(String title, String author, double price, String ISBN, int publishedYear, boolean isNewArrival) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.ISBN = ISBN;
        this.publishedYear = publishedYear;
        this.isNewArrival = isNewArrival;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public boolean isNewArrival() {
        return isNewArrival;
    }
}

class Customer {
    private String name;
    private String email;
    private List<Order> orders;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}

class Order {
    private Book book;
    private int quantity;

    public Order(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public double TotalPrice() {
        return book.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "Order: " + book.getTitle() + " by " + book.getAuthor() +
               " (Quantity: " + quantity + ", Total Price: $" + TotalPrice() + ")";
    }
}

class Bookstore {
    public List<Book> books;
    private List<Customer> customers;

    public Bookstore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
        // Sample data - add new arrival books to the store
        books.add(new Book("New Arrival Book 1", "Author 1", 29.99, "ISBN111111", 2023, true));
        books.add(new Book("New Arrival Book 2", "Author 2", 34.99, "ISBN222222", 2023, true));
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void update(String ISBN, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                books.set(i, updatedBook);
                break;
            }
        }
    }

    public void removeBook(String ISBN) {
        books.removeIf(book -> book.getISBN().equals(ISBN));
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getNewArrivalBooks() {
        List<Book> newArrivals = new ArrayList<>();
        for (Book book : books) {
            if (book.isNewArrival()) {
                newArrivals.add(book);
            }
        }
        return newArrivals;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer findbyEmail(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }
}

public class BookstoreManagementSystem {
    public static void main(String[] args) {
        Bookstore bookstore = new Bookstore();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Bookstore Management System");
                System.out.println("1. Add Book");
                System.out.println("2. Update Book");
                System.out.println("3. Remove Book");
                System.out.println("4. Print All Books");
                System.out.println("5. Create Customer Account");
                System.out.println("6. Place Order");
                System.out.println("7. View Order History");
                System.out.println("8. View New Arrival Books");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading the integer input

                switch (choice) {
                    case 1:
                        System.out.print("Enter Book Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Price: $");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character after reading the double input
                        System.out.print("Enter ISBN: ");
                        String ISBN = scanner.nextLine();
                        System.out.print("Enter Published Year: ");
                        int publishedYear = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character after reading the integer input
                        System.out.print("Enter if new arrival: ");
                        boolean ina = scanner.nextBoolean();
                        Book newBook = new Book(title, author, price, ISBN, publishedYear, ina);
                        bookstore.addBook(newBook);
                        System.out.println("Book added successfully!");
                        break;
                    case 2:
                        // Update book
                        System.out.print("Enter ISBN of the book to update: ");
                        String updateISBN = scanner.nextLine();
                        Book bookToUpdate = null;
                        for (Book book : bookstore.getBooks()) {
                            if (book.getISBN().equals(updateISBN)) {
                                bookToUpdate = book;
                                break;
                            }
                        }
                        if (bookToUpdate != null) {
                            System.out.print("Enter Updated Book Title: ");
                            String updatedTitle = scanner.nextLine();
                            System.out.print("Enter Updated Author: ");
                            String updatedAuthor = scanner.nextLine();
                            System.out.print("Enter Updated Price: $");
                            double updatedPrice = scanner.nextDouble();
                            scanner.nextLine(); // Consume the newline character after reading the double input
                            System.out.print("Enter Updated Published Year: ");
                            int updatedPublishedYear = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character after reading the integer input
                            bookToUpdate = new Book(updatedTitle, updatedAuthor, updatedPrice, updateISBN, updatedPublishedYear, bookToUpdate.isNewArrival());
                            bookstore.update(updateISBN, bookToUpdate);
                            System.out.println("Book updated successfully!");
                        } else {
                            System.out.println("Book with ISBN " + updateISBN + " not found.");
                        }
                        break;
                    case 3:
                        // Remove book
                        System.out.print("Enter ISBN of the book to remove: ");
                        String removeISBN = scanner.nextLine();
                        bookstore.removeBook(removeISBN);
                        System.out.println("Book with ISBN " + removeISBN + " removed successfully!");
                        break;
                    case 4:
                        // Print all books
                        System.out.println("All Books:");
                        for (Book book : bookstore.getBooks()) {
                            System.out.println("Title: " + book.getTitle());
                            System.out.println("Author: " + book.getAuthor());
                            System.out.println("Price: $" + book.getPrice());
                            System.out.println("ISBN: " + book.getISBN());
                            System.out.println("Published Year: " + book.getPublishedYear());
                            System.out.println("New Arrival: " + (book.isNewArrival() ? "Yes" : "No"));
                            System.out.println("-------------------------");
                        }
                        break;
                    case 5:
                        // Create customer account
                        System.out.print("Enter Customer Name: ");
                        String custname = scanner.nextLine();
                        System.out.print("Enter Customer Email: ");
                        String custmail = scanner.nextLine();
                        Customer newCustomer = new Customer(custname, custmail);
                        bookstore.addCustomer(newCustomer);
                        System.out.println("Customer account created successfully!");
                        break;
                    case 6:
                        System.out.print("Enter Customer Email: ");
                        String custemail = scanner.nextLine();
                        Customer customer = bookstore.findbyEmail(custemail);
                        if (customer != null) {
                            System.out.print("Enter Book ISBN: ");
                            String orderISBN = scanner.nextLine();
                            Book orderBook = null;
                            for (Book b : bookstore.getBooks()) {
                                if (b.getISBN().equals(orderISBN)) {
                                    orderBook = b;
                                    break;
                                }
                            }
                            if (orderBook != null) {
                                System.out.print("Enter Quantity: ");
                                int quantity = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character after reading the integer input
                                Order order = new Order(orderBook, quantity);
                                customer.addOrder(order);
                                System.out.println("Order placed successfully!");
                            } else {
                                System.out.println("Book with ISBN " + orderISBN + " not found.");
                            }
                        } else {
                            System.out.println("Customer with email " + custemail + " not found.");
                        }
                        break;
                    case 7:
                        System.out.print("Enter Customer Email: ");
                        String custmailhis = scanner.nextLine();
                        Customer custhis = bookstore.findbyEmail(custmailhis);
                        if (custhis != null) {
                            List<Order> orders = custhis.getOrders();
                            if (!orders.isEmpty()) {
                                System.out.println("Order History for " + custhis.getName() + ":");
                                for (Order order : orders) {
                                    System.out.println(order);
                                }
                            } else {
                                System.out.println("No orders found for " + custhis.getName() + ".");
                            }
                        } else {
                            System.out.println("Customer with email " + custmailhis + " not found.");
                        }
                        break;
                    case 8:
                        // View new arrival books
                        List<Book> newArrivalBooks = bookstore.getNewArrivalBooks();
                        if (!newArrivalBooks.isEmpty()) {
                            System.out.println("New Arrival Books:");
                            for (Book book : newArrivalBooks) {
                                System.out.println("Title: " + book.getTitle());
                                System.out.println("Author: " + book.getAuthor());
                                System.out.println("Price: $" + book.getPrice());
                                System.out.println("ISBN: " + book.getISBN());
                                System.out.println("Published Year: " + book.getPublishedYear());
                                System.out.println("-------------------------");
                            }
                        } else {
                            System.out.println("No new arrival books at the moment.");
                        }
                        break;
                    case 9:
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input to avoid an infinite loop
            } 
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Consume the invalid input to avoid an infinite loop
            }
        }
    }
}