package org.global.academy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Stock {
    private String companyName;
    private String symbol;
    private String stockExchange;
    private double currentPrice;
    private double priceWhenBought;

    // Constructor
    public Stock(String companyName, String symbol, String stockExchange, double currentPrice) {
        this.companyName = companyName;
        this.symbol = symbol;
        this.stockExchange = stockExchange;
        this.currentPrice = currentPrice;
        this.priceWhenBought = 0.0;
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPriceWhenBought() {
        return priceWhenBought;
    }

    public void setPriceWhenBought(double priceWhenBought) {
        this.priceWhenBought = priceWhenBought;
    }

    // Method to display stock information
    public void displayInfo() {
        System.out.println("Company: " + companyName);
        System.out.println("Symbol: " + symbol);
        System.out.println("Stock Exchange: " + stockExchange);
        System.out.println("Current Price: $" + currentPrice);
    }

    // Method to lookup current price from Yahoo Finance
    public void lookupCurrentPrice() {
        String urlString = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol.toLowerCase();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            conn.disconnect();
            // Parse JSON
            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject meta = json.getAsJsonObject("chart").getAsJsonArray("result")
                                  .get(0).getAsJsonObject().getAsJsonObject("meta");
            double price = meta.get("regularMarketPrice").getAsDouble();
            this.currentPrice = price;
        } catch (Exception e) {
            System.err.println("Error fetching stock price: " + e.getMessage());
        }
    }
}
