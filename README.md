# TP2 – API REST distribuée avec Java (Spring Boot)

Deux microservices indépendants :

| Service | Port | Endpoints |
|---|---|---|
| ServiceEtudiants | 8081 | `GET/POST /etudiants`, `GET/PUT/DELETE /etudiants/{id}` |
| ServiceCours | 8082 | `GET/POST /cours`, `GET/PUT/DELETE /cours/{id}`, `GET /cours/enrichis` |

`GET /cours/enrichis` appelle ServiceEtudiants (RestTemplate) pour ajouter le nom de l'étudiant à chaque cours.

```
TP2/
├─ ServiceEtudiants/        projet Maven (model, repository, controller) + Dockerfile
├─ ServiceCours/            projet Maven (+ dto/EtudiantDto, service/EtudiantClient) + Dockerfile
├─ docker-compose.yml
├─ TP2.postman_collection.json
└─ requests.http
```

## Prérequis
JDK 17+, Maven 3+, Docker Desktop (pour la partie E), Postman.

## 1. Lancement local (sans Docker)

Terminal 1 :
```bash
cd ServiceEtudiants
mvn spring-boot:run
```
Terminal 2 :
```bash
cd ServiceCours
mvn spring-boot:run
```
(Ou ouvrir chaque projet dans IntelliJ et lancer la classe `...Application`.)

## 2. Lancement avec Docker (partie E)

```bash
cd ServiceEtudiants && mvn clean package -DskipTests && cd ..
cd ServiceCours     && mvn clean package -DskipTests && cd ..
docker-compose up --build
```
Les services restent accessibles sur `localhost:8081` et `localhost:8082`.
Pour simuler la panne : `docker stop service-etudiants` (relancer : `docker start service-etudiants`).

## 3. Tests (partie D)

Importer `TP2.postman_collection.json` dans Postman (Import → fichier) et exécuter les dossiers **dans l'ordre** (Run collection).
Arrêter ServiceEtudiants avant le dossier 5. Chaque requête vérifie automatiquement le code HTTP attendu.

| Scénario | Requête | Résultat attendu |
|---|---|---|
| 1 | `POST :8081/etudiants` | **201**, `{"id":1,"nom":"Sara","email":"sara@u.com"}` |
| 1 | `GET :8081/etudiants/99` | **404**, message « Etudiant 99 non trouvé » |
| 2 | `POST :8082/cours` | **201**, `{"id":1,"titre":"Prog 3","etudiantId":1}` |
| 3 | `GET :8082/cours/enrichis` | **200**, cours 1 → `"etudiantNom":"Sara"` |
| 4 | `DELETE :8081/etudiants/2` puis `/cours/enrichis` | **204**, puis cours 2 → `"INCONNU"` (étudiant introuvable) |
| 5 | Service arrêté, `/cours/enrichis` | **200**, tous `"INCONNU"` (ServiceEtudiants indisponible) |

Captures pour le rapport : `GET /etudiants`, `GET /cours`, `GET /cours/enrichis`.

## 4. Choix techniques

- **DTO local** (`EtudiantDto`) : ServiceCours n'importe pas le code de ServiceEtudiants, les deux projets restent indépendants.
- **URL configurable** : `etudiants.service.url` vaut `http://localhost:8081` en local et `http://etudiants:8081` dans Docker (dans un conteneur, `localhost` désigne le conteneur lui-même).
- **Codes HTTP** : 201 à la création, 204 à la suppression, 404 si l'id n'existe pas.
- **Timeouts de 2 s** sur le RestTemplate : un service lent ne bloque pas ServiceCours.

## 5. Réponses aux questions

**Que se passe-t-il si ServiceEtudiants est indisponible ?**
L'appel HTTP lève une `ResourceAccessException` (connexion refusée ou timeout). Sans `try/catch`, `/cours/enrichis` renverrait une erreur 500 : la panne d'un service se propagerait à l'autre. Avec la gestion d'erreur, ServiceCours continue de répondre (200) et remplace le nom par `INCONNU` (dégradation gracieuse).

**Comment rendre la communication plus résiliente ?**
- Timeouts (déjà en place) pour ne pas attendre indéfiniment.
- Retry : réessayer quelques fois avant d'abandonner.
- Circuit Breaker (Resilience4j) : après plusieurs échecs, on n'appelle plus le service pendant un moment et on répond directement avec un fallback.
- Cache des derniers étudiants connus, ou communication asynchrone par message broker (RabbitMQ, Kafka).
