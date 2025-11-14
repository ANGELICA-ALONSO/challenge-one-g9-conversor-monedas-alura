package com.alura.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExchangeRateApiService implements ExchangeService {

    // API key (embedded here for demo; consider externalizing)
    private static final String API_KEY = "ebc035176d55dfa9931fd0e3";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public double convert(String base, String target, double amount) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/pair/" + base + "/" + target + "/" + amount;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        Pattern p = Pattern.compile("\"conversion_result\"\\s*:\\s*([-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?)");
        Matcher m = p.matcher(body);
        if (m.find()) {
            try {
                return Double.parseDouble(m.group(1));
            } catch (NumberFormatException e) {
                throw new IOException("No se pudo parsear conversion_result: " + e.getMessage());
            }
        }

        throw new IOException("Campo conversion_result no encontrado. Respuesta: " + body);
    }
}
