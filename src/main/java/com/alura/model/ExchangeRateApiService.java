package com.alura.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateApiService implements ExchangeService {

    // API key (embedded here for demo; consider externalizing)
    private static final String API_KEY = "ebc035176d55dfa9931fd0e3";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public double convert(String base, String target, double amount) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/pair/" + base + "/" + target + "/" + amount;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // Parse response JSON using Gson into DTO
        PairResponse pr;
        try {
            pr = gson.fromJson(body, PairResponse.class);
        } catch (Exception e) {
            throw new IOException("No se pudo parsear la respuesta JSON: " + e.getMessage() + "\nBody: " + body);
        }

        if (pr == null) {
            throw new IOException("Respuesta vacía de la API: " + body);
        }

        if (pr.result != null && pr.result.equalsIgnoreCase("error")) {
            String err = pr.errorType != null ? pr.errorType : "error desconocido";
            throw new IOException("API devolvió error: " + err + ". Mensaje: " + pr.message);
        }

        // conversion_result expected to be present
        if (pr.conversionResult == null) {
            throw new IOException("Campo conversion_result no encontrado en la respuesta: " + body);
        }

        return pr.conversionResult;
    }

    // DTO mapping fields from the ExchangeRate-API Pair response
    private static class PairResponse {
        public String result;
        @SerializedName("base_code")
        public String baseCode;
        @SerializedName("target_code")
        public String targetCode;
        @SerializedName("conversion_rate")
        public Double conversionRate;
        @SerializedName("conversion_result")
        public Double conversionResult;
        // optional error fields
        @SerializedName("error-type")
        public String errorType;
        public String message;
    }
}
