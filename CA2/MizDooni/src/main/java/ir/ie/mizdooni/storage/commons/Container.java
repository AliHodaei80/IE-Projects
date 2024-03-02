package ir.ie.mizdooni.storage.commons; 
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Container<T> {
    private static final Gson gson = new Gson();

    public void saveToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T loadFromFile(String filePath, Class<T> classOfT) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
