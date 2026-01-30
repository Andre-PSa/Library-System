package bci.user;

public class Normal extends Behavior{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Normal(User user){super(3,3,8,15,user);}
    public Normal(User user,int consecutiveResults){this(user);setConsecutiveResults(consecutiveResults);}

    public void update(boolean onTime){
        if (onTime){
            if (getConsecutiveResults()>=0){
                changeConsecutiveResults(1);
                if (getConsecutiveResults()>=5){
                    getUser().setBehavior(new Compliant(getUser()));
                }
            }
            else setConsecutiveResults(0);
        }
        else if (getConsecutiveResults()<=0){
            changeConsecutiveResults(-1);
            if (getConsecutiveResults()<=-3){
                getUser().setBehavior(new NonCompliant(getUser()));
            }
        }
        else setConsecutiveResults(0);
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
}
