package bci.work;

import bci.user.User;
import bci.user.Notification;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import java.util.List;

public abstract class Work implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _title;
    private final Genre _genre;
    private final int _id;
    private final String _workType;
    private int _inventory;
    private int _availableCopies;
    private int _price;
    private Map<Integer,User> _notificationsAvailable = new HashMap<Integer,User>();
    private Map<Integer,User> _notificationsBorrow = new HashMap<Integer,User>();

    public Work(String title, Genre genre, int id, String type, int inventory, int price) {
        _title = title;
        _id = id;
        _workType = type;
        _genre = genre;
        _inventory = inventory;
        _availableCopies = inventory;
        _price = price;
    }

    public String getTitle(){return _title;}
    public Genre getGenre(){return _genre;}
    public int getId(){return _id;}
    public String getType(){return _workType;}
    public int getInventory(){return _inventory;}
    public int getAvailable(){return _availableCopies;}
    public int getPrice(){return _price;}

    public void changeInventory(int amountToUpdate) {
        if(_inventory + amountToUpdate>=0){
            _inventory+=amountToUpdate;
            _availableCopies+=amountToUpdate;
        }
    }

    public void requestWork(){
        _availableCopies--;
        _notificationsBorrow.values().forEach(user -> user.addNotification(new Notification("REQUISIÇÃO: "+this.toString())));
    }

    public void returnWork(){
        _availableCopies++;
        if (_availableCopies == 1){
            _notificationsAvailable.values().forEach(user -> user.addNotification(new Notification("DISPONIBILIDADE: "+this.toString())));
        }
    }

    public void addNotificationBorrow(User user){
        _notificationsBorrow.put(user.getId(),user);
    }

    public void addNotificationAvailable(User user){
        _notificationsAvailable.put(user.getId(),user);
    }

    public void rmNotificationBorrow(int userID){
        _notificationsBorrow.remove(userID);
    }

    public void rmNotificationAvailable(int userID){
        _notificationsAvailable.remove(userID);
    }

    @Override
    public String toString(){
        return _id + " - " + _availableCopies + " de " + _inventory + " - " + _workType + " - " + _title + " - " + _price + " - " + _genre;
    }

    public abstract boolean searchCreator(String prompt);

    public abstract List<String> getCreators();

    @Override
    public boolean equals(Object o){
        if (o instanceof Work){
            Work w = (Work) o;
            return w._id==_id;
        }
        return false;
    }
}

