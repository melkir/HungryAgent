package agents;

import java.util.HashSet;
import java.util.Set;

public class SharedSpace<T extends Agent> {
    private Set<T> space = new HashSet<T>();
    private HashSet<T> historySpace = new HashSet<T>();

    public synchronized T take(T entity) {
        while (true) {
            for (T t : space)
                if (t.getType().equals(entity.getType())) {
                    space.remove(t);
                    return t;
                }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void add(T entity) {
        if (!historySpace.contains(entity)) {
            historySpace.add(entity);
            space.add(entity);
            notifyAll();
        }
    }
}