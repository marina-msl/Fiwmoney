package com.mmsl.fiwmoney.application;

import java.util.Scanner;

import com.mmsl.fiwmoney.model.Stock;
import com.mmsl.fiwmoney.service.RequestStock;

/**
 * Application
 */
public class Application {

    public void display() {

        System.out.println("Enter the stock:");
        Scanner userInput = new Scanner(System.in);

        String stock = userInput.nextLine();

        System.out.println("Enter the current average price of your stock:");
        Double currentPrice = userInput.nextDouble();

        RequestStock requestStock = new RequestStock();
        Stock stockRequested = requestStock.get(stock);
        stockRequested.setCurrentPrice(currentPrice);
        
        System.out.println(stockRequested.toString());

        // Stock json = requestStock.get(stock);

        // Gson gson = new GsonBuilder().create();

        // gson.fromJson(json, Stock.class);

        // System.out.println();

        // Stock stockRquested = new Stock(stock, currentAveragePrice, averagePrice);

        // Stock model = gson.fromJson(json, Stock.class);



        userInput.close();
    }

}