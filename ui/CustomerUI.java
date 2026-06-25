package ui;

import client.Client;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Customer;
import network.Request;
import network.Response;

import java.util.List;

public class CustomerUI {

    public VBox getView() {

        VBox root = new VBox(15);

        root.setStyle("""
            -fx-padding:20;
            -fx-background-color:#f5f7fa;
            """);

        Label title = new Label("CUSTOMER MANAGEMENT");

        title.setStyle("""
            -fx-font-size:24;
            -fx-font-weight:bold;
            -fx-text-fill:#1E3A8A;
            """);

        TableView<Customer> table = new TableView<>();

        table.setPrefHeight(400);

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Customer, Integer> idCol =
                new TableColumn<>("ID");

        TableColumn<Customer, String> nameCol =
                new TableColumn<>("Name");

        TableColumn<Customer, String> phoneCol =
                new TableColumn<>("Phone");

        TableColumn<Customer, String> emailCol =
                new TableColumn<>("Email");

        table.getColumns().addAll(
                idCol,
                nameCol,
                phoneCol,
                emailCol
        );

        idCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getId()));

        nameCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getName()));

        phoneCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPhone()));

        emailCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEmail()));

        Runnable load = () -> {

            Response res =
                    Client.send(
                            new Request(
                                    "GET_CUSTOMERS",
                                    null
                            )
                    );

            if (res != null && res.isSuccess()) {

                List<Customer> list =
                        (List<Customer>) res.getData();

                table.setItems(
                        FXCollections.observableArrayList(list)
                );
            }
        };

        TextField txtId = new TextField();
        TextField txtName = new TextField();
        TextField txtPhone = new TextField();
        TextField txtEmail = new TextField();

        txtId.setPromptText("ID");
        txtName.setPromptText("Customer Name");
        txtPhone.setPromptText("Phone");
        txtEmail.setPromptText("Email");

        String fieldStyle = """
            -fx-background-radius:8;
            -fx-border-radius:8;
            -fx-padding:8;
            """;

        txtId.setStyle(fieldStyle);
        txtName.setStyle(fieldStyle);
        txtPhone.setStyle(fieldStyle);
        txtEmail.setStyle(fieldStyle);

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.setStyle("""
            -fx-background-color:white;
            -fx-padding:15;
            -fx-background-radius:10;
            -fx-border-radius:10;
            """);

        form.add(new Label("ID"), 0, 0);
        form.add(txtId, 1, 0);

        form.add(new Label("Name"), 0, 1);
        form.add(txtName, 1, 1);

        form.add(new Label("Phone"), 0, 2);
        form.add(txtPhone, 1, 2);

        form.add(new Label("Email"), 0, 3);
        form.add(txtEmail, 1, 3);

        Button add = new Button("Add");
        Button update = new Button("Update");
        Button delete = new Button("Delete");
        Button reload = new Button("Reload");

        String buttonStyle = """
            -fx-font-weight:bold;
            -fx-padding:8 15 8 15;
            -fx-background-radius:8;
            """;

        add.setStyle(
                buttonStyle +
                        "-fx-background-color:#22C55E;" +
                        "-fx-text-fill:white;"
        );

        update.setStyle(
                buttonStyle +
                        "-fx-background-color:#3B82F6;" +
                        "-fx-text-fill:white;"
        );

        delete.setStyle(
                buttonStyle +
                        "-fx-background-color:#EF4444;" +
                        "-fx-text-fill:white;"
        );

        reload.setStyle(
                buttonStyle +
                        "-fx-background-color:#F59E0B;" +
                        "-fx-text-fill:white;"
        );

        HBox btnBox =
                new HBox(
                        10,
                        add,
                        update,
                        delete,
                        reload
                );

        btnBox.setStyle(
                "-fx-padding:10 0 10 0;"
        );

        add.setOnAction(e -> {

            Customer c =
                    new Customer(
                            0,
                            txtName.getText(),
                            txtPhone.getText(),
                            txtEmail.getText()
                    );

            Client.send(
                    new Request(
                            "ADD_CUSTOMER",
                            c
                    )
            );

            load.run();
        });

        update.setOnAction(e -> {

            Customer c =
                    new Customer(
                            Integer.parseInt(txtId.getText()),
                            txtName.getText(),
                            txtPhone.getText(),
                            txtEmail.getText()
                    );

            Client.send(
                    new Request(
                            "UPDATE_CUSTOMER",
                            c
                    )
            );

            load.run();
        });

        delete.setOnAction(e -> {

            int id =
                    Integer.parseInt(txtId.getText());

            Client.send(
                    new Request(
                            "DELETE_CUSTOMER",
                            id
                    )
            );

            load.run();
        });

        reload.setOnAction(e -> load.run());

        table.setOnMouseClicked(e -> {

            Customer c =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (c != null) {

                txtId.setText(
                        String.valueOf(c.getId()));

                txtName.setText(
                        c.getName());

                txtPhone.setText(
                        c.getPhone());

                txtEmail.setText(
                        c.getEmail());
            }
        });

        load.run();

        root.getChildren().addAll(
                title,
                table,
                form,
                btnBox
        );

        return root;
    }


}
