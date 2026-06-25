package ui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Payment;
import javafx.scene.control.Label;



public class PaymentUI {

    public VBox getView() {

        VBox layout = new VBox(10);

        Label title =
                new Label("QUẢN LÝ THANH TOÁN");

        TableView<Payment> table =
                new TableView<>();

        table.getColumns().addAll(
                new TableColumn<>("ID"),
                new TableColumn<>("Order ID"),
                new TableColumn<>("Amount"),
                new TableColumn<>("Date"),
                new TableColumn<>("Method")
        );

        GridPane form = new GridPane();

        form.add(new Label("Order ID"),0,0);
        form.add(new TextField(),1,0);

        form.add(new Label("Amount"),0,1);
        form.add(new TextField(),1,1);

        form.add(new Label("Date"),0,2);
        form.add(new DatePicker(),1,2);

        ComboBox<String> method =
                new ComboBox<>();

        method.getItems().addAll(
                "Tiền mặt",
                "Chuyển khoản"
        );

        form.add(new Label("Method"),0,3);
        form.add(method,1,3);

        HBox buttons = new HBox(
                10,
                new Button("Thanh toán"),
                new Button("Làm mới")
        );

        layout.getChildren().addAll(
                title,
                table,
                form,
                buttons
        );

        return layout;
    }
}