package fr.univ.tp.etudiants.model;

public class Etudiant {
    private Integer id;
    private String nom;
    private String email;

    public Etudiant() {}

    public Etudiant(Integer id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
