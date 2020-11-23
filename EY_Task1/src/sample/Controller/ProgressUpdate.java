package sample.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javafx.concurrent.Task;
import sample.main;

public class ProgressUpdate extends Task<List<Data>> {
    private List<Data> dataList;
    private Connection conn = main.returnCon();

    public ProgressUpdate(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    protected List<Data> call() throws Exception {
        int counter, count;
        count = dataList.size();
        counter = 0;
        //загружаем в СУБД считанные из файла строки и обновляем окно прогресса
        for (Data dataline: dataList) {
            String query = " insert into lines (date, rus, eng, intnum, doublenum)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, dataline.getDate());
            preparedStmt.setString (2, dataline.getRus());
            preparedStmt.setString   (3, dataline.getEng());
            preparedStmt.setInt(4, dataline.getIntnum());
            preparedStmt.setDouble    (5, dataline.getDoublenum());
            preparedStmt.execute();
            //Thread.sleep(1);
            counter++;
            this.updateMessage("Cкопировано "+counter+" из "+count+" строк");
            this.updateProgress(counter, count);
        }
        return dataList;
    }

}
