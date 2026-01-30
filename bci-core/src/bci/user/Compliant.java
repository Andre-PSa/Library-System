package bci.user;

public class Compliant extends Behavior{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    public Compliant(User user){super(5,8,15,30,user);}
    
    public void update(boolean onTime){
        if (!onTime){
            getUser().setBehavior(new Normal(getUser(),-1));
        }
    }

    @Override
    public String toString(){
        return "CUMPRIDOR";
    }
}
