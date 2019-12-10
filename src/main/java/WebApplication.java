import common.ConnectionTest;
import controller.MyController;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class WebApplication {

    public static void main(String[] args) throws Exception {

        String port = args[1];
        System.out.println("Server start");
        System.out.println("Connect To Database.......");
        ConnectionTest.testConnection();
        new MyController(Integer.parseInt(port));


    }
}
