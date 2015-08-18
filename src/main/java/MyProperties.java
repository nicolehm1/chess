import java.io.*;
import java.util.Properties;

/**
 * Created by Nico on 14.08.2015.
 */
public class MyProperties extends Properties{
    private static MyProperties ourInstance = new MyProperties();
    File file = new File("C:\\Users\\Nico\\IdeaProjects\\Chess\\properties");

    public static MyProperties getInstance() {
        return ourInstance;
    }

    private MyProperties() {
        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() throws IOException {
        InputStream reader = new FileInputStream(file);
        this.loadFromXML(reader);
    }

    public void saveProperties() throws IOException {
        OutputStream writer = new FileOutputStream(file);
        this.storeToXML(writer, "Stored Properties");
    }
}
