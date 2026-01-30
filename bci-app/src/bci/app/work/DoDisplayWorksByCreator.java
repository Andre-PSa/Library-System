package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("Creator",Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{_receiver.displayWorksByCreator(stringField("Creator")).forEach(_display::addLine);}
        catch(WrongInputException e){throw new NoSuchCreatorException(stringField("Creator"));}
    }
}
