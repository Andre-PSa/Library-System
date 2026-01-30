package bci;

import bci.exceptions.*;

import java.io.*;
//FIXME maybe import classes
import java.util.List;

/**
 * The façade class.
 */
public class LibraryManager {

    /** The object doing all the actual work. */
    private Library _library = new Library(/*_defaultRules*/);

    //FIXME maybe define constructors

    public void save() throws MissingFileAssociationException, IOException {
        _library.save();
    }

    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        _library.save();
    }

    public void load(String filename) throws UnavailableFileException {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            Library newLibrary = (Library) ois.readObject();
            newLibrary.setFilename(filename);
            _library = newLibrary;
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }
    }


    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
      } catch (IOException | UnrecognizedEntryException | CoreUserRegistrationFailedException e) {
        throw new ImportFileException(filename, e);
      }
    }

    public int displayDate(){return _library.displayDate();}
    
    public void advanceDate(int increment){_library.advanceDate(increment);}

    public int registerUser(String name, String email) throws CoreUserRegistrationFailedException{
        return _library.registerUser(name, email);
    }

    public String showUser(int UserID) throws WrongInputException{
      return _library.showUser(UserID);
    }

    public List<String> showUserNotifications(int UserID) throws WrongInputException{
      return _library.showUserNotifications(UserID);
    }

    public List<String> showUsers(){
      return _library.showUsers();
    }

    public String displayWork(int WorkID) throws WrongInputException{
      return _library.displayWork(WorkID);
    }

    public List<String> displayWorks(){
      return _library.displayWorks();
    }

    public List<String> displayWorksByCreator(String Creator) throws WrongInputException{
      return _library.displayWorksByCreator(Creator);
    }

    public boolean getChanged(){return _library.getChanged();}

    public List<String> performSearch(String prompt){
      return _library.performSearch(prompt);
    }

    public int requestWork(int userID, int workID) throws WrongInputException,CoreBorrowingRuleFailedException{
      return _library.requestWork(userID,workID);
    }

    public void changeWorkInventory(int workID, int amountToUpdate) throws WrongInputException,CoreImpossibleInventoryChange{
      _library.changeWorkInventory(workID,amountToUpdate);
    }

    public void payFine(int userID) throws CoreUserIsActiveException, WrongInputException{
      _library.payFine(userID);
    }

    public void userIncrementFine(int userID, int fine) throws WrongInputException{
      _library.userIncrementFine(userID,fine);
    }

    public int getUserFine(int userID)throws WrongInputException{
      return _library.getUserFine(userID);
    }

    public int returnWork(int userID, int workID) throws WrongInputException, CoreWorkNotBorrowedByUserException{
      return _library.returnWork(userID,workID);
    }

    public void addNotificationAvailable(int userID, int workID){
      _library.addNotificationAvailable(userID, workID);
    }
}
