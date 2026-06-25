package ui;

import client.ClientSession;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.UserAccount;

public class MainMenuUI {

    private BorderPane root = new BorderPane();

    public void show(Stage stage) {

        root.setStyle(
                "-fx-background-color:#F4F6F9;"
        );

        Label title =
                new Label(
                        "👟 SHOE STORE MANAGEMENT"
                );

        title.setStyle(
                "-fx-font-size:26px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:white;"
        );

        StackPane header =
                new StackPane(title);

        header.setPadding(
                new Insets(15)
        );

        header.setStyle(
                "-fx-background-color:#1E3A8A;"
        );

        root.setTop(header);

        VBox sidebar = new VBox(12);

        sidebar.setPadding(
                new Insets(15)
        );

        sidebar.setPrefWidth(220);

        sidebar.setStyle(
                "-fx-background-color:white;"
        );

        Button dashboardBtn =
                createMenuButton("📊 Dashboard");

        Button productBtn =
                createMenuButton("👟 Product");

        Button customerBtn =
                createMenuButton("👤 Customer");

        Button orderBtn =
                createMenuButton("📦 Order");

        Button paymentBtn =
                createMenuButton("💰 Payment");

        Button logoutBtn =
                createMenuButton("🚪 Logout");

        sidebar.getChildren().addAll(
                dashboardBtn,
                productBtn,
                customerBtn,
                orderBtn,
                paymentBtn,
                logoutBtn
        );

        root.setLeft(sidebar);

        root.setCenter(
                new DashboardUI().getView()
        );

        dashboardBtn.setOnAction(e ->
                root.setCenter(
                        new DashboardUI().getView()
                )
        );

        productBtn.setOnAction(e ->
                root.setCenter(
                        new ProductUI().getView()
                )
        );

        customerBtn.setOnAction(e ->
                root.setCenter(
                        new CustomerUI().getView()
                )
        );

        orderBtn.setOnAction(e ->
                root.setCenter(
                        new OrderUI().getView()
                )
        );

        paymentBtn.setOnAction(e ->
                root.setCenter(
                        new PaymentUI().getView()
                )
        );

        logoutBtn.setOnAction(e -> {

            ClientSession.clear();

            new LoginUI().show(stage);

        });

        UserAccount user =
                ClientSession.getCurrentUser();

        if (user == null ||
                !"ADMIN".equalsIgnoreCase(
                        user.getRole()
                )) {

            productBtn.setDisable(true);
            orderBtn.setDisable(true);
            paymentBtn.setDisable(true);
        }

        Label footer =
                new Label(
                        "Logged in as: "
                                + user.getUsername()
                );

        footer.setStyle(
                "-fx-padding:10;" +
                        "-fx-font-weight:bold;"
        );

        root.setBottom(footer);

        Scene scene =
                new Scene(
                        root,
                        1200,
                        700
                );

        stage.setTitle(
                "Shoe Store Management"
        );

        stage.setScene(scene);

        stage.show();
    }

    private Button createMenuButton(
            String text
    ) {

        Button btn =
                new Button(text);

        btn.setMaxWidth(
                Double.MAX_VALUE
        );

        btn.setPrefHeight(45);

        btn.setStyle(
                "-fx-background-color:#3B82F6;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-size:14px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;"
        );

        return btn;
    }


}
