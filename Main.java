import javafx.application.Application;
import javafx.stage.Stage;
import server.ServerMain;
import ui.LoginUI;

public class Main extends Application {

    private Thread serverThread;

    @Override
    public void start(Stage stage) {

        System.out.println("=== START SYSTEM ===");

        serverThread = new Thread(() -> {
            try {
                System.out.println("[SERVER] Starting...");
                ServerMain.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[CLIENT] Opening Login...");


        new LoginUI().show(stage);
    }

    @Override
    public void stop() {

        System.out.println("=== STOP SYSTEM ===");

        if (serverThread != null) {
            serverThread.interrupt();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}