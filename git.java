import java.io.File;
import org.apache.commons.codec.digest.DigestUtils;

public class git {
    public static void main(String[] args) {
        if (args[0].trim().equals("init")) {
            init();
        }
    }
    
    public static void init() {
        File git = new File("git");
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

    public static String hashFile(String input) {
        String hash = DigestUtils.sha1Hex(input);
        return hash;
    }


}