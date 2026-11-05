package fr.univ.tp.etudiants.repository;

import fr.univ.tp.etudiants.model.Etudiant;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stockage en mémoire (pas de base de données).
 * ConcurrentHashMap car plusieurs requêtes HTTP peuvent arriver en même temps.
 */
@Repository
public class EtudiantRepository {
    private final Map<Integer, Etudiant> store = new ConcurrentHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public List<Etudiant> findAll() {
        List<Etudiant> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparing(Etudiant::getId));
        return list;
    }

    public Optional<Etudiant> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public Etudiant save(Etudiant e) {
        if (e.getId() == null) e.setId(seq.getAndIncrement());
        store.put(e.getId(), e);
        return e;
    }

    public boolean existsById(int id) {
        return store.containsKey(id);
    }

    public void delete(int id) {
        store.remove(id);
    }
}
