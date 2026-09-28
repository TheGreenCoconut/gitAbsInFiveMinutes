import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.apache.commons.codec.digest.DigestUtils;

public class git {
    public static void main(String[] args) throws IOException {
        if (args[0].equals("init")) {
            init();
        }
        if (args[0].equals("add")) {
            add(args[1]);
        }
    }
    
    public static void init() {
        File git = new File("git");
        if (git.exists()) {
            return;
        }
        git.mkdir();

        File obj = new File(git, "objects");
        obj.mkdir();

        File index = new File(git, "INDEX");
        try {
            index.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File head = new File(git, "HEAD");
        try {
            head.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void add(String fileName) throws IOException {
        byte[] fileContents = Files.readAllBytes(Path.of(fileName));
        String hash = hashFile(fileContents);
        Path obj = Path.of("git", "objects", hash);
        Files.write(obj, fileContents, StandardOpenOption.CREATE);

        Path index = Path.of("git", "INDEX");
        String write = hash + " " + fileName + "\n";
        Files.writeString(index, write, StandardOpenOption.APPEND);

    }

    public static String hashFile(byte[] input) {
        String hash = DigestUtils.sha1Hex(input);
        return hash;
    }



    





}