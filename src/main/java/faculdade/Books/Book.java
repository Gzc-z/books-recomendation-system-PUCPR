package Books;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private UUID ID;
    public String Name;
    public String Genre;
    public String Author;
    public String Set;


    public Book(String Name, String Genre, String Author, String Set) {
        this.ID = UUID.randomUUID();
        this.Name = Name;
        this.Genre = Genre;
        this.Author = Author;
        this.Set = Set;
    }

    public UUID getID(){
        return ID;
    }

    @Override
    public String toString(){
        return Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book b = (Book) o;
        return Objects.equals(Name, b.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }
}

