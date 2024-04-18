package ir.ie.mizdooni.storage.commons; 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.ie.mizdooni.utils.DateTimeSerializer;
import ir.ie.mizdooni.utils.TimeSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Container<T> {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
            .registerTypeAdapter(LocalTime.class, new TimeSerializer())
            .create();

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
