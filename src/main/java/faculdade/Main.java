import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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
        System.out.println("\nLivro -> recomendações ; níveis de recomendações");
        for (Map.Entry<Book, Set<Book>> b : graphBook.hashmap.entrySet()) {
            Map<Book, Float> distancias = Graph.djikstraSimples(graphBook.hashmap, b.getKey());
            System.out.println(b + "\n\t" + distancias + "\n");
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
                inner.get("Set"),
                Date.valueOf(inner.get("Publication_Date"))
                // inner.get("Publication_Date")
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

    public static Map<Book, Float> djikstraSimples(HashMap<Book, Set<Book>> grafo, Book origem) {
        Map<Book, Float> distancias = new HashMap<>();
        Queue<Book> fila = new LinkedList<>();
        distancias.put(origem, 0f);
        fila.add(origem);
        while (!fila.isEmpty()) {
            Book atual = fila.poll();
            float distanciaAtual = distancias.get(atual);
            for (Book vizinho : grafo.getOrDefault(atual, new HashSet<>())) {
                if (!distancias.containsKey(vizinho)) {
                    // com certeza existe algo mlr q isso
                    if (origem.Date.after(vizinho.Date)){
                        distancias.put(vizinho, distanciaAtual + 0.6f);
                    } else{
                        distancias.put(vizinho, distanciaAtual + 0.9f);
                    }
                    fila.add(vizinho);
                }
            }
        }
        distancias.remove(origem);
        return distancias;
    }
}
