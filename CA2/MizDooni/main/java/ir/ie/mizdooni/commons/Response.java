package ir.ie.mizdooni.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import ir.ie.mizdooni.utils.DateTimeSerializer;
import ir.ie.mizdooni.utils.TimeSerializer;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class Response {
    @Expose(serialize = true)
    boolean success;
    @Expose(serialize = true)
    Object data;

    public Response(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer())
                .registerTypeAdapter(LocalTime.class, new TimeSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return g.toJson(this);
    }
}
