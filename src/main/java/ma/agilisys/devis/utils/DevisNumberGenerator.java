package ma.agilisys.devis.utils;

import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DevisNumberGenerator {
    private final AtomicLong counter = new AtomicLong(0);

    public String generateUniqueNumber() {
        long seq = counter.incrementAndGet();
        return String.format("DEV-%04d-%013d",
                Year.now().getValue(),
                seq).substring(0, 20); // DEV-2024-000000001
    }
}
