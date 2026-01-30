package bci.exceptions;

public class CoreBorrowingRuleFailedException extends Exception{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _ruleNumber;

    public CoreBorrowingRuleFailedException(int ruleNumber){_ruleNumber = ruleNumber;}

    public int getRuleNumber(){return _ruleNumber;}
}
