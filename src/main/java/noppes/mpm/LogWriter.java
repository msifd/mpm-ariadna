package noppes.mpm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LogWriter {
    private static Logger logger = Logger.getLogger("MorePlayerModels");

    static {
        try {
            File file = new File("logs/MorePlayerModels-latest.log");
            File file1 = new File("logs/MorePlayerModels-1.log");
            File file2 = new File("logs/MorePlayerModels-2.log");
            File file3 = new File("logs/MorePlayerModels-3.log");
            if (file3.exists())
                file3.delete();
            if (file2.exists())
                file2.renameTo(file3);
            if (file1.exists())
                file1.renameTo(file2);
            if (file.exists()) {
                file.renameTo(file1);
            }
            Handler handler = new FileHandler("logs/MorePlayerModels-latest.log");
            handler.setLevel(Level.ALL);
            handler.setFormatter(new Formatter() {
                public String format(LogRecord record) {
                    return "[" + new SimpleDateFormat("HH:mm:ss").format(new Date(record.getMillis())) + "] [MorePlayerModels/" + record.getLevel() + "]" + record.getMessage() + "\n";
                }
            });
            logger.addHandler(handler);

            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(handler.getFormatter());
            consoleHandler.setLevel(Level.ALL);
            logger.addHandler(consoleHandler);

            logger.setLevel(Level.ALL);
            info(new Date().toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(Object msg) {
        logger.log(Level.FINE, msg.toString());
    }

    public static void error(Object msg) {
        logger.log(Level.SEVERE, msg.toString());
    }

    public static void error(Object msg, Exception e) {
        logger.log(Level.SEVERE, msg.toString(), e);
    }

    public static void except(Exception e) {
        logger.log(Level.SEVERE, e.getMessage(), e);
    }
}
