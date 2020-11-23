package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sample.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerWinCalculation {
    private Connection conn = main.returnCon();
    @FXML
    private Text sum1;
    @FXML
    private Text sum2;
    @FXML
    private Text mediana1;
    @FXML
    private Text mediana2;

    public void Calculation(ActionEvent actionEvent) throws IOException, SQLException {
        Statement statement = conn.createStatement();

        //подсчет суммы
        ResultSet results0 = statement.executeQuery("SELECT SUM (intnum) AS total FROM lines");
        BigDecimal sum = BigDecimal.valueOf(0);
        while(results0.next()) {
            sum = results0.getBigDecimal(1);
        }

        //подсчет медианы
        ResultSet results1 = statement.executeQuery("SELECT CASE WHEN c % 2 = 0 AND c > 1 THEN (a[1]+a[2])/2 ELSE a[1] END\n" +
                "FROM\n" +
                "    (\n" +
                "        SELECT ARRAY(SELECT doublenum FROM Lines ORDER BY doublenum OFFSET (c-1)/2 LIMIT 2) AS a, c\n" +
                "        FROM (SELECT count(*) AS c FROM Lines where doublenum is not null) AS count\n" +
                "        OFFSET 0\n" +
                "    )\n" +
                "        AS midrows;");
        Double mediana = 0.0;
        while(results1.next()) {
            mediana = results1.getDouble(1);
        }

        //вывод
        sum1.setText("Сумма");
        sum2.setText(sum.toString());
        mediana1.setText("Медиана");
        mediana2.setText(Double.toString(mediana));
    }
}
