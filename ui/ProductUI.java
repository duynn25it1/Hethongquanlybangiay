package ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Product;
import service.ProductService;

import java.util.List;

public class ProductUI {

    public VBox getView() {

        VBox layout = new VBox(15);

        layout.setPadding(
                new Insets(20)
        );

        layout.setStyle(
                "-fx-background-color:#f5f7fa;"
        );

        Label title =
                new Label(
                        "PRODUCT MANAGEMENT"
                );

        title.setStyle(
                "-fx-font-size:24;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#1E3A8A;"
        );

        ProductService service =
                new ProductService();

        TableView<Product> table =
                new TableView<>();

        table.setPrefHeight(400);

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Product, Integer> idCol =
                new TableColumn<>("ID");

        TableColumn<Product, String> nameCol =
                new TableColumn<>("Name");

        TableColumn<Product, String> sizeCol =
                new TableColumn<>("Size");

        TableColumn<Product, Double> priceCol =
                new TableColumn<>("Price");

        TableColumn<Product, Integer> quantityCol =
                new TableColumn<>("Quantity");

        table.getColumns().addAll(
                idCol,
                nameCol,
                sizeCol,
                priceCol,
                quantityCol
        );

        idCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getId()));

        nameCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getName()));

        sizeCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getSize()));

        priceCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getPrice()));

        quantityCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getQuantity()));

        Runnable loadData = () -> {

            List<Product> list =
                    service.getProducts();

            table.setItems(
                    FXCollections.observableArrayList(list)
            );
        };

        loadData.run();

        TextField txtSearch =
                new TextField();

        txtSearch.setPromptText(
                "Search product..."
        );

        txtSearch.setStyle(
                "-fx-background-radius:8;"
        );

        Button btnSearch =
                new Button("Search");

        Button btnRefresh =
                new Button("Refresh");

        btnSearch.setStyle(
                "-fx-background-color:#3B82F6;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-weight:bold;"
        );

        btnRefresh.setStyle(
                "-fx-background-color:#F59E0B;" +
                        "-fx-text-fill:white;" +
                        "-fx-font-weight:bold;"
        );

        HBox searchBox =
                new HBox(
                        10,
                        txtSearch,
                        btnSearch,
                        btnRefresh
                );

        btnSearch.setOnAction(e -> {

            String keyword =
                    txtSearch.getText().trim();

            if (keyword.isEmpty()) {

                loadData.run();

                return;
            }

            List<Product> result =
                    service.searchProducts(
                            keyword
                    );

            table.setItems(
                    FXCollections.observableArrayList(result)
            );
        });

        btnRefresh.setOnAction(e ->
                loadData.run()
        );

        TextField txtId =
                new TextField();

        TextField txtName =
                new TextField();

        TextField txtSize =
                new TextField();

        TextField txtPrice =
                new TextField();

        TextField txtQuantity =
                new TextField();

        String fieldStyle =
                "-fx-background-radius:8;" +
                        "-fx-padding:8;";

        txtId.setStyle(fieldStyle);
        txtName.setStyle(fieldStyle);
        txtSize.setStyle(fieldStyle);
        txtPrice.setStyle(fieldStyle);
        txtQuantity.setStyle(fieldStyle);

        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.setStyle(
                "-fx-background-color:white;" +
                        "-fx-padding:15;" +
                        "-fx-background-radius:10;"
        );

        form.add(new Label("ID"), 0, 0);
        form.add(txtId, 1, 0);

        form.add(new Label("Name"), 0, 1);
        form.add(txtName, 1, 1);

        form.add(new Label("Size"), 0, 2);
        form.add(txtSize, 1, 2);

        form.add(new Label("Price"), 0, 3);
        form.add(txtPrice, 1, 3);

        form.add(new Label("Quantity"), 0, 4);
        form.add(txtQuantity, 1, 4);

        Button btnAdd =
                new Button("Add");

        Button btnUpdate =
                new Button("Update");

        Button btnDelete =
                new Button("Delete");

        String buttonStyle =
                "-fx-font-weight:bold;" +
                        "-fx-background-radius:8;" +
                        "-fx-padding:8 15 8 15;";

        btnAdd.setStyle(
                buttonStyle +
                        "-fx-background-color:#22C55E;" +
                        "-fx-text-fill:white;"
        );

        btnUpdate.setStyle(
                buttonStyle +
                        "-fx-background-color:#3B82F6;" +
                        "-fx-text-fill:white;"
        );

        btnDelete.setStyle(
                buttonStyle +
                        "-fx-background-color:#EF4444;" +
                        "-fx-text-fill:white;"
        );

        HBox buttons =
                new HBox(
                        10,
                        btnAdd,
                        btnUpdate,
                        btnDelete
                );

        table.setOnMouseClicked(e -> {

            Product p =
                    table.getSelectionModel()
                            .getSelectedItem();

            if (p != null) {

                txtId.setText(
                        String.valueOf(
                                p.getId()
                        ));

                txtName.setText(
                        p.getName()
                );

                txtSize.setText(
                        p.getSize()
                );

                txtPrice.setText(
                        String.valueOf(
                                p.getPrice()
                        ));

                txtQuantity.setText(
                        String.valueOf(
                                p.getQuantity()
                        ));
            }
        });

        btnAdd.setOnAction(e -> {

            Product p =
                    new Product(
                            Integer.parseInt(txtId.getText()),
                            txtName.getText(),
                            "Unknown",
                            txtSize.getText(),
                            Double.parseDouble(txtPrice.getText()),
                            Integer.parseInt(txtQuantity.getText())
                    );

            service.addProduct(p);

            loadData.run();
        });

        btnUpdate.setOnAction(e -> {

            Product p =
                    new Product(
                            Integer.parseInt(txtId.getText()),
                            txtName.getText(),
                            "Unknown",
                            txtSize.getText(),
                            Double.parseDouble(txtPrice.getText()),
                            Integer.parseInt(txtQuantity.getText())
                    );

            service.updateProduct(p);

            loadData.run();
        });

        btnDelete.setOnAction(e -> {

            service.deleteProduct(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            loadData.run();
        });

        layout.getChildren().addAll(
                title,
                searchBox,
                table,
                form,
                buttons
        );

        return layout;
    }

}
