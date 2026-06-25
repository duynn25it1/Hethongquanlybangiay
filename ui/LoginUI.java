package ui;

import client.Client;
import client.ClientSession;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.UserAccount;
import network.Request;
import network.Response;

public class LoginUI {

    public void show(Stage stage) {

        Label icon = new Label("👟");

        icon.setStyle(
                "-fx-font-size:60px;"
        );

        Label title =
                new Label(
                        "SHOE STORE SYSTEM"
                );

        title.setStyle(
                "-fx-font-size:26px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#1E3A8A;"
        );

        TextField txtUser =
                new TextField();

        txtUser.setPromptText(
                "Username"
        );

        PasswordField txtPass =
                new PasswordField();

        txtPass.setPromptText(
                "Password"
        );

        String fieldStyle =
                "-fx-pref-width:250;" +
                        "-fx-pref-height:40;" +
                        "-fx-background-radius:8;" +
                        "-fx-border-radius:8;";

        txtUser.setStyle(fieldStyle);
        txtPass.setStyle(fieldStyle);

        Label msg =
                new Label();

        msg.setStyle(
                "-fx-text-fill:red;"
        );

        Button btnLogin =
                new Button(
                        "Login"
                );

        Button btnRegister =
                new Button(
                        "Register"
                );

        btnLogin.setPrefWidth(250);
        btnRegister.setPrefWidth(250);

        btnLogin.setStyle(
                "-fx-background-color:#2563EB;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-pref-height:40;"
        );

        btnRegister.setStyle(
                "-fx-background-color:#22C55E;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-pref-height:40;"
        );

        btnLogin.setOnAction(e -> {

            String username =
                    txtUser.getText().trim();

            String password =
                    txtPass.getText().trim();

            if (username.isEmpty()
                    || password.isEmpty()) {

                msg.setText(
                        "Vui lòng nhập đầy đủ thông tin!"
                );

                return;
            }

            Response res =
                    Client.send(
                            new Request(
                                    "LOGIN",
                                    username + ":" + password
                            )
                    );

            if (res.isSuccess()) {

                UserAccount user =
                        (UserAccount) res.getData();

                ClientSession.setCurrentUser(user);

                new MainMenuUI().show(stage);

            } else {

                msg.setText(
                        res.getMessage()
                );
            }
        });

        btnRegister.setOnAction(e ->
                new RegisterUI().show(stage)
        );

        VBox card =
                new VBox(15);

        card.setAlignment(
                Pos.CENTER
        );

        card.setStyle(
                "-fx-background-color:white;" +
                        "-fx-padding:30;" +
                        "-fx-background-radius:15;" +
                        "-fx-border-radius:15;"
        );

        card.getChildren().addAll(
                icon,
                title,
                txtUser,
                txtPass,
                btnLogin,
                btnRegister,
                msg
        );

        VBox root =
                new VBox(card);

        root.setAlignment(
                Pos.CENTER
        );

        root.setStyle(
                "-fx-background-color:#F4F6F9;"
        );

        Scene scene =
                new Scene(
                        root,
                        500,
                        450
                );

        stage.setTitle(
                "Shoe Store Login"
        );

        stage.setScene(scene);

        stage.show();
    }

}