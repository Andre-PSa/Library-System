package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.exceptions.CoreUserIsActiveException;
import bci.exceptions.CoreWorkNotBorrowedByUserException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import static bci.app.user.Prompt.userId;
import static bci.app.work.Prompt.workId;
//FIXME maybe import classes

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("UserID",userId());
        addIntegerField("WorkID",workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int workID = integerField("WorkID");
        int userID = integerField("UserID");
        try{
            int fine = _receiver.returnWork(userID,workID);
            if (fine > 0){
                _display.addLine(Message.showFine(userID, fine));
                _display.display();
                if(Form.confirm(Prompt.finePaymentChoice())) _receiver.payFine(userID);
            }
        }
        catch(WrongInputException e){
            switch(e.getInputType()){
                case "workId" -> throw new NoSuchWorkException(workID);
                case "userId" -> throw new NoSuchUserException(userID);
            }
        }
        catch(CoreWorkNotBorrowedByUserException e){throw new WorkNotBorrowedByUserException(workID, userID);}
        catch(CoreUserIsActiveException e){throw new UserIsActiveException(userID);}
    }

}
