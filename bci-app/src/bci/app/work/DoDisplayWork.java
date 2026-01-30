package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("workID",Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{_display.addLine(_receiver.displayWork(integerField("workID")));}
        catch(WrongInputException e){throw new NoSuchWorkException(integerField("workID"));}
    }

}
