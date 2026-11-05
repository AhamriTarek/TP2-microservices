package fr.univ.tp.etudiants.controller;

import fr.univ.tp.etudiants.model.Etudiant;
import fr.univ.tp.etudiants.repository.EtudiantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantRepository repo;

    public EtudiantController(EtudiantRepository repo) {
        this.repo = repo;
    }

    // GET /etudiants -> 200
    @GetMapping
    public List<Etudiant> all() {
        return repo.findAll();
    }

    // GET /etudiants/{id} -> 200 ou 404
    @GetMapping("/{id}")
    public Etudiant get(@PathVariable int id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Etudiant " + id + " non trouvé"));
    }

    // POST /etudiants -> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Etudiant create(@RequestBody Etudiant e) {
        e.setId(null); // l'id est généré par le serveur
        return repo.save(e);
    }

    // PUT /etudiants/{id} -> 200 ou 404
    @PutMapping("/{id}")
    public Etudiant update(@PathVariable int id, @RequestBody Etudiant e) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etudiant " + id + " non trouvé");
        }
        e.setId(id);
        return repo.save(e);
    }

    // DELETE /etudiants/{id} -> 204 ou 404
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etudiant " + id + " non trouvé");
        }
        repo.delete(id);
    }
}
