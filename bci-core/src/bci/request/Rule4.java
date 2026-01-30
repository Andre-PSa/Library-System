package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule4 extends BorrowingRule{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule4(BorrowingRule nextRule){super(nextRule,4);}

    public boolean check(Work work, User user){
        return user.canRequestWorks();
    }
}
