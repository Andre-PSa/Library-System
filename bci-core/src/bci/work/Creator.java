package bci.work;

import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

public class Creator implements Serializable{
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;
    
    private final String _name;
    private Map<String,Work> _works = new TreeMap<String,Work>(String.CASE_INSENSITIVE_ORDER);

    public Creator(String name){_name = name;}

    public Creator(String name, Work work){this(name);_works.put(work.getTitle(),work);}

    public boolean hasWorks(){return !_works.isEmpty();}

    public void addWork(Work work){_works.put(work.getTitle(),work);}
    public void rmWork(String workTitle){_works.remove(workTitle);}

    public String getName(){return _name;}

    public Map<String,Work> getWorks(){return _works;}
}
