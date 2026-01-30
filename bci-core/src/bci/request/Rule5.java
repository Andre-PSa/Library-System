package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule5 extends BorrowingRule{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule5(BorrowingRule nextRule){super(nextRule,5);}

    public boolean check(Work work, User user){
        return !work.getGenre().toString().equals("Referência");
    }
}
