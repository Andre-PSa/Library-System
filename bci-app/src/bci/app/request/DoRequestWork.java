package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.CoreBorrowingRuleFailedException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import static bci.app.user.Prompt.userId;
import static bci.app.work.Prompt.workId;
//FIXME maybe import classes

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("UserID", userId());
        addIntegerField("WorkID", workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int workID = integerField("WorkID");
        int userID = integerField("UserID");
        try{
        _display.addLine(Message.workReturnDay(
            workID,
            _receiver.requestWork(userID,workID)
            ));
        }
        catch (WrongInputException e){
            switch(e.getInputType()){
                case "workId" -> throw new NoSuchWorkException(workID);
                case "userId" -> throw new NoSuchUserException(userID);
            }
        }
        catch (CoreBorrowingRuleFailedException e){
            if (e.getRuleNumber()==3){
                if (Form.confirm(Prompt.returnNotificationPreference())){
                    _receiver.addNotificationAvailable(userID,workID);
                }
            }
            else throw new BorrowingRuleFailedException(userID, workID, e.getRuleNumber());
        }
    }

}
