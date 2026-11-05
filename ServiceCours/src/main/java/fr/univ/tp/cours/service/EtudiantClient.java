package fr.univ.tp.cours.service;

import fr.univ.tp.cours.dto.EtudiantDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Client HTTP vers ServiceEtudiants (communication inter-services synchrone).
 *
 * L'URL n'est pas écrite en dur : elle vient de la propriété "etudiants.service.url".
 *  - en local      : http://localhost:8081
 *  - dans Docker   : http://etudiants:8081 (nom du service docker-compose)
 */
@Service
public class EtudiantClient {

    private final RestTemplate rest;
    private final String baseUrl;

    public EtudiantClient(RestTemplate rest,
                          @Value("${etudiants.service.url}") String serviceUrl) {
        this.rest = rest;
        this.baseUrl = serviceUrl + "/etudiants";
    }

    /**
     * @throws org.springframework.web.client.HttpClientErrorException.NotFound si l'étudiant n'existe pas
     * @throws org.springframework.web.client.ResourceAccessException si ServiceEtudiants est injoignable
     */
    public EtudiantDto getEtudiantById(int id) {
        return rest.getForObject(baseUrl + "/" + id, EtudiantDto.class);
    }
}
