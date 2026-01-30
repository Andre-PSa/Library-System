package bci.user;

import java.io.Serializable;

public class Notification implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private String _message;

    public Notification(String message){
        _message = message;
    }

    @Override
    public String toString(){
        return _message;
    }
}
