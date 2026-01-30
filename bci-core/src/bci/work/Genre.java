package bci.work;

public enum Genre {
    REFERENCE("Referência"),
    FICTION("Ficção"),
    SCITECH("Técnica e Científica");

    private final String _printName;

    private Genre(String printName){
        _printName = printName;
    }

    @Override
    public String toString(){return _printName;}
}
