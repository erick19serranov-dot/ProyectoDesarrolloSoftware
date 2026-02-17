public class Main {
public static void main(String[] args) {
    javafx.application.Application.launch(Launcher.class, args);
}

public static class Launcher extends javafx.application.Application {
    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/BillboardView.fxml"));
        javafx.scene.Parent root = loader.load();
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        primaryStage.setTitle("Cartelera");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

}
