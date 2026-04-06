public class Main {
    public static void main(String[] args) throws InterruptedException {
        int limit = 5;
        long windowSizeMs = 10000;
        
        RateLimitingService service = new RateLimitingService(limit, windowSizeMs, RateLimiterType.SLIDING_WINDOW);
        
        System.out.println("Processing API requests...");
        String userId = "User-T1";
        
        for (int i = 1; i <= 8; i++) {
            boolean needsExternalCall = true; 
            
            if (needsExternalCall) {
                if (service.isAllowed(userId)) {
                    System.out.println("Request " + i + " -> External API called successfully.");
                } else {
                    System.out.println("Request " + i + " -> Rete limit exceeded. Request rejected.");
                }
            }
            
            Thread.sleep(500); 
        }
    }
}
