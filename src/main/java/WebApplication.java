import common.ConnectionTest;
import controller.MyController;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class WebApplication {

    public static void main(String[] args) {

        if (args == null || args.length != 2 || !args[0].equals("-port")) {
            throw new IllegalArgumentException("Wrong Argument, It Must be -port [port]");
        }
        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong Argument, Port must be a number");
        }
        System.out.println("Server start");
        System.out.println("Connect To Database.......");
        ConnectionTest.testConnection();
        new MyController(port);

    }
}
