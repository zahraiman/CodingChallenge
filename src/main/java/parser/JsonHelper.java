package parser;

import com.google.gson.stream.MalformedJsonException;
import model.Record;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private static final Logger jsonLogger = Logger.getLogger( JsonHelper.class.getName() );


    public static List<Record> readRecords(String inputFileName) throws IOException {
        if (new File(inputFileName).exists()) {
            return readJsonStream(new FileReader(inputFileName));
        }
        return null;
    }

    private static List<Record> readJsonStream(FileReader in) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter((new ArrayList<Record>()).getClass(), new LeadDeserializer()).create();
        try {
            List<Record> records = gson.fromJson(new BufferedReader(in), (new ArrayList<Record>()).getClass());
            return records;
        }catch(Exception e){
            jsonLogger.error("ERROR: The input file has the wrong json format for leads!");
        }
        return null;
    }

    public static void writeRecords(List<Record> records, String outputFileName) throws IOException {
        writeJsonStream(new FileWriter(outputFileName), records);
    }

    private static void writeJsonStream(FileWriter out, List<Record> records) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter((new ArrayList<Record>()).getClass(), new LeadSerializer()).create();
        BufferedWriter outWriter = new BufferedWriter(out);
        gson.toJson(records, outWriter);
        outWriter.flush();
        outWriter.close();
    }

    public static String getRecordJson(Record record){
        Gson gson = new GsonBuilder().registerTypeAdapter(Record.class, new RecordSerializer()).create();
        return gson.toJson(record);
    }

}
