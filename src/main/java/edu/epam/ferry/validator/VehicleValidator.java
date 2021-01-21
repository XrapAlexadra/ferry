package edu.epam.ferry.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class VehicleValidator {

    private static final Logger logger = LogManager.getLogger(VehicleValidator.class);

    private VehicleValidator() {
    }

    public static boolean isValidData(List<Integer> data){
        boolean result = true;
        if(data.size() != 2){
            result = false;
            logger.info(data + " invalid data! Number of parameters must be 2.");
        }
        for (int i : data){
            if(i <= 0 ){
                result = false;
                logger.info(i + " invalid number! Parameter must be > 0.");
            }
        }
        return result;
    }
}
