package ir.ie.mizdooni.commons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class Request {
    String operation;
    Map<String, Object> data;

    public Request(String operation, String data) {
        Gson g = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        this.data = g.fromJson(data, type);
        this.operation = operation;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getOperation() {
        return operation;
    }
}
