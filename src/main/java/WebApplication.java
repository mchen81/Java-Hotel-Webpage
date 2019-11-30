import common.ConnectionTest;

public class WebApplication {

    public static void main(String[] args) {
        System.out.println("Server start");
        System.out.println("Connect To Database.......");
        ConnectionTest.testConnection();

    }
}
