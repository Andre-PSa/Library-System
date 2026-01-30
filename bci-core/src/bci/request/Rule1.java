package bci.request;

import bci.user.User;
import bci.work.Work;

public class Rule1 extends BorrowingRule {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public Rule1(BorrowingRule nextRule){super(nextRule,1);}

    public boolean check(Work work, User user){
        return !user.hasWork(work.getId());
    }
}
