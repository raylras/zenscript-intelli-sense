package raylras.zen.dap.debugserver.runtime;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ObjectIdMap<T> {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final HashMap<T, Integer> reversedMap = new HashMap<>();
    private final HashMap<Integer, T> idMap = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);


    public T getById(int id) {
        try {
            lock.readLock().lock();
            return idMap.get(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public int tryGet(T obj) {
        try {
            lock.readLock().lock();
            Integer existing = reversedMap.get(obj);
            if (existing != null) {
                return existing;
            }
            return -1;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getOrPut(T obj) {
        int existing = tryGet(obj);
        if (existing >= 0) {
            return existing;
        }
        try {
            lock.writeLock().lock();
            int id = nextId.incrementAndGet();
            reversedMap.put(obj, id);
            idMap.put(id, obj);
            return id;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int remove(T obj) {
        try {
            lock.writeLock().lock();
            Integer id = reversedMap.remove(obj);
            if (id == null) {
                return -1;
            }
            idMap.remove(id);
            return id;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public T removeById(int id) {
        try {
            lock.writeLock().lock();
            T obj = idMap.remove(id);
            reversedMap.remove(obj);
            return obj;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void reset() {
        try {
            lock.writeLock().lock();
            this.idMap.clear();
            this.reversedMap.clear();
            this.nextId.set(1);
        } finally {
            lock.writeLock().unlock();
        }
    }


}
