package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ControllerWinMerge {
    @FXML
    private TextField delLine;
    @FXML
    private Text report;
/*
    public ArrayList<String> deleteLines (List<String> inputList, String sign){
        ArrayList<String> fixedList = new ArrayList<String>();
        for (String testString:inputList){
            if (!(testString.contains(sign))) fixedList.add(testString);
        }
        return fixedList;
    }
*/
    public void MergeFiles(ActionEvent actionEvent) throws IOException {
        File file = new File("Merged.txt");
        if (file.exists()){
            file.delete();
        }

        //создание списка имен файлов
        List<Path> inputs = new ArrayList<>();
        for(int i=0;i<100;i++) {
            String path = new String("files\\" + (i + 1) + ".txt");
            inputs.add(Paths.get(path));
        }

        Path output = Paths.get("Merged.txt");
        Charset charset = StandardCharsets.UTF_8;
        Integer counter = 0;
        //проходимся по списку имен файлов и считываем строки
        for (Path path : inputs) {
            List<String> lines = Files.readAllLines(path, charset);
            //если пользователь указал набор символов для удаления строк, отбраковываем строки с заданным набором
            if(!((delLine.getText()).isEmpty())) {
                ArrayList<String> fixedList = new ArrayList<String>();
                for (String testString:lines){
                    if (!(testString.contains(delLine.getText()))) fixedList.add(testString);
                    else counter++;
                }
                Files.write(output, fixedList, charset, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
            //в противном случае, просто записываем все строки в файл Merged.txt
            else {
                Files.write(output, lines, charset, StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
        }
        if (!((delLine.getText()).isEmpty())) report.setText("Было удалено "+Integer.toString(counter)+" строк");
    }
}
