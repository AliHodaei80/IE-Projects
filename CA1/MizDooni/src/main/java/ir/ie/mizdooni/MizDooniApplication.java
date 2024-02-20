package ir.ie.mizdooni;

import ir.ie.mizdooni.utils.Parser;
import ir.ie.mizdooni.commons.*;

public class MizDooniApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		Request r = Parser.parse("CreateUser {\"12 12\" : \"123  13131313131\"}");
		System.out.println(r.getOperation());
		System.out.println(r.getData().get("12 12"));

	}

}
