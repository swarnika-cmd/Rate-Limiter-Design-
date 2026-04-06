public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Define limits: 5 requests per 10 seconds (10000 ms) for easier testing
        int limit = 5;
        long windowSizeMs = 10000;
        
        // Strategy Pattern in action: we can just change the enum here to switch the algorithm!
        RateLimitingService service = new RateLimitingService(limit, windowSizeMs, RateLimiterType.SLIDING_WINDOW);
        
        System.out.println("Processing API requests...");
        String userId = "User-T1";
        
        for (int i = 1; i <= 8; i++) {
            // "if no external call is needed, no rate limiting check is performed"
            boolean needsExternalCall = true; 
            
            if (needsExternalCall) {
                if (service.isAllowed(userId)) {
                    System.out.println("Request " + i + " -> External API called successfully.");
                } else {
                    System.out.println("Request " + i + " -> Rete limit exceeded. Request rejected.");
                }
            }
            
            // Sleep a little bit to simulate time passing between requests
            Thread.sleep(500); 
        }
    }
}
