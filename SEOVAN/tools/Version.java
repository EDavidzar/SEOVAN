import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class Version {
    public static void main(String[] args) throws IOException {
        Path f = Paths.get(args[0]);
        Files.createDirectories(f.getParent());
        Properties p = new Properties();
        if (Files.exists(f)) {
            try (InputStream in = Files.newInputStream(f)) { p.load(in); }
        }
        long n = Long.parseLong(p.getProperty("build", "0")) + 1;

        long d = n % 100;
        long c = (n / 100) % 10;
        long b = (n / 1000) % 10;
        long a = 5 + n / 10000;

        p.setProperty("build", Long.toString(n));
        p.setProperty("version", a + "." + b + "." + c + "." + d);
        try (OutputStream out = Files.newOutputStream(f)) {
            p.store(out, "Autogenerado en cada compilacion");
        }
    }
}
