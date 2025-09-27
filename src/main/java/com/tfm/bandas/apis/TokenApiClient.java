package com.tfm.bandas.apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenApiClient {

    // ==================== Autenticación con Keycloak ====================
    private static boolean isTokenActive(String jwtToken) {
        if (jwtToken == null) return false;
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) return false;
            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            long exp = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readTree(payload).get("exp").asLong();
            long now = System.currentTimeMillis() / 1000;
            return exp > now;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getToken(String id, String pass, String keycloakHost,
                                  String realm, HttpClient client, boolean typePassword) throws IOException, InterruptedException {
        String filename;
        if(typePassword) {
            filename = "password_token.json";
        } else {
            filename = "credential_token.json";
        }
        TokenFileStore store = new TokenFileStore(filename);
        TokenFileStore.TokenRecord saved = store.read();

        if (saved != null
                && id.equals(saved.id)
                && pass.equals(saved.pass)
                && isTokenActive(saved.token)) {
            System.out.println("Token reutilizado desde fichero: " + filename);
            return saved.token;
        }

        String url = keycloakHost + "/realms/" + realm + "/protocol/openid-connect/token";
        String body;
        if (typePassword) {
            System.out.println("Solicitando nuevo token por password");
            body = "grant_type=password"
                    + "&client_id=frontend-local"
                    + "&username=" + id
                    + "&password=" + pass;
        } else {
            System.out.println("Solicitando nuevo token por client_credentials");
            body = "grant_type=client_credentials"
                    + "&client_id=" + id
                    + "&client_secret=" + pass;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String resp = response.body();
            String token = resp.split("\"access_token\":\"")[1].split("\"")[0];
            try {
                store.write(new TokenFileStore.TokenRecord(id, pass, token));
            } catch (IOException ignore) {
                // Si falla el guardado no bloqueamos el flujo.
            }
            return token;
        } else {
            throw new RuntimeException("Error obteniendo token: " + response.statusCode() + " " + response.body());
        }
    }
}
