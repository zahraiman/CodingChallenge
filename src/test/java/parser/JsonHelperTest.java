package parser;


import model.Record;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JsonHelperTest {

    @Test
    public void jsonReaderReturnsNullForNonExisitingFile() throws IOException {
        String fakeFile = "fakeFileName.json";
        assertEquals(null, JsonHelper.readRecords(fakeFile));
    }

    @Test
    public void jsonReaderReturnsTwoRecordsForFileWithTwoRecords() throws IOException {
        String twoRecordFile = "twoRecordFile.json";
        FileWriter fw = new FileWriter(twoRecordFile);
        fw.write("{\"leads\":[{\"_id\": \"jkj238238jdsnfsj23\",\"email\": \"foo@bar.com\",\"firstName\":  \"John\",\"lastName\": \"Smith\",\"address\": \"123 Street St\",\"entryDate\": \"2014-05-07T17:30:20+00:00\"},{\"_id\": \"edu45238jdsnfsj23\",\"email\": \"mae@bar.com\",\"firstName\":  \"Ted\",\"lastName\": \"Masters\",\"address\": \"44 North Hampton St\",\"entryDate\": \"2014-05-07T17:31:20+00:00\"}]}");
        fw.close();
        assertEquals(Objects.requireNonNull(JsonHelper.readRecords(twoRecordFile)).size(), 2);
    }

    @Test
    public void jsonReaderReturnsNullForWrongJsonSyntax() throws IOException {
        String wrongJsonSyntax = "wrongJsonSyntax.json";
        FileWriter fw = new FileWriter(wrongJsonSyntax);
        fw.write("{\"leads\":[{\"_id\": \"jkj238238jdsnfsj23\",\"email\": \"foo@bar.com\",\"firstName\":  \"John\",\"lastName\": \"Smith\",\"address\": \"123 Street St\",\"entryDate\": \"2014-05-07T17:30:20+00:00\"},{\"_id\": \"edu45238jdsnfsj23\",\"email\": \"mae@bar.com\",\"firstName\":  \"Ted\",\"lastName\": \"Masters\",\"address\": \"44 North Hampton St\",\"entryDate\": \"2014-05-07T17:31:20+00:00\"}}");
        fw.close();
        assertNull(JsonHelper.readRecords(wrongJsonSyntax));
    }

    @Test
    public void jsonReaderReturnsEmptyForFileWithWrongFormatRecords() throws IOException {
        String wrongFormatFile = "wrongFormatFile.json";
        FileWriter fw = new FileWriter(wrongFormatFile);
        fw.write("{\"leadsWRONG\":[{\"_id\": \"jkj238238jdsnfsj23\",\"email\": \"foo@bar.com\",\"firstName\":  \"John\",\"lastName\": \"Smith\",\"address\": \"123 Street St\",\"entryDate\": \"2014-05-07T17:30:20+00:00\"},{\"_id\": \"edu45238jdsnfsj23\",\"email\": \"mae@bar.com\",\"firstName\":  \"Ted\",\"lastName\": \"Masters\",\"address\": \"44 North Hampton St\",\"entryDate\": \"2014-05-07T17:31:20+00:00\"}}");
        assertNull(JsonHelper.readRecords(wrongFormatFile));
        new File(wrongFormatFile).delete();;
    }

    @Test
    public void jsonWriterReturnsTwoRecordsForFileWithTwoRecords() throws IOException {
        String twoRecordFile = "twoRecordFile.json";
        ArrayList<Record> twoRecords = new ArrayList<>(2);
        twoRecords.add(new Record("id1", "email1@email.com", "name1", "familyName1", "address1", new Date(10546), 0));
        twoRecords.add(new Record("id2", "email2@email.com", "name2", "familyName2", "address2", new Date(105462), 1));
        JsonHelper.writeRecords(twoRecords, twoRecordFile);

        //read the records back from json
        List<Record> records = JsonHelper.readRecords(twoRecordFile);
        assertEquals(Objects.requireNonNull(JsonHelper.readRecords(twoRecordFile)).size(), 2);
        assertTrue(records.get(0).equals(twoRecords.get(0)));
        assertTrue(records.get(1).equals(twoRecords.get(1)));
    }

    @Test
    public void getRecordJsonReturnsCorrectJson() throws ParseException {
        Record record = new Record("id1", "email1@email.com", "name1", "familyName1", "address1", new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse("07-05-14 13:30:20"), 0);
        String jsonRecord = JsonHelper.getRecordJson(record);
        assertEquals(jsonRecord, "{\"_id\":\"id1\",\"email\":\"email1@email.com\",\"firstName\":\"name1\",\"lastName\":\"familyName1\",\"entryDate\":\"Wed May 07 13:30:20 EDT 2014\",\"address\":\"address1\"}");
    }

    @After
    public void deleteGeneratedFiles(){
        String wrongFormatFile = "wrongFormatFile.json";
        String twoRecordFile = "twoRecordFile.json";
        String wrongJsonSyntax = "wrongJsonSyntax.json";
        new File(wrongFormatFile).delete();
        new File(twoRecordFile).delete();
        new File(wrongJsonSyntax).delete();
    }

}
