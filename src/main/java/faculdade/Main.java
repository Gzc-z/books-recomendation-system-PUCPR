import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Books.Book;
import UserIO.BooksIO;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Map<String, Map<String, String>> fromJson;
        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        Graph graphBook = new Graph();

        String interests = BooksIO.askInterests();

        // i've no idea what happened here
        try (FileReader json = new FileReader("books.json")) {
            fromJson = gson.fromJson(json, type);
            BookReference bookRef = new BookReference();

            Set<Book> recomendations = bookRef.bookRecomendations(fromJson, interests);
            Set<Book> recomendationsSnapshot = new HashSet<>(recomendations);
            for (Book BookSet : recomendationsSnapshot) {
                recomendations.remove(BookSet);
                graphBook.newGraph(BookSet, recomendations);
                recomendations.addAll(recomendationsSnapshot);
            }
            for (Map.Entry<Book, Set<Book>> b : graphBook.hashmap.entrySet()) {
                System.out.println(b + "\n");
                //last book:
                // System.out.println(bookRef.book.Name);
            }

            for (Book book : recomendations) {
                System.out.println("-".repeat(16));
                System.out.println("Coleção: " + book.Set);
                System.out.println(book.getID());
                System.out.println(book.Name);
                System.out.println(book.Author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BookReference {
    Book book;
    Set<Book> recomendations = new HashSet<>();

    public Set<Book> bookRecomendations(Map<String, Map<String, String>> fromJson, String interests){
        for (Map.Entry<String, Map<String, String>> entry : fromJson.entrySet()) {
            Map<String, String> inner = entry.getValue();
            if (inner.get("Set").equals(interests)){
                book = new Book(inner.get("Name"),
                    inner.get("Genre"),
                    inner.get("Author"),
                    inner.get("Set"));
                recomendations.add(book);
            }
        }
        return recomendations;
    }
}

class Graph{
    HashMap<Book, Set<Book>> hashmap = new HashMap<>();
    public HashMap<Book, Set<Book>> newGraph(Book book, Set<Book> recomendations){
        hashmap.put(book, recomendations);
        // System.out.println(hashmap + "\n");
        return hashmap;
    }
}

