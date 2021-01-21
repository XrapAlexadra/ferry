package edu.epam.ferry.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VehicleParser {

    private static final Logger logger = LogManager.getLogger(VehicleParser.class);

    private static final String NUMBER_MATCHER = "\\d+";
    private static final String NUMBER_SEPARATOR = " +";

    private static VehicleParser instance = new VehicleParser();


    private VehicleParser() {
    }

    public static VehicleParser getInstance() {
        return instance;
    }

    public List<Integer> parse(String line){
        List<Integer> data = new ArrayList<>();
        String[] numbers = line.split(NUMBER_SEPARATOR);
        int i = 0;
        while (i < numbers.length){
            String temp = numbers[i];
            if(temp.matches(NUMBER_MATCHER)){
                int number = Integer.parseInt(temp);
                data.add(number);
            }
            else {
                logger.error("{} has invalid double number: {}!", line, temp);
                data.clear();
                break;
            }
            i++;
        }
        logger.info( "Parse: " + line + " to " + data);
        return data;
    }
}
