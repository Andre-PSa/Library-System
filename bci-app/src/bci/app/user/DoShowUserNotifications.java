package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("userID",Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{_receiver.showUserNotifications(integerField("userID")).forEach(_display::addLine);}
        catch (WrongInputException e){throw new NoSuchUserException(integerField("userID"));}
    }

}
