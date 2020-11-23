package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class main extends Application {
    private static Connection conn;
    public static Connection returnCon() {
        return conn;
    }

    //открытие главного меню
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/WinMain.fxml"));
        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //соединение с сервером
    public static Connection connect() throws SQLException {
        final String url = "jdbc:postgresql://localhost/postgres";
        final String user = "postgres";
        final String password = "password";
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args) throws SQLException {
        connect();
        launch(args);
    }
}
