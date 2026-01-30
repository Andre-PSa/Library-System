package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
/**
 * 4.1.3. Advance the current date.
 */
class DoAdvanceDate extends Command<LibraryManager> {

    DoAdvanceDate(LibraryManager receiver) {
        super(Label.ADVANCE_DATE, receiver);
        addIntegerField("increment", Prompt.daysToAdvance());
    }

    @Override
    protected final void execute() {
        _receiver.advanceDate(integerField("increment"));
        /*FIXME  o sistema deve actualizar, caso seja necessário, outros aspectos que dela dependam,
        designadamente, a situação dos utentes relativa a prazos.*/
    }
}
