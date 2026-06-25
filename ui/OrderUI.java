package ui;

import client.Client;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Order;
import network.Request;
import network.Response;

import java.util.Date;
import java.util.List;

public class OrderUI {

    public VBox getView() {

        VBox root = new VBox(15);

        root.setStyle("""
            -fx-padding:20;
            -fx-background-color:#f5f7fa;
            """);

        Label title = new Label("ORDER MANAGEMENT");

        title.setStyle("""
            -fx-font-size:24;
            -fx-font-weight:bold;
            -fx-text-fill:#1E3A8A;
            """);

        TableView<Order> table = new TableView<>();

        table.setPrefHeight(400);

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Order, Integer> idCol =
                new TableColumn<>("ID");

        TableColumn<Order, Integer> customerCol =
                new TableColumn<>("Customer ID");

        TableColumn<Order, Date> dateCol =
                new TableColumn<>("Order Date");

        TableColumn<Order, Double> totalCol =
                new TableColumn<>("Total Amount");

        table.getColumns().addAll(
                idCol,
                customerCol,
                dateCol,
                totalCol
        );

        idCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getId()));

        customerCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getCustomerId()));

        dateCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getOrderDate()));

        totalCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getTotalAmount()));

        Runnable load = () -> {

            Response res =
                    Client.send(
                            new Request(
                                    "GET_ORDERS",
                                    null
                            )
                    );

            if (res != null && res.isSuccess()) {

                List<Order> list =
                        (List<Order>) res.getData();

                table.setItems(
                        FXCollections.observableArrayList(list)
                );
            }
        };

        TextField txtId = new TextField();
        TextField txtCustomerId = new TextField();
        TextField txtTotal = new TextField();

        txtId.setPromptText("Order ID");
        txtCustomerId.setPromptText("Customer ID");
        txtTotal.setPromptText("Total Amount");

        String fieldStyle = """
            -fx-background-radius:8;
            -fx-border-radius:8;
            -fx-padding:8;
            """;

        txtId.setStyle(fieldStyle);
        txtCustomerId.setStyle(fieldStyle);
        txtTotal.setStyle(fieldStyle);

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.setStyle("""
            -fx-background-color:white;
            -fx-padding:15;
            -fx-background-radius:10;
            -fx-border-radius:10;
            """);

        form.add(new Label("Order ID"), 0, 0);
        form.add(txtId, 1, 0);

        form.add(new Label("Customer ID"), 0, 1);
        form.add(txtCustomerId, 1, 1);

        form.add(new Label("Total Amount"), 0, 2);
        form.add(txtTotal, 1, 2);

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

            Order o =
                    new Order(
                            0,
                            Integer.parseInt(txtCustomerId.getText()),
                            new Date(),
                            Double.parseDouble(txtTotal.getText())
                    );

            Client.send(
                    new Request(
                            "ADD_ORDER",
                            o
                    )
            );

            load.run();
        });

        update.setOnAction(e -> {

            Order o =
                    new Order(
                            Integer.parseInt(txtId.getText()),
                            Integer.parseInt(txtCustomerId.getText()),
                            new Date(),
                            Double.parseDouble(txtTotal.getText())
                    );

            Client.send(
                    new Request(
                            "UPDATE_ORDER",
                            o
                    )
            );

            load.run();
        });

        delete.setOnAction(e -> {

            Client.send(
                    new Request(
                            "DELETE_ORDER",
                            Integer.parseInt(txtId.getText())
                    )
            );

            load.run();
        });

        reload.setOnAction(e ->
                load.run()
        );

        table.setOnMouseClicked(e -> {

            Order o =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (o != null) {

                txtId.setText(
                        String.valueOf(o.getId()));

                txtCustomerId.setText(
                        String.valueOf(o.getCustomerId()));

                txtTotal.setText(
                        String.valueOf(o.getTotalAmount()));
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
