package ir.ie.mizdooni;

import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.validators.RequestSchemaValidator;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import ir.ie.mizdooni.commons.*;

public class MizDooniApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		Gson g = new Gson();
		Map<String, Object> request_m = new HashMap<String, Object>();
		Map<String, Object> address_m = new HashMap<String, Object>();
		request_m.put("role", "client");
		request_m.put("username", "c1");
		request_m.put("password", "pswd");
		request_m.put("email", "pooriatajmehrabi@gmail.com");
		address_m.put("country", "Iran");
		address_m.put("city", "Tehran");
		request_m.put("address", address_m);
		System.out.println(request_m);
		String request_payload = g.toJson(request_m);
		System.out.println(request_payload);
		Request r = Parser.parse("addUser".concat(" ").concat(request_payload));
		try {
			RequestSchemaValidator.validate(r);
			System.out.println(r.getOperation() + " Was Successful");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
