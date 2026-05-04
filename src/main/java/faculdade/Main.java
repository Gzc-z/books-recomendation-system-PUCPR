import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Books.Book;

public class Main {
    static Scanner scan = new Scanner(System.in);

    private static BufferedReader FileReader() throws IOException {
        return new BufferedReader(new FileReader("books.json"));
    }
    static Map<Integer, String> interestsMap = Map.of(
        1, "Senhor dos Pastéis",
        2, "Jogos Ferozes",
        3, "Mistborn"
    );

    public static String askInterests(){
        System.out.println("wat are your interests?");
        System.out.println("1. Senhor dos Pastéis");
        System.out.println("2. Jogos Ferozes");
        System.out.println("3. Mistborn");

        return interestsMap.get(scan.nextInt());
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        Map<String, Map<String, String>> fromJson;
        Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();

        Graph graphBook = new Graph();
        String interests = askInterests();
        Set<Book> recomendations = null; // i shouldn't use null as default value :\

        try (BufferedReader json = FileReader()) {
            fromJson = gson.fromJson(json, type);
            // recomendations = bookRef.bookRecomendations(fromJson, interests);
            recomendations = new BookReference().bookRecomendations(fromJson, interests);

        } catch (Exception e) {
            e.getMessage();
            System.exit(1);
        }

        Set<Book> recomendationsSnapshot = new HashSet<>(recomendations);
        for (Book BookSet : recomendationsSnapshot) {
            recomendations.remove(BookSet);
            graphBook.putInGraph(BookSet, recomendations);
            recomendations.addAll(recomendationsSnapshot);
        }
        for (Map.Entry<Book, Set<Book>> b : graphBook.hashmap.entrySet()) {
            System.out.println(b);
        }
    }
}

class BookReference {
    Set<Book> recomendations = new HashSet<>();
    Map<String, String> inner;
    Book book;

    public Set<Book> bookRecomendations(Map<String, Map<String, String>> fromJson, String interests){
        for (Map.Entry<String, Map<String, String>> entry : fromJson.entrySet()) {
            inner = entry.getValue();
            book = new Book(
                inner.get("Name"),
                inner.get("Genre"),
                inner.get("Author"),
                inner.get("Set")
            );
            if (inner.get("Set").equals(interests)){
                recomendations.add(book);
            }
        }
        return recomendations;
    }
}

class Graph{
    HashMap<Book, Set<Book>> hashmap = new HashMap<>();
    public HashMap<Book, Set<Book>> putInGraph(Book book, Set<Book> recomendations){
        hashmap.put(book, new HashSet<>(recomendations));
        return hashmap;
    }
}
