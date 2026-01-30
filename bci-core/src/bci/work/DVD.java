package bci.work;

import java.util.List;

public class DVD extends Work{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final String _director;
    private final String _IGAC;

    public DVD(String title, Genre genre, int id, int inventory, int price, String director, String IGAC) {
        super(title, genre, id, "DVD", inventory, price);
        _director = director;
        _IGAC = IGAC;
    }

    @Override
    public String toString(){
        return super.toString()+ " - " + _director + " - " + _IGAC;
    }

    public boolean searchCreator(String prompt){
        return _director.contains(prompt);
    }

    public List<String> getCreators(){
        return List.of(_director);
    }
}
