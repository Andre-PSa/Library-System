package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import bci.exceptions.CoreUserIsActiveException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userID", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.payFine(integerField("userID"));
        }
        catch(CoreUserIsActiveException e){throw new UserIsActiveException(integerField("userID"));}
        catch(WrongInputException e){throw new NoSuchUserException(integerField("userID"));}
    }
}
