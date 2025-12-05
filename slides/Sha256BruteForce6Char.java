import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicLong;

public class Sha256BruteForce6Char {

    // ✅ Alphanumeric character set (62 chars)
    private static final char[] CHARSET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final int PASSWORD_LENGTH = 6;

    // ✅ Known password (for teaching demo)
    private static final String REAL_PASSWORD = "aBc123";

    // ✅ SHA-256 hash of the real password (computed safely at load time)
    private static final String TARGET_HASH = sha256(REAL_PASSWORD);

    private static final AtomicLong attempts = new AtomicLong(0);

    public static void main(String[] args) {
        System.out.println("🔐 Real password: " + REAL_PASSWORD);
        System.out.println("🔑 SHA-256 hash: " + TARGET_HASH);
        System.out.println("🚀 Starting brute-force...\n");

        long startTime = System.currentTimeMillis();

        char[] guess = new char[PASSWORD_LENGTH];

        if (bruteForce(guess, 0)) {
            long endTime = System.currentTimeMillis();
            System.out.println("\n✅ Password found!");
            System.out.println("🔢 Attempts: " + attempts.get());
            System.out.println("⏱ Time: " + (endTime - startTime) + " ms");
        } else {
            System.out.println("❌ Password not found.");
        }
    }

    // 🔁 Recursive brute-force generator
    private static boolean bruteForce(char[] guess, int position) {
        if (position == PASSWORD_LENGTH) {
            attempts.incrementAndGet();
            String candidate = new String(guess);

            if (sha256(candidate).equals(TARGET_HASH)) {
                System.out.println("\n🔓 CRACKED PASSWORD: " + candidate);
                return true;
            }

            // Progress output every 5 million attempts
            if (attempts.get() % 5_000_000 == 0) {
                System.out.println("Tried: " + attempts.get() + " → " + candidate);
            }

            return false;
        }

        for (char c : CHARSET) {
            guess[position] = c;
            if (bruteForce(guess, position + 1)) {
                return true;
            }
        }
        return false;
    }

    // 🔐 SHA-256 hashing function (NO throws Exception anymore)
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
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }
}
