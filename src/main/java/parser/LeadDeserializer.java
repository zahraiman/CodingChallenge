package parser;

import model.Record;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeadDeserializer  implements JsonDeserializer<ArrayList<Record>>
{
    private static int recordCounter = 0;
    @Override
    public ArrayList<Record> deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext ctx) throws JsonParseException {

        JsonObject _global = json.getAsJsonObject();
        ArrayList<Record> records = new ArrayList<Record>();
        JsonArray ja = _global.get("leads").getAsJsonArray();

        for (JsonElement je : ja) {
            Record currentRecord = ctx.deserialize(je, Record.class);
            currentRecord.setRecordNumber(recordCounter);
            recordCounter++;
            records.add(currentRecord);
        }

        return records;
    }
}