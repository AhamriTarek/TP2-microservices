package fr.univ.tp.cours.repository;

import fr.univ.tp.cours.model.Cours;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Stockage en mémoire, même principe que EtudiantRepository. */
@Repository
public class CoursRepository {
    private final Map<Integer, Cours> store = new ConcurrentHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public List<Cours> findAll() {
        List<Cours> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparing(Cours::getId));
        return list;
    }

    public Optional<Cours> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public Cours save(Cours c) {
        if (c.getId() == null) c.setId(seq.getAndIncrement());
        store.put(c.getId(), c);
        return c;
    }

    public boolean existsById(int id) {
        return store.containsKey(id);
    }

    public void delete(int id) {
        store.remove(id);
    }
}
