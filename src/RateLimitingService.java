import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class RateLimitingService {
    private final Map<String, RateLimiter> limiters = new ConcurrentHashMap<>();
    
    private final int limit;
    private final long windowSizeMs;
    private final RateLimiterType type;

    public RateLimitingService(int limit, long windowSizeMs, RateLimiterType type) {
        this.limit = limit;
        this.windowSizeMs = windowSizeMs;
        this.type = type;
    }

    public boolean isAllowed(String key) {
        // Create a new rate limiter for the key if it doesn't exist
        limiters.putIfAbsent(key, createLimiter());
        return limiters.get(key).allowRequest();
    }

    private RateLimiter createLimiter() {
        if (type == RateLimiterType.FIXED_WINDOW) {
            return new FixedWindowRateLimiter(limit, windowSizeMs);
        } else if (type == RateLimiterType.SLIDING_WINDOW) {
            return new SlidingWindowRateLimiter(limit, windowSizeMs);
        }
        throw new IllegalArgumentException("Unknown rate limiter type");
    }
}
