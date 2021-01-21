package edu.epam.ferry.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    private static final Logger logger = LogManager.getLogger(DataReader.class);

    private static final String DEFAULT_FILE_NAME = "data/data.txt";

    private static DataReader instance = new DataReader();

    private DataReader() {
    }

    public static DataReader getInstance(){
        return instance;
    }

    public List<String> readLines(){
        List<String> result = readLines(DEFAULT_FILE_NAME);
        return result;
    }

    public List<String> readLines(String fileName) {
        File file = new File(fileName);
        List<String> content = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            content = bufferedReader.lines().map(String::trim).collect(Collectors.toList());
            logger.info("Read from file: {} data: {}.", fileName, content);
        } catch (IOException e) {
            logger.error(e);
        }
        return content;
    }
}
