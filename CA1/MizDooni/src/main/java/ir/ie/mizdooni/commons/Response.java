package ir.ie.mizdooni.commons;

import com.google.gson.Gson;

public class Response {
    boolean success;
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
        Gson g = new Gson();
        return g.toJson(this);
    }
}
