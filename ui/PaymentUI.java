package ui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import model.Payment;
import service.PaymentService;

import java.util.Date;
import java.util.List;

public class PaymentUI {

    public VBox getView() {

        VBox root = new VBox(15);

        root.setPadding(new Insets(20));

        root.setStyle("""
                -fx-background-color:#f5f7fa;
                """);

        Label title =
                new Label("PAYMENT MANAGEMENT");

        title.setStyle("""
                -fx-font-size:18px;
                -fx-font-weight:bold;
                """);

        TableView<Payment> table =
                new TableView<>();

        TableColumn<Payment,Integer> idCol =
                new TableColumn<>("ID");

        TableColumn<Payment,Integer> orderCol =
                new TableColumn<>("Order ID");

        TableColumn<Payment,Double> amountCol =
                new TableColumn<>("Amount");

        TableColumn<Payment,Date> dateCol =
                new TableColumn<>("Payment Date");

        TableColumn<Payment,String> methodCol =
                new TableColumn<>("Method");

        table.getColumns().addAll(
                idCol,
                orderCol,
                amountCol,
                dateCol,
                methodCol
        );

        idCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getId()));

        orderCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getOrderId()));

        amountCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getAmount()));

        dateCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(
                        c.getValue().getPaymentDate()));

        methodCol.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getMethod()));

        PaymentService service =
                new PaymentService();

        Runnable loadData = () -> {

            List<Payment> list =
                    service.getPayments();

            table.setItems(
                    FXCollections.observableArrayList(list)
            );
        };

        TextField txtOrderId =
                new TextField();

        TextField txtAmount =
                new TextField();

        ComboBox<String> cbMethod =
                new ComboBox<>();

        txtOrderId.setPromptText("Order ID");

        txtAmount.setPromptText("Amount");

        cbMethod.getItems().addAll(
                "Cash",
                "Bank Transfer",
                "VNPay",
                "Momo"
        );

        cbMethod.setValue("Cash");

        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.add(
                new Label("Order ID"),
                0,
                0
        );

        form.add(
                txtOrderId,
                1,
                0
        );

        form.add(
                new Label("Amount"),
                0,
                1
        );

        form.add(
                txtAmount,
                1,
                1
        );

        form.add(
                new Label("Method"),
                0,
                2
        );

        form.add(
                cbMethod,
                1,
                2
        );

        Button btnPay =
                new Button("Thanh toán");

        Button btnRefresh =
                new Button("Làm mới");

        btnPay.setStyle(
                "-fx-background-color:#2ecc71;" +
                        "-fx-text-fill:white;"
        );

        btnRefresh.setStyle(
                "-fx-background-color:#3498db;" +
                        "-fx-text-fill:white;"
        );

        HBox buttons =
                new HBox(
                        10,
                        btnPay,
                        btnRefresh
                );

        btnPay.setOnAction(e -> {

            try {

                Payment payment =
                        new Payment(
                                0,
                                Integer.parseInt(
                                        txtOrderId.getText()
                                ),
                                Double.parseDouble(
                                        txtAmount.getText()
                                ),
                                new Date(),
                                cbMethod.getValue()
                        );

                service.addPayment(
                        payment
                );

                loadData.run();

                txtOrderId.clear();
                txtAmount.clear();

            } catch (Exception ex) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.ERROR
                        );

                alert.setContentText(
                        "Dữ liệu không hợp lệ!"
                );

                alert.showAndWait();
            }
        });

        btnRefresh.setOnAction(e ->
                loadData.run()
        );

        loadData.run();

        root.getChildren().addAll(
                title,
                table,
                form,
                buttons
        );

        return root;
    }
}