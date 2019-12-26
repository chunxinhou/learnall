package log4j;

import io.sentry.Sentry;
import org.apache.log4j.Logger;

/**
 * @author houchunxin
 */
public class Log4jLearn {

    private static Logger logger = Logger.getLogger(Log4jLearn.class);


    public static void main(String[] args) {
       // while (true) {
            logger.error("error");
            logger.info("info");
            logger.debug("debug");
        //}
    }

}
