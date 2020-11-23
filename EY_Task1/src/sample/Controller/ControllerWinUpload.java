package sample.Controller;

import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.main;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerWinUpload {
    private Connection conn = main.returnCon();
    @FXML
    private Text counter;

    public void getFile(ActionEvent actionEvent) throws IOException, SQLException {
        //пользователь выбирает файл и программа считывает строки
        FileDialog dialog = new FileDialog((Frame)null, "Выберите файл");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        File file = new File(dialog.getDirectory()+dialog.getFile());
        Path path = Paths.get(file.getAbsolutePath());
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = Files.readAllLines(path, charset);
        List<Data> dataList = new ArrayList<>();
        for (String line: lines) {
            String date = line.substring(0,line.indexOf("||"));
            int index = line.indexOf("||")+2;
            String eng = line.substring(index,line.indexOf("||", index));
            index = line.indexOf("||", index) +2;
            String rus = line.substring(index,line.indexOf("||", index));
            index = line.indexOf("||", index) +2;
            String intnum = line.substring(index,line.indexOf("||", index));
            index = line.indexOf("||", index) +2;
            String doublenum = line.substring(index,line.indexOf("||", index));
            int intnumFixed = Integer.parseInt(intnum);
            double doublenumFixed = Double.parseDouble(doublenum.replace(',','.'));
            Data data = new Data(date, rus ,eng, intnumFixed, doublenumFixed);
            dataList.add(data);
        }

        //создаем новое окно для отслеживания прогресса
        final javafx.scene.control.Label label = new javafx.scene.control.Label("Прогресс:");
        final ProgressBar progressBar = new ProgressBar(0);

        final javafx.scene.control.Button startButton = new javafx.scene.control.Button("Начать");

        final javafx.scene.control.Label statusLabel = new Label();
        statusLabel.setMinWidth(250);
        statusLabel.setTextFill(Color.BLUE);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //при нажатии кнопки "Начать" создаем экземпляр класса progressUpdate и связываем с ним элементы окна
                startButton.setDisable(true);
                progressBar.setProgress(0);
                ProgressUpdate progressUpdate = new ProgressUpdate(dataList);
                progressBar.progressProperty().unbind();
                progressBar.progressProperty().bind(progressUpdate.progressProperty());
                statusLabel.textProperty().unbind();
                statusLabel.textProperty().bind(progressUpdate.messageProperty());
                progressUpdate.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
                        new EventHandler<WorkerStateEvent>() {

                            @Override
                            public void handle(WorkerStateEvent t) {
                                List<Data> copied = progressUpdate.getValue();
                                statusLabel.textProperty().unbind();
                                statusLabel.setText("Скопировано: " + copied.size()+" строк");
                            }
                        });
                new Thread(progressUpdate).start();

            }
        });
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);

        root.getChildren().addAll(label, progressBar, //
                statusLabel, startButton);

        Scene scene = new Scene(root, 500, 120, Color.WHITE);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("ProgressBar");
        primaryStage.setScene(scene);
        primaryStage.show();
        dialog.dispose();
    }
}
