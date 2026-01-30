package bci.app.main;

import java.io.IOException;

import bci.LibraryManager;
import bci.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() {
        try {_receiver.save();}
        catch (MissingFileAssociationException e) {
			try {_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));}
            catch(MissingFileAssociationException | IOException e2) {e2.printStackTrace();}
		}
        catch (IOException e) {e.printStackTrace(); }
	}
}
