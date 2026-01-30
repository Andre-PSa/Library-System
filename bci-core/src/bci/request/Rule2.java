package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule2 extends BorrowingRule{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule2(BorrowingRule nextRule){super(nextRule,2);}

    public boolean check(Work work, User user){
        return !user.getStatus().isSuspended();
    }
}
