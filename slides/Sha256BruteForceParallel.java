import java.security.MessageDigest;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Sha256BruteForceParallel {

    private static final char[] CHARSET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int PASSWORD_LENGTH = 6;
    private static final String REAL_PASSWORD = "aBc123";
    private static final String TARGET_HASH = sha256(REAL_PASSWORD);

    private static final AtomicBoolean found = new AtomicBoolean(false);
    private static final AtomicLong attempts = new AtomicLong(0);

    public static void main(String[] args) throws Exception {

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        System.out.println("CPU cores detected: " + cores);
        System.out.println("Target SHA-256: " + TARGET_HASH);
        System.out.println("Starting parallel brute force...\n");

        long startTime = System.currentTimeMillis();

        // ✅ Divide work by first character
        for (char firstChar : CHARSET) {
            pool.execute(() -> {
                char[] guess = new char[PASSWORD_LENGTH];
                guess[0] = firstChar;
                bruteForce(guess, 1);
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);

        long endTime = System.currentTimeMillis();

        System.out.println("\n✅ Total attempts: " + attempts.get());
        System.out.println("⏱ Time: " + (endTime - startTime) + " ms");
    }

    // 🔁 Recursive brute-force (thread-safe)
    private static void bruteForce(char[] guess, int position) {
        if (found.get()) return;

        if (position == PASSWORD_LENGTH) {
            long count = attempts.incrementAndGet();
            String candidate = new String(guess);

            if (sha256(candidate).equals(TARGET_HASH)) {
                found.set(true);
                System.out.println("\n🔓 CRACKED PASSWORD: " + candidate);
                return;
            }

            if (count % 10_000_000 == 0) {
                System.out.println("Tried: " + count + " → " + candidate);
            }
            return;
        }

        for (char c : CHARSET) {
            if (found.get()) return;
            guess[position] = c;
            bruteForce(guess, position + 1);
        }
    }

    // 🔐 SHA-256 (safe for static use)
    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
