import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

/*
 * USER NOTE: For all commands that you want to run, at the time of writing, the only way to do so
 * is to use terminal. You must write the following to get this code to compile correctly:
 * "java -cp ".:commons-codec-1.22.1/commons-codec-1.22.1.jar" git.java [YOUR COMMAND HERE]"
 */

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
        File gitFolder = new File("git");
        if (gitFolder.exists()) {
            System.out.println("");
            return;
        }
        gitFolder.mkdir();

        File objectsFolder = new File(gitFolder, "objects");
        objectsFolder.mkdir();

        File indexFile = new File(gitFolder, "INDEX");
        try {
            indexFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File headFile = new File(gitFolder, "HEAD");
        try {
            headFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void add(String fileName) throws IOException {
        byte[] fileContents = Files.readAllBytes(Path.of(fileName));
        List<String> indexLines = Files.readAllLines(Path.of("git", "INDEX"));

        String filePath = "gitAbsInFiveMinutes/" + fileName;

        String hashValue = hashFile(fileContents);
        Path objectsPath = Path.of("git", "objects", hashValue);
        Path indexPath = Path.of("git", "INDEX");

        for (int lineNumber = 0; lineNumber < indexLines.size(); lineNumber++) {
            if (indexLines.get(lineNumber).contains(fileName)) {
                if (indexLines.get(lineNumber).contains(hashValue)) {
                    return;
                }
                indexLines.remove(lineNumber);
                lineNumber--;
            }
        }

        indexLines.add(hashValue + " " + filePath);

        for (int i = 0; i < indexLines.size(); i++) {
            if (i == indexLines.size() - 1) {
                Files.writeString(indexPath, indexLines.get(i));
            }
            Files.writeString(indexPath, indexLines.get(i) + "\n");

        }
        Files.writeString(indexPath, String.join("\n", indexLines));

        Files.write(objectsPath, fileContents, StandardOpenOption.CREATE);

    }

    public static String hashFile(byte[] input) {
        String hashValue = DigestUtils.sha1Hex(input);
        return hashValue;
    }

}
