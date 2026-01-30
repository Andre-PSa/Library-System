package bci.user;

public class Active extends Status {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Active(User user){super(user);}

    public void suspend(){getUser().setStatus(new Suspended(getUser()));}

    public void unsuspend(){}

    public boolean isSuspended(){return false;}

    public String toString(int fine){
        return "ACTIVO";  
    }
}
