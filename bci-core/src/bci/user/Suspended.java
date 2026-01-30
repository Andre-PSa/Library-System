package bci.user;

public class Suspended extends Status{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Suspended(User user){super(user);}

    public boolean isSuspended(){return true;}

    public void suspend(){}

    public void unsuspend(){getUser().setStatus(new Active(getUser()));}

    public String toString(int fine){
        return "SUSPENSO - EUR " + fine;  
    }
}
