package Client.GUI;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class UserUIBuySellRemove extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        // Construct all assets used by the various tabs

        //Construct assets used to create the Remove Order tab
        VBox RemoveOrderBox = new VBox();

        TextField RemoveOrderText = new TextField();
        Label RemoveOrderLabel = new Label("Order No.");
        RemoveOrderBox.setPadding(new Insets(10, 30, 30 ,30));
        RemoveOrderBox.setAlignment((Pos.CENTER));
        RemoveOrderBox.getChildren().addAll(RemoveOrderLabel, RemoveOrderText);

        //Construct assets used to create the Buy Order tab.
        VBox BuyBox = new VBox();

        TextField assetTypeText = new TextField();
        TextField assetQuantityText = new TextField();

        Label assetTypeLabel = new Label("Asset Type");
        Label assetQuantityLabel = new Label("Asset Quantity");

        Button buyButton = new Button("Buy");

        BuyBox.setPadding(new Insets(10, 30, 30 ,30));
        BuyBox.setAlignment((Pos.CENTER));
        BuyBox.getChildren().addAll(assetTypeLabel,assetTypeText,assetQuantityLabel,assetQuantityText, buyButton);

        //Construct assets used to create the Sell Order tab.
        VBox SellBox = new VBox();

        TextField sAssetTypeText = new TextField();
        TextField sAssetQuantityText = new TextField();


        Label sAssetTypeLabel = new Label("Asset Type");
        Label sAssetQuantityLabel = new Label("Asset Quantity");

        Button sellButton = new Button("Sell");

        SellBox.setPadding(new Insets(10, 30, 30 ,30));
        SellBox.setAlignment((Pos.CENTER));
        SellBox.getChildren().addAll(sAssetTypeLabel, sAssetTypeText, sAssetQuantityLabel, sAssetQuantityText, sellButton);


        // Create new tabs for the Buy, Sell and Remove Order options a user has
        Tab BuyOrder = new Tab("Buy", BuyBox);
        Tab SellOrder = new Tab("Sell"  , SellBox);
        Tab RemoveOrder = new Tab("Remove Order" , RemoveOrderBox);


        tabPane.getTabs().add(BuyOrder);
        tabPane.getTabs().add(SellOrder);
        tabPane.getTabs().add(RemoveOrder);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Unknown Organisation's Trading Platform");

        // Make GUI visible
        primaryStage.show();
    }
}