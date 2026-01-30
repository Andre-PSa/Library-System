package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule3 extends BorrowingRule{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule3(BorrowingRule nextRule){super(nextRule,3);}

    public boolean check(Work work,User user){
        return work.getAvailable()>0;
    }
}
