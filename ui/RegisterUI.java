package ui;

import client.Client;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Request;
import network.Response;

public class RegisterUI {

    public void show(Stage stage) {

        Label title = new Label("REGISTER ACCOUNT");

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");

        PasswordField txtConfirm = new PasswordField();
        txtConfirm.setPromptText("Confirm Password");

        Label msg = new Label();

        Button btnRegister = new Button("Register");
        Button btnBack = new Button("Back to Login");

        btnRegister.setOnAction(e -> {

            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            String confirm = txtConfirm.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                msg.setText("Please fill all fields!");
                return;
            }

            if (!password.equals(confirm)) {
                msg.setText("Password not match!");
                return;
            }

            Response res = Client.send(
                    new Request("REGISTER", username + ":" + password)
            );

            msg.setText(res.getMessage());

            if (res.isSuccess()) {
                new LoginUI().show(stage);
            }
        });


        btnBack.setOnAction(e ->
                new LoginUI().show(stage)
        );

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                title,
                txtUsername,
                txtPassword,
                txtConfirm,
                btnRegister,
                btnBack,
                msg
        );

        Scene scene = new Scene(root, 400, 350);

        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }
}