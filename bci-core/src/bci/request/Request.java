package bci.request;

import bci.user.*;
import bci.work.Work;
import java.io.Serializable;

public class Request implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private User _user;
    private Work _work;
    private int _dueDate;
    private int _fine = 0;

    public Request(User user, Work work, int dueDate){
        _user = user;
        _work = work;
        _dueDate = dueDate;
    }

    public void update(int date){
        if (date > _dueDate){
            _user.updateRequest(_work,false);
            _fine+=(date-_dueDate)*5;
        }
    }

    public void returnWork(int date){
        boolean onTime = true;
        if (date > _dueDate) onTime = false;
        _user.returnWork(_work.getId(),onTime);
        _work.returnWork();
    }

    public int getFine(){return _fine;}
}
