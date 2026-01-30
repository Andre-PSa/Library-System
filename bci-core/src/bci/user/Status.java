package bci.user;

import java.io.Serializable;

public abstract class Status implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private User _user;

    public Status(User user){_user = user;}

    public User getUser(){return _user;}

    public abstract boolean isSuspended();
    
    public abstract void suspend();

    public abstract void unsuspend();

    public abstract String toString(int fine);
}
