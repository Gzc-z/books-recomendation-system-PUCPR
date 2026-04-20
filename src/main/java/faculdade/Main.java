import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Books.Book;
import UserIO.BooksIO;

public class Main {
    public static void main(String[] args) {


        String quaso = BooksIO.askInterests();
        System.out.println(quaso);

        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        try (FileReader file = new FileReader("books.json")) {
            Map<String, Map<String, String>> response = gson.fromJson(file, type);

            for (Map.Entry<String, Map<String, String>> entry : response.entrySet()) {
                Map<String, String> inner = entry.getValue();
                System.out.println(inner.get("Set"));
                System.out.println("-".repeat(16));
             }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set<Book> relationShip = new HashSet<>();
        // relationShip.add(book);


        // graph graphBook = new graph();
        // graphBook.newGraph().put();

        // for (Map.Entry<Book, Set<Book>> b : graphBook.newGraph().entrySet()) {
        //     System.out.println(b);
        // }
    }
}

class graph{
    public HashMap<Book, Set<Book>> newGraph(){
        HashMap<Book, Set<Book>> hashmap = new HashMap<>();
        return hashmap;
    }
}


class Response {
    List<Book> books;
}
