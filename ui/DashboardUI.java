package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class DashboardUI {

    public VBox getView() {

        VBox root = new VBox(20);

        root.setPadding(new Insets(20));

        root.setStyle(
                "-fx-background-color:#F4F6F9;"
        );

        Label title = new Label(
                "🏪 SHOE STORE MANAGEMENT"
        );

        title.setStyle(
                "-fx-font-size:28px;" +
                        "-fx-font-weight:bold;" +
                        "-fx-text-fill:#1E3A8A;"
        );

        HBox cards = new HBox(20);

        VBox productCard =
                createCard(
                        "👟",
                        "Products",
                        "0"
                );

        VBox customerCard =
                createCard(
                        "👤",
                        "Customers",
                        "0"
                );

        VBox orderCard =
                createCard(
                        "📦",
                        "Orders",
                        "0"
                );

        VBox revenueCard =
                createCard(
                        "💰",
                        "Revenue",
                        "0 VNĐ"
                );

        cards.getChildren().addAll(
                productCard,
                customerCard,
                orderCard,
                revenueCard
        );

        root.getChildren().addAll(
                title,
                cards
        );

        return root;
    }

    private VBox createCard(
            String icon,
            String title,
            String value
    ) {

        Label lblIcon =
                new Label(icon);

        lblIcon.setStyle(
                "-fx-font-size:32px;"
        );

        Label lblTitle =
                new Label(title);

        lblTitle.setStyle(
                "-fx-font-size:16px;" +
                        "-fx-font-weight:bold;"
        );

        Label lblValue =
                new Label(value);

        lblValue.setStyle(
                "-fx-font-size:20px;" +
                        "-fx-text-fill:#2563EB;" +
                        "-fx-font-weight:bold;"
        );

        VBox card = new VBox(
                10,
                lblIcon,
                lblTitle,
                lblValue
        );

        card.setAlignment(
                Pos.CENTER
        );

        card.setPrefSize(
                180,
                130
        );

        card.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:15;" +
                        "-fx-border-radius:15;" +
                        "-fx-border-color:#D1D5DB;" +
                        "-fx-padding:15;"
        );

        return card;
    }

}