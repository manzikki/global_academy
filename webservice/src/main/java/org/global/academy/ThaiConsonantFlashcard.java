package org.global.academy;

public class ThaiConsonantFlashcard extends Flashcard {
    private String symbol;          // e.g. ก
    private String thaiReading;     // e.g. กอ ไก่
    private String romanized;       // e.g. gor gai
    private String toneClass;       // e.g. Mid

    // Constructor
    public ThaiConsonantFlashcard(String symbol, String thaiReading,
                                  String romanized, String toneClass) {
        // Build the front and back text for the base Flashcard
        super(symbol + "  " + thaiReading,
              romanized + " (Tone: " + toneClass + ")");

        this.symbol = symbol;
        this.thaiReading = thaiReading;
        this.romanized = romanized;
        this.toneClass = toneClass;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getThaiReading() {
        return thaiReading;
    }

    public String getRomanized() {
        return romanized;
    }

    public String getToneClass() {
        return toneClass;
    }

    // Setters
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setThaiReading(String thaiReading) {
        this.thaiReading = thaiReading;
    }

    public void setRomanized(String romanized) {
        this.romanized = romanized;
    }

    public void setToneClass(String toneClass) {
        this.toneClass = toneClass;
    }

    @Override
    public String toString() {
        return "ThaiConsonantFlashcard{" +
                "symbol='" + symbol + '\'' +
                ", thaiReading='" + thaiReading + '\'' +
                ", romanized='" + romanized + '\'' +
                ", toneClass='" + toneClass + '\'' +
                '}';
    }
}
