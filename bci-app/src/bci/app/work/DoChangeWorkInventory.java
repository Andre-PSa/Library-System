package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.CoreImpossibleInventoryChange;
import bci.exceptions.WrongInputException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("workID", Prompt.workId());
        addIntegerField("amountToUpdate", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {
        int workID = integerField("workID");
        int amountToUpdate = integerField("amountToUpdate");
        try{
            _receiver.changeWorkInventory(workID,amountToUpdate);
        }
        catch(WrongInputException e){throw new NoSuchWorkException(integerField("workID"));}
        catch(CoreImpossibleInventoryChange e){_display.addLine(Message.notEnoughInventory(workID,amountToUpdate));}
    }

}
