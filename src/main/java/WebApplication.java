import common.ConnectionTest;
import controller.MyController;

public class WebApplication {

    public static void main(String[] args) throws Exception{
        System.out.println("Server start");
        System.out.println("Connect To Database.......");
        ConnectionTest.testConnection();
        new MyController();
    }
}
