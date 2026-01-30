package bci.work;

import java.util.List;
import java.util.stream.Collectors;

public class Book extends Work{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final List<String> _authors;
    private final String _ISBN;

    public Book(String title, Genre genre, int id, int inventory, int price, List<String> authors, String ISBN) {
        super(title, genre, id, "Livro", inventory, price);
        _authors = authors;
        _ISBN = ISBN;
    }

    @Override
    public String toString(){
        String authors = _authors.stream().collect(Collectors.joining("; "));
        return super.toString()+ " - " + authors + " - " + _ISBN;
    }

    public boolean searchCreator(String prompt){
        return _authors.stream().anyMatch(author -> author.contains(prompt));
    }

    public List<String> getCreators(){
        return _authors;
    }
}
