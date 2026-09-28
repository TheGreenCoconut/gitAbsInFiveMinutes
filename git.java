import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class git {
    public static void main(String[] args) throws IOException {
        if (args[0].equals("init")) {
            init();
        } else if (args[0].equals("add")) {
            if (args.length < 2) {
                System.out.println("");
            } else {
                add(args[1]);
            }
            
        }
    }
    
    public static void init() {
        File git = new File("git");
        if (git.exists()) {
            System.out.println("");
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
        List<String> indexLines = Files.readAllLines(Path.of("git", "INDEX"));

        String hash = hashFile(fileContents);
        Path obj = Path.of("git", "objects", hash);
        Path index = Path.of("git", "INDEX");
        
        for (int i = 0; i < indexLines.size(); i++) {
            if (indexLines.get(i).contains(fileName)) {
                indexLines.remove(i);
                i--;
            }
        }

        indexLines.add(hash + " " + fileName);
        
        Files.write(index, indexLines);
        
        Files.write(obj, fileContents, StandardOpenOption.CREATE);
        

    }

    public static String hashFile(byte[] input) {
        String hash = DigestUtils.sha1Hex(input);
        return hash;
    }



    





}