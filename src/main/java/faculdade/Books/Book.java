package Books;

import java.util.Date;
import java.util.UUID;

public class Book {
    private UUID ID;
    public String Name;
    private String Genre;
    private String Author;
    private Date PublicationDate;
    private String[] Set;


    public Book() {
        // this.ID = UUID.randomUUID();
        // this.Name = Name;
        // this.Genre = Genre;
        // this.Author = Author;
        // this.PublicationDate = PublicationDate;
        // this.Set = Set
    }

    @Override
    public String toString(){
        return Name;
    }
}
