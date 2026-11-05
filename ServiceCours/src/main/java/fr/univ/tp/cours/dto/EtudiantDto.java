package fr.univ.tp.cours.dto;

/**
 * Copie locale de la structure "Etudiant" renvoyée par ServiceEtudiants.
 * On n'importe pas la classe de l'autre projet : chaque microservice reste indépendant.
 */
public class EtudiantDto {
    private Integer id;
    private String nom;
    private String email;

    public EtudiantDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
