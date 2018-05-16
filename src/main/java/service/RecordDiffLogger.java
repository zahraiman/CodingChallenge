package service;

import model.Record;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import parser.JsonHelper;
import org.apache.log4j.Logger;

import java.io.IOException;

public class RecordDiffLogger {

    private static final Logger diffLogger = Logger.getLogger( Record.class.getName() );

    public static void startDeduplicatorLogger() throws IOException {
        diffLogger.addAppender(new FileAppender(new PatternLayout(), "./logs/deduplicator-log.log", false));
        diffLogger.info("{\"DiffLog\": [" );
    }

    public static void finishDeduplicatorLogger(){
        diffLogger.info("{}]}" );
    }

    public static void logFieldDifferences(Record remaining, Record duplicate)  {
        StringBuilder sb = new StringBuilder("");
        sb.append("{\"source record\": ");
        sb.append(JsonHelper.getRecordJson(duplicate));
        sb.append(", \"output record\" : ");
        sb.append(JsonHelper.getRecordJson(remaining));

        try {
            sb.append(", \"change\": {");
            sb.append(remaining.populateFieldDifferences(duplicate));
            sb.deleteCharAt(sb.length()-1);
            sb.append("}},");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String diffMsg = sb.toString();
        diffLogger.info(diffMsg);
    }

}
