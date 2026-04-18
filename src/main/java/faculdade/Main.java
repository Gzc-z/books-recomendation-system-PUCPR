import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Product.Book;

// i hate java
public class Main {
    public static void main(String[] args) {



        Book book;
        for (String name : bookNames) {
            book = new Book(name);
        }

        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

        // Set<Book> relationShip = new HashSet<>();
        // relationShip.add(book);

        graph graphBook = new graph();
        // graphBook.newGraph().put(book, relationShip);

        for (Map.Entry<Book, Set<Book>> b : graphBook.newGraph().entrySet()) {
            System.out.println(b);
        }
    }
}

class graph{
    public HashMap<Book, Set<Book>> newGraph(){
        HashMap<Book, Set<Book>> hashmap = new HashMap<>();
        return hashmap;
    }
}
