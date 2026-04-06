public class FixedWindowRateLimiter implements RateLimiter {
    private final int limit;
    private final long windowSizeMs;
    
    private long windowStart;
    private int counter;

    public FixedWindowRateLimiter(int limit, long windowSizeMs) {
        this.limit = limit;
        this.windowSizeMs = windowSizeMs;
        this.windowStart = System.currentTimeMillis();
        this.counter = 0;
    }

    @Override
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        
        // Check if we passed the current window
        if (now - windowStart >= windowSizeMs) {
            windowStart = now;
            counter = 0;
        }

        if (counter < limit) {
            counter++;
            return true;
        }
        
        return false;
    }
}
