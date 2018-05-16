package parser;

import model.Record;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LeadSerializer implements JsonSerializer<ArrayList<Record>>
{

    @Override
    public JsonObject serialize(ArrayList<Record> records, Type typeOfT,
                                    JsonSerializationContext ctx) throws JsonParseException {
        JsonArray ja = new JsonArray();
        if (records == null)
            return null;
        else {

            for (Record bc : records) {
                ja.add(ctx.serialize(bc, Record.class));

            }
        }

        JsonObject global = new JsonObject();
        global.add("leads", ja);
        return global;
    }


}