package bci.user;

public class NonCompliant extends Behavior{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    public NonCompliant(User user){super(1,2,2,2,user);}

    public void update(boolean onTime){
        if (onTime){
            changeConsecutiveResults(1);
            if (getConsecutiveResults()>=3){
                getUser().setBehavior(new Normal(getUser(),getConsecutiveResults()));
            }
        }
        else setConsecutiveResults(0);
    }

    @Override
    public String toString(){
        return "FALTOSO";
    }
}
