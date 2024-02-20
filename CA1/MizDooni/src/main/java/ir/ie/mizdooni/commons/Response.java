package ir.ie.mizdooni.commons;

import com.google.gson.Gson;

public class Response {
    boolean success;
    Object data;

    @Override
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }
}
