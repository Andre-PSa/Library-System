package bci.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import bci.work.Work;
import bci.request.Request;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public class User implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private String _name;
    private String _email;
    private final int _id;
    private Status _status = new Active(this);   
    private Behavior _behavior = new Normal(this);
    private Map<Integer,Request> _requests = new HashMap<Integer,Request>(); 
    private int _fine = 0;
    private List<Notification> _notifications = new ArrayList<Notification>();
    private Map<Integer,Work> _borrowedWorks = new HashMap<Integer,Work>();
    private Set<Integer> _overdueWorkIDs = new HashSet<Integer>();

    public User(int id, String name, String email) {
        this._id = id;
        this._name = name;
        this._email = email;
    }

    public void addWork(Work work) {
        _borrowedWorks.put(work.getId(),work);
    }

    public boolean hasWork(int workID){
        return _borrowedWorks.get(workID) != null;
    }

    public void returnWork(int workID,boolean onTime){
        _borrowedWorks.remove(workID);
        _behavior.update(onTime);
        if (!onTime){
            _overdueWorkIDs.remove(workID);
        }
    }

    public void addRequest(int workID,Request request){
        _requests.put(workID,request);
    }
    
    public void updateRequest(Work work,boolean onTime){
        if (!onTime){_status.suspend(); _overdueWorkIDs.add(work.getId());}
    }

    public void payFine() {
        _fine = 0;
        checkRemoveSuspension();
    }

    private void checkRemoveSuspension(){
        if (_fine==0 && _overdueWorkIDs.isEmpty()) _status.unsuspend();
    }

    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }

    public List<String> showNotifications() {
        List<String> notifs = _notifications.stream().map(Notification::toString).toList();
        _notifications.clear();
        return notifs;
    }

    public boolean canRequestWorks(){
        return _borrowedWorks.size()<_behavior.getMaxRequests();
    }

    public Status getStatus() {return _status;}

    public void setStatus(Status status) {_status = status;}

    public String getName(){return _name;}

    public void setName(String name){_name = name;}

    public int getId(){return _id;}

    public Behavior getBehavior(){return _behavior;}

    public void setBehavior(Behavior behavior){_behavior = behavior;}

    public String getEmail(){return _email;}

    public void setEmail(String email){_email=email;}

    public int getFine(){return _fine;}

    public void incrementFine(int increment){_fine+=increment;}

    @Override
    public String toString(){
        return _id + " - " + _name + " - " + _email + " - " + _behavior + " - " + _status.toString(_fine);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof User){
            User u = (User) o;
            return u._id==_id;
        }
        return false;
    }
}
