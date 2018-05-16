package parser;

import model.Record;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RecordSerializer  implements JsonSerializer<Record> {
    @Override
    public JsonElement serialize(final Record record, final Type type, final JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("_id", new JsonPrimitive(record.get_id()));
        result.add("email", new JsonPrimitive(record.getEmail()));
        result.add("firstName", new JsonPrimitive(record.getFirstName()));
        result.add("lastName", new JsonPrimitive(record.getLastName()));
        result.add("entryDate", new JsonPrimitive(record.getEntryDate().toString()));
        result.add("address", new JsonPrimitive(record.getAddress()));
        return result;
    }
}
