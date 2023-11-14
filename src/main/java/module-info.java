module com.example.envoisrecois {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;


    opens com.example.envoisrecois to javafx.fxml;
    exports com.example.envoisrecois;
}