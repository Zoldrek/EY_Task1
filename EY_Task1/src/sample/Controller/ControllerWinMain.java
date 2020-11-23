package sample.Controller;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerWinMain {

    public static String getRandomDate(){
        LocalDate localdate = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 5))));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localdate.format(formatter);
    }

    public static String getRandomString(String lang){
        String SALTCHARS;
        if (lang == "eng") {
            SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        }
        else{
            SALTCHARS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯабвгдеёжзийклмнопрстуфхцчшщьыъэюя";
        }
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String getRandomInt(){
        Random random = new Random();
        return Integer.toString(random.nextInt(100000000 - 1) + 1);
    }

    public static String getRandomDouble(){
        Random rand = new Random();
        int a=1;
        int b=20;
        double dx  = a +  rand.nextDouble()*(b - a);
        String number;
        number = String.format("%.8f", dx);
        number = number.replace(".", ",");
        return number;
    }

    //генерация случайной строки
    public static String getLine(){
        String genLine = (getRandomDate()+"||"+getRandomString("eng")+"||"+getRandomString("rus")+"||"+getRandomInt()+"||"+getRandomDouble()+"||");
        return genLine;
    }


    //генерация файлов
    @FXML
    private void Generation() {
        for(int i=0;i<100;i++) {
            try {
                List<String> lines = new ArrayList<>();
                for (int j = 0; j < 100000; j++) {
                    lines.add(getLine());
                }
                String path = new String("files\\"+ (i + 1) +".txt");
                Path file = Paths.get(path);
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void MergeWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/WinMerge.fxml"));
        primaryStage.setTitle("Объединение");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    private void UploadWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/WinUpload.fxml"));
        primaryStage.setTitle("Загрузка");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    private void CalculationWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/WinCalculation.fxml"));
        primaryStage.setTitle("Подсчет");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
