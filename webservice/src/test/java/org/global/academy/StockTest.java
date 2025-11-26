package org.global.academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    
    @Test
    public void testLookupCurrentPrice() {
        // Create an Apple stock
        Stock apple = new Stock("Apple Inc.", "AAPL", "NASDAQ", 0.0);
        
        // Call lookupCurrentPrice
        apple.lookupCurrentPrice();
        
        // Check that price is not 0
        assertNotEquals(0.0, apple.getCurrentPrice(), 
                       "Current price should not be 0 after lookup");
        assertTrue(apple.getCurrentPrice() > 0, 
                  "Current price should be greater than 0");
    }
}