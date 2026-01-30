package bci.exceptions;

/*Class encoding user input issues*/
public class WrongInputException extends Exception{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _inputSpecification;
    private final String _inputType;

    public WrongInputException(String inputSpecification,String inputType) {
        _inputSpecification = inputSpecification;
        _inputType = inputType;
    }

    public String getinputSpecification() {
        return _inputSpecification;
    }

    public String getInputType() {
        return _inputType;
    }
}
