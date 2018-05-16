import model.Record;
import parser.JsonHelper;
import service.Deduplicator;
import service.RecordDiffLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String inputFileName = "data/leads.json";

    public static void main(String[] args) throws IOException {
        List<Record> testRecords = JsonHelper.readRecords(inputFileName);
        Deduplicator deduplicator = new Deduplicator();
        RecordDiffLogger.startDeduplicatorLogger();
        ArrayList<Record> uniqueRecords = deduplicator.deduplicate((List<Record>) testRecords);// deduplicator.deduplicateSequentially(testRecords);
        RecordDiffLogger.finishDeduplicatorLogger();
        JsonHelper.writeRecords(uniqueRecords, "data/leads_out.json");
    }
}
