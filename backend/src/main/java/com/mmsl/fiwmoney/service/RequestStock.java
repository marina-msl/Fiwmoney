package com.mmsl.fiwmoney.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mmsl.fiwmoney.exception.StockException;
import com.mmsl.fiwmoney.model.Stock;

public class RequestStock {

    // public String get(String stock) {
    public Stock get(String stock) {

        String url = "https://brapi.dev/api/quote/" + stock
                + "?range=5d&interval=1d&fundamental=true&dividends=false&token=dm5Q62W9vhrM82bqq9SywZ";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.body().contains("Error")) {
            throw new StockException("Error: stock not found");
        }
        Gson gson = new GsonBuilder().create();

        // Stock stockRquested = new Stock(stock, currentAveragePrice, averagePrice);
        Stock stockRequested = gson.fromJson(response.body(), Stock.class);

        return stockRequested;
        // return response.body();
    }
}
