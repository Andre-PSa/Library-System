package bci.user;

import java.io.Serializable;

public abstract class Behavior implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private User _user;
    private int _consecutiveResults;
    private int _maxRequests;
    private int _dueDateSingleCopy;
    private int _dueDateFewCopies;
    private int _dueDateManyCopies;

    public Behavior(int maxRequests, int dueDateSingleCopy, int dueDateFewCopies, int dueDateManyCopies,User user){
        _maxRequests = maxRequests;
        _dueDateSingleCopy = dueDateSingleCopy;
        _dueDateFewCopies = dueDateFewCopies;
        _dueDateManyCopies = dueDateManyCopies;
        _user = user;
    }

    public int getMaxRequests(){ return _maxRequests; }

    public int getDueDate(int copies){
        if (copies == 1) return _dueDateSingleCopy;
        if (copies > 5) return _dueDateManyCopies;
        return _dueDateFewCopies;
    }

    public int getConsecutiveResults(){return _consecutiveResults;}

    public void setConsecutiveResults(int consecutiveResults){_consecutiveResults = consecutiveResults;}

    public void changeConsecutiveResults(int increment){_consecutiveResults+=increment;}

    public User getUser(){return _user;}

    public void setUser(User user){_user = user;}

    public abstract void update(boolean onTime);

    @Override
    public abstract String toString();
}
