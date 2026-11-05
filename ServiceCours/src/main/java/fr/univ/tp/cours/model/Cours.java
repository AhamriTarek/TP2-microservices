package fr.univ.tp.cours.model;

public class Cours {
    private Integer id;
    private String titre;
    private Integer etudiantId; // lien vers Etudiant (ServiceEtudiants)

    public Cours() {}

    public Cours(Integer id, String titre, Integer etudiantId) {
        this.id = id;
        this.titre = titre;
        this.etudiantId = etudiantId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public Integer getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Integer etudiantId) { this.etudiantId = etudiantId; }
}
