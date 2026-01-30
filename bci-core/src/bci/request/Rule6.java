package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule6 extends BorrowingRule{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule6(BorrowingRule nextRule){super(nextRule,6);}

    public boolean check(Work work, User user){
        return user.getBehavior().toString().equals("CUMPRIDOR") || work.getPrice()<=25;
    }
}
