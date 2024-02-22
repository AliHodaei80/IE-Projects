package ir.ie.mizdooni;

import ir.ie.mizdooni.commons.Request;
import ir.ie.mizdooni.controllers.MizDooniController;
import ir.ie.mizdooni.utils.Parser;

import java.util.Scanner;

public class MizDooniApplication {

    public static void main(String[] args) {
        // addUser {"role": "client", "username": "user1", "password": "1234", "email":"user1@gmail.com", "address": {"country": "Iran", "city": "Tehran"}}
        // addRestaurant {"name": "restaurant1", "managerUsername": "user1", "type": "Iranian","startTime": "08:00", "endTime": "23:00", "description": "Open seven days a week","address": {"country": "Iran", "city": "Tehran", "street": "North Kargar"}}
        // addUser {"role": "manager", "username": "user2", "password": "1234", "email":"user2@gmail.com", "address": {"country": "Iran", "city": "Tehran"}}
        MizDooniController controller = MizDooniController.getInstance();
        Request request;
        Scanner in = new Scanner(System.in);
        String command = "";
        while (true) {
            command = in.nextLine();
            if (command == null || command.equals("exit"))
                break;
            request = Parser.parseCommand(command);
            System.out.println(controller.handleRequest(request));
        }
    }

}
