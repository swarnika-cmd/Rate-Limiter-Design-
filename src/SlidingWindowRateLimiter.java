import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int limit;
    private final long windowSizeMs;
    private final Queue<Long> requestTimestamps;

    public SlidingWindowRateLimiter(int limit, long windowSizeMs) {
        this.limit = limit;
        this.windowSizeMs = windowSizeMs;
        this.requestTimestamps = new LinkedList<>();
    }

    @Override
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        
        // Remove timestamps that are outside the current sliding window
        while (!requestTimestamps.isEmpty() && now - requestTimestamps.peek() >= windowSizeMs) {
            requestTimestamps.poll();
        }

        if (requestTimestamps.size() < limit) {
            requestTimestamps.add(now);
            return true;
        }
        
        return false;
    }
}
