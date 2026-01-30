package bci;

import bci.exceptions.*;
import bci.user.User;
import bci.work.*;
import bci.request.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/** Class that represents the library as a whole. */
class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    private String _filename = null;
    private boolean _changed = false;
    private int _date = 1;
    private Map<Integer,User> _users = new HashMap<Integer,User>();
    private int lastUserId = 1;
    private Map<Integer,Work> _works = new HashMap<Integer,Work>();
    private int lastWorkId = 1;
    private Map<String,Creator> _creators = new HashMap<String,Creator>();
    private BorrowingRule _rule1 = new Rule1(null);
    private HashMap<String,Request> _requests = new HashMap<String,Request>();

    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * Each line in the file must be formatted as either a user or a work entry.
     * Supported types are USER, DVD, and BOOK.
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException if an entry is not recognized or is invalid
     * @throws IOException if an I/O error occurs while reading the file
     * @throws CoreUserRegistrationFailedException if user registration fails
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException, CoreUserRegistrationFailedException {
      try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){
        String line;
        while ((line = br.readLine())!= null){
          String lineParts[]=line.split(":");
          String type = lineParts[0];
          String fields[] = Arrays.copyOfRange(lineParts,1,lineParts.length);
          switch (type) {
            case "USER" -> registerUser(fields[0],fields[1]);
            case "DVD","BOOK" -> parseWork(type, fields);
          }
        }
        addRules();
      }
    }

    private void addRules(){
      _rule1 = new Rule1(
              new Rule2(
              new Rule3(
              new Rule4(
              new Rule5(
              new Rule6(null))))));
    }

    private void parseWork(String type, String fields[]) throws UnrecognizedEntryException{
      String title = fields[0];
      int price, inventory;
      try{price = Integer.parseInt(fields[2]);}
      catch(NumberFormatException e){throw new UnrecognizedEntryException("Invalid price: " + fields[2]);}
      try{inventory = Integer.parseInt(fields[5]);}
      catch(NumberFormatException e){throw new UnrecognizedEntryException("Invalid inventory: " + fields[5]);}
      Genre genre = switch (fields[3]) {
        case "FICTION" -> Genre.FICTION;
        case "REFERENCE" -> Genre.REFERENCE;
        case "SCITECH" -> Genre.SCITECH;
        default -> throw new UnrecognizedEntryException("Invalid genre: " + fields[3]);
      };

      switch (type){
        case "DVD":{
          DVD newWork = new DVD(title,genre,lastWorkId,inventory,price,fields[1],fields[4]);
          addCreator(List.of(fields[1]), newWork);
          _works.put(lastWorkId++,newWork);
          break;
        }

        case "BOOK": {
          List<String> authors = Arrays.stream(fields[1].split(",")).map(String::trim).toList();
          Book newWork = new Book(title,genre,lastWorkId,inventory,price,authors,fields[4]);
          addCreator(authors, newWork);
          _works.put(lastWorkId++,newWork);
          break;
        }
      }
    }

    private void addCreator(List<String> CreatorNames, Work work){
      for (String creatorName : CreatorNames){
        Creator creator = _creators.get(creatorName);
        if(creator!=null){creator.addWork(work);}
        else{_creators.put(creatorName,new Creator(creatorName,work));}
      }
    }

    /**
     * Saves the current state of the library to the associated file.
     *
     * @throws MissingFileAssociationException if no file is associated with the library
     * @throws IOException if an I/O error occurs during saving
     */
    void save() throws MissingFileAssociationException, IOException{
      if (_filename == null) throw new MissingFileAssociationException();
      try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
          oos.writeObject(this);
      }
      _changed = false;
    }

    /**
     * Returns the current date of the library system.
     *
     * @return the current date as an integer
     */
    int displayDate(){return _date;}

    /**
     * Advances the library system date by the specified increment.
     *
     * @param increment the number of days to advance
     */
    void advanceDate(int increment){
      if(increment>0){
        _changed=true;
        _requests.values().stream().forEach(r->r.update(_date+increment));
        _date+=increment;
      }
    }

    /**
     * Registers a new user in the library system.
     *
     * @param name the user's name
     * @param email the user's email address
     * @return the unique ID assigned to the new user
     * @throws CoreUserRegistrationFailedException if registration fails due to invalid data
     */
    int registerUser(String name, String email) throws CoreUserRegistrationFailedException{
      if (name.isEmpty() || email.isEmpty()){
        throw new CoreUserRegistrationFailedException();
      }
      _users.put(lastUserId,new User(lastUserId,name,email));
      _changed = true;
      return lastUserId++;
    }

    /**
     * Returns a string representation of the user with the given ID.
     *
     * @param userID the user's unique identifier
     * @return a string describing the user
     * @throws WrongInputException if the user ID is invalid
     */
    String showUser(int userID) throws WrongInputException{
      if(userID<lastUserId && userID>0) return _users.get(userID).toString();
      throw new WrongInputException("Incorrect User ID: " + userID,"userId");
    }

    /**
     * Returns a list of notifications for the specified user.
     *
     * @param userID the user's unique identifier
     * @return a list of notification strings for the user
     * @throws WrongInputException if the user ID is invalid
     */
    List<String> showUserNotifications(int userID) throws WrongInputException{
      if(userID<lastUserId && userID>0) {_changed=true;return _users.get(userID).showNotifications();}
      throw new WrongInputException("Incorrect User ID: " + userID,"userId");
    }

    /**
     * Returns a list of all users in the library system.
     *
     * The list is ordered alphabetically by user name (case-insensitive).
     *
     * @return a list of user string representations
     */
    List<String> showUsers(){
      return _users.values().stream().sorted(Comparator.comparing(User::getName,String.CASE_INSENSITIVE_ORDER)).map(User::toString).toList();
    }

    boolean hasWork(int workID){
      return workID<lastWorkId && workID>0 && _works.get(workID)!=null;
    }

    /**
     * Returns a string representation of the work with the given ID.
     *
     * @param workID the work's unique identifier
     * @return a string describing the work
     * @throws WrongInputException if the work ID is invalid
     */
    String displayWork(int workID) throws WrongInputException{
      if(hasWork(workID)) return _works.get(workID).toString();
      throw new WrongInputException("Incorrect Work ID: " + workID,"workId");
    }

    /**
     * Returns a list of all works in the library system.
     *
     * The list is ordered by work ID in ascending order.
     *
     * @return a list of work string representations
     */
    List<String> displayWorks(){
      return _works.values().stream().sorted(Comparator.comparing(Work::getId)).map(Work::toString).toList();
    }

    /**
     * Returns a list of works by the specified creator.
     *
     * The list contains string representations of works associated with the creator.
     *
     * @param CreatorName the name of the creator
     * @return a list of works by the creator
     * @throws WrongInputException if the creator does not exist
     */
    List<String> displayWorksByCreator(String CreatorName) throws WrongInputException{
      Creator creator = _creators.get(CreatorName);
      if (creator != null)return creator.getWorks().values().stream().map(Work::toString).toList();
      throw new WrongInputException("Incorrect Creator ID: " + CreatorName,"CreatorId");
    }

    /**
     * Returns the filename associated with the library for saving/loading.
     *
     * @return the filename as a string, or null if not set
     */
    String getFilename() {
        return _filename;
    }

    /**
     * Sets the filename to be associated with the library for saving/loading.
     *
     * @param filename the filename to associate
     */
    void setFilename(String filename) {
        _filename = filename;
    }

    /**
     * Returns whether the library state has changed since the last save.
     *
     * @return true if the library has unsaved changes, false otherwise
     */
    boolean getChanged(){return _changed;}

    List<String> performSearch(String prompt){
      return _works.values().stream().filter(Work -> Work.getTitle().contains(prompt)||Work.searchCreator(prompt)).map(Work::toString).collect(Collectors.toList());
    }

    int requestWork(int userID, int workID) throws WrongInputException,CoreBorrowingRuleFailedException{
      Work work;
      User user;

      if(hasWork(workID)) work=_works.get(workID);
      else throw new WrongInputException("Incorrect Work ID: " + workID,"workId");

      if(userID<lastUserId && userID>0) user=_users.get(userID);
      else throw new WrongInputException("Incorrect User ID: " + userID,"userId");

      int checkResult = _rule1.checkRule(work, user);
      if (checkResult!=0) throw new CoreBorrowingRuleFailedException(checkResult);

      int due_date = _date + user.getBehavior().getDueDate(work.getInventory());
      Request newRequest = new Request(user, work, due_date);
      _requests.put(userID+","+workID,newRequest);

      user.addRequest(workID,newRequest);
      user.addWork(work);
      work.requestWork();

      return due_date;
    }

    void addNotificationAvailable(int userID, int workID){
      _works.get(workID).addNotificationAvailable(_users.get(userID));
    }

    void changeWorkInventory(int workID, int amountToUpdate) throws WrongInputException,CoreImpossibleInventoryChange{
      Work work;
      if(hasWork(workID)) work = _works.get(workID);
      else throw new WrongInputException("Incorrect Work ID: " + workID,"workId");
      if(work.getInventory() + amountToUpdate>=0)work.changeInventory(amountToUpdate);
      else throw new CoreImpossibleInventoryChange();
      if (work.getInventory()==0){
        rmWork(work);
      }
    }

    private void rmWork(Work work){
      _works.remove(work.getId());
      List<String> workCreators = work.getCreators();
      workCreators.stream().forEach(creator -> _creators.get(creator).rmWork(work.getTitle()));
      workCreators.stream().filter(creator -> !_creators.get(creator).hasWorks()).forEach(creator-> _creators.remove(creator));
    }

    void payFine(int userID) throws CoreUserIsActiveException,WrongInputException{
      User user;
      if(userID<lastUserId && userID>0) user=_users.get(userID);
      else throw new WrongInputException("Incorrect User ID: " + userID,"userId");
      if(user.getFine()>0)user.payFine();
      else throw new CoreUserIsActiveException();
    }

    void userIncrementFine(int userID, int fine) throws WrongInputException{
      if(userID<lastUserId && userID>0)_users.get(userID).incrementFine(fine);
      else throw new WrongInputException("Incorrect User ID: " + userID,"userId");
    }

    int getUserFine(int userID) throws WrongInputException{
      if(userID<lastUserId && userID>0)return _users.get(userID).getFine();
      else throw new WrongInputException("Incorrect User ID: " + userID,"userId");
    }

    int returnWork(int userID, int workID) throws CoreWorkNotBorrowedByUserException,WrongInputException{
      if(!(hasWork(workID))) throw new WrongInputException("Incorrect Work ID: " + workID,"workId");

      if(!(userID<lastUserId && userID>0)) throw new WrongInputException("Incorrect User ID: " + userID,"userId");

      Request AssociatedRequest = _requests.get(userID+","+workID);
      if (AssociatedRequest!=null){
        AssociatedRequest.returnWork(_date);
        _requests.remove(userID+","+workID);
      }
      else throw new CoreWorkNotBorrowedByUserException();

      User user = _users.get(userID);
      user.incrementFine(AssociatedRequest.getFine());
      return user.getFine();
    }
}
