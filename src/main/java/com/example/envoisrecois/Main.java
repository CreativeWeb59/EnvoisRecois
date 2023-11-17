package com.example.envoisrecois;

import com.example.envoisrecois.controllersFx.LoginController;
import com.example.envoisrecois.controllersFx.MainController;
import com.example.envoisrecois.outils.Environment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // recuperation largeur fenetre
        double widthApp = Environment.getWidth();
        double heightApp = Environment.getHeight();
        widthApp = 800;
        heightApp = 600;

//        Image icon = new Image(Main.class.getResource("images/icon.png").openStream());
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Parent root = fxmlLoader.load();
//        MainController mainController = fxmlLoader.getController();
//        mainController.onLoad();
        LoginController loginController = fxmlLoader.getController();
        loginController.onLoad();

        Scene scene = new Scene(root, widthApp, heightApp);
        stage.setTitle("EnvoisRecois");
//        stage.getIcons().add(icon);
        stage.setScene(scene);

//        stage.setFullScreen(true);
//        stage.setFullScreenExitHint("");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}