package bci.request;

import java.io.Serializable;

import bci.user.User;
import bci.work.Work;

public abstract class BorrowingRule implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private BorrowingRule _nextRule;
    private final int _ruleID;

    public BorrowingRule(BorrowingRule nextRule, int ruleID){
        _nextRule = nextRule;
        _ruleID = ruleID;
    }

    public void linkNext(BorrowingRule nextRule){
        _nextRule = nextRule;
    }

    public int checkRule(Work work, User user){
        if (!check(work,user)) return _ruleID;
        else if (_nextRule == null) return 0;       
        else return _nextRule.checkRule(work, user);
    }

    protected abstract boolean check(Work work, User user);
}