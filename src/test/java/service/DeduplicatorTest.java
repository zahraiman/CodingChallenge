package service;

import model.Record;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class DeduplicatorTest {
    private Deduplicator deduplicator;
    private SimpleDateFormat sdf;
    @Before
    public void setUp(){
        deduplicator = new Deduplicator();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    public void deduplicateRemovesDuplicatesFromListCorrectly() throws ParseException {
        String recordsStr = "1 iman@yay im iman 01/01/2000,2 zahra@yay za zahra 01/02/2000,3 zahra@yay za zahra 01/03/2000,1 zahra@yay za zahra 01/04/2000";
        List<Record> recordsWithDuplication = buildRecordsFromString(recordsStr);

        ArrayList<Record> uniqueRecords = deduplicator.deduplicate(recordsWithDuplication);
        assertEquals(uniqueRecords.size(), 1);
        assertTrue(uniqueRecords.get(0).equals(recordsWithDuplication.get(3)));
    }

    @Test
    public void deduplicateRemovesDuplicatesFromListCorrectly2() throws ParseException {
        String recordsStr = "1 iman@yay im iman 01/01/2000,2 zahra@yay za zahra 01/02/2000,3 zahra@yay za zahra 01/03/2000,2 iman@yay za zahra 01/04/2000";
        List<Record> recordsWithDuplication = buildRecordsFromString(recordsStr);

        ArrayList<Record> uniqueRecords = deduplicator.deduplicate(recordsWithDuplication);
        assertEquals(uniqueRecords.size(), 2);
        assertTrue(uniqueRecords.get(0).equals(recordsWithDuplication.get(2)));
        assertTrue(uniqueRecords.get(1).equals(recordsWithDuplication.get(3)));
    }

    private List<Record> buildRecordsFromString(String recordsStr) throws ParseException {
        List<Record> records = new ArrayList<>();
        int recordCounter = 0;
        for(String recordStr : recordsStr.split(",")){
            String[] splits = recordStr.split(" ");
            records.add(new Record(splits[0], splits[1], splits[2], splits[3], "", sdf.parse(splits[4]), recordCounter));
            recordCounter++;
        }
        return records;
    }

}

