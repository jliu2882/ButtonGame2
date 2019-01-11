package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {
    private static final String dbFile = "src/ButtonGames/db.csv";

    public static List<List<String>> read(int lines) {
        CSVUtilities helper = new CSVUtilities(new File(Controller.dbFile));

        List<List<String>> data = new ArrayList<>();
        List<String> columns = helper.getColumnHeaders();

        IntStream.range(0, lines).forEach(i -> {
            List<String> rowData = new ArrayList<>();

            IntStream.range(0, columns.size()).forEach(col -> {
                // TODO: Reduce I/O operations
                List<String> columnData = helper.getDataString(col + 1);
                if (i < columnData.size()) rowData.add(columnData.get(i));
            });
            if (rowData.size() > 0) data.add(rowData);
        });
        return data;
    }

    public static List<List<String>> read() {
        return Controller.read((new CSVUtilities(new File(Controller.dbFile))).getDataString(1).size());


}
