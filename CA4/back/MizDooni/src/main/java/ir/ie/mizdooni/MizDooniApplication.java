package ir.ie.mizdooni;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.controllers.MizDooniController;
import ir.ie.mizdooni.utils.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
@SpringBootApplication
public class   MizDooniApplication {

    public static void main(String[] args) {
        SpringApplication.run(MizDooniApplication.class, args);

//        MizDooniController controller = MizDooniController.getInstance();
//
//        Request request;
//        Scanner in = new Scanner(System.in);
//        String command = "";
//        List<String> commands = List.of("addUser {\"role\": \"client\",\"username\": \"ali\",\"password\": \"1234\",\"email\": \"user1@gmail.com\",\"address\": {\"country\": \"Iran\",\"city\": \"Tehran\"}}",
//                "addUser {\"role\": \"manager\",\"username\": \"ass\",\"password\": \"1234\",\"email\": \"user2@gmail.com\",\"address\": {\"country\": \"Iran\",\"city\": \"Tehran\"}}");
//        while (in.hasNext()) {
//            command = in.nextLine();
//            if (command == null || command.equals("exit"))
//                break;
//            try {
//                request = Parser.parseCommand(command);
//                System.out.println(controller.handleRequest(request));
//        } catch (Exception e) {
//                System.out.println("Error Happened! message: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
////        for (String c : commands) {
////            try {
////                request = Parser.parseCommand(c);
////                System.out.println(controller.handleRequest(request));
////            } catch (Exception e) {
////                System.out.println("Error Happened! message: " + e.getMessage());
////                e.printStackTrace();
////            }
////        }
    }

}
