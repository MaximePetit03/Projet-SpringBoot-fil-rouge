import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientBibliotheque {

    private static final String BASE_URL = "http://localhost:8082/api/v1";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static int nombreEtapes = 0;
    private static int nombreConformes = 0;


    private static HttpResponse<String> get(String chemin) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + chemin))
                .header("Accept", "application/json")
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> post(String chemin, String corpsJson) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + chemin))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpsJson))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> patch(String chemin, String corpsJson) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + chemin))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(corpsJson))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> delete(String chemin) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + chemin))
                .header("Accept", "application/json")
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    private static void afficher(
            String description,
            String methode,
            String chemin,
            HttpResponse<String> reponse,
            int codeAttendu) {

        nombreEtapes++;

        boolean conforme = reponse.statusCode() == codeAttendu;

        if (conforme) {
            nombreConformes++;
        }

        System.out.println();
        System.out.println("Étape " + nombreEtapes + " : " + description);

        System.out.println(
                methode + " " + chemin + " (attendu : " + codeAttendu + ")"
                 + " -> " + reponse.statusCode() + " (" + (conforme ? "CONFORME" : "NON CONFORME") + ")"
        );

        System.out.println("Réponse : " + reponse.body());
    }


    public static void main(String[] args) throws Exception {

        String chemin = "/livres/disponibles?size=3";
        HttpResponse<String> reponse = get(chemin);

        afficher(
                "Lister les 3 premiers livres disponibles", "GET",
                chemin, reponse, 200
        );


        chemin = "/livres/99";
        reponse = get(chemin);

        afficher(
                "Lire un livre inexistant", "GET",
                chemin, reponse, 404
        );


        chemin = "/auteurs";
        String corpsAuteur = """ {
                  "nom": "Verne",
                  "prenom": "Jules",
                  "nationalite": "Française"
                } """;
        reponse = post(chemin, corpsAuteur);

        afficher(
                "Créer l'auteur Jules Verne", "POST",
                chemin, reponse, 201
        );


        chemin = "/emprunts";
        String corpsEmprunt = """ {
                  "livreId": 2,
                  "emprunteur": "Alice Durand",
                  "emailEmprunteur": "alice.durand@email.com"
                } """;
        reponse = post(chemin, corpsEmprunt);

        afficher(
                "Emprunter le livre 2", "POST",
                chemin, reponse, 201
        );


        chemin = "/emprunts";
        String corpsEmpruntLivre8 = """ {
                  "livreId": 8,
                  "emprunteur": "Alice Durand",
                  "emailEmprunteur": "alice.durand@email.com"
                } """;
        reponse = post(chemin, corpsEmpruntLivre8);

        afficher(
                "Emprunter le livre 8 (stock à 0)", "POST",
                chemin, reponse, 409
        );


        chemin = "/emprunts/2/retour";
        reponse = patch(chemin, "");

        afficher(
                "Rendre l'emprunt 2", "PATCH",
                chemin, reponse, 200
        );


        chemin = "/emprunts/2/retour";
        reponse = patch(chemin, "");

        afficher(
                "Rendre l'emprunt 2 une deuxième fois", "PATCH",
                chemin, reponse, 409
        );


        chemin = "/auteurs/1";
        reponse = delete(chemin);

        afficher(
                "Supprimer l'auteur 1 (il a encore des livres)", "DELETE",
                chemin, reponse, 409
        );


        chemin = "/livres";
        String corpsLivreInvalide = """ {
                  "titre": "",
                  "isbn": "978-2-0000-0000-0",
                  "prix": -5,
                  "stock": 1,
                  "auteurId": 1
                } """;
        reponse = post(chemin, corpsLivreInvalide);

        afficher(
                "Créer un livre invalide (titre vide, prix négatif)", "POST",
                chemin, reponse, 400
        );


        chemin = "/auteurs/5";
        reponse = delete(chemin);

        afficher(
                "Supprimer l'auteur 5 (sans livre)", "DELETE",
                chemin, reponse, 204
        );


        System.out.println();
        System.out.println(
                "Bilan : " + nombreConformes + "/" + nombreEtapes
                        + " étapes conformes"
        );
    }
}