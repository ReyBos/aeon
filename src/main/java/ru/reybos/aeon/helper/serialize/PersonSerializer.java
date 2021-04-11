package ru.reybos.aeon.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.aeon.model.Person;

import java.lang.reflect.Type;

public class PersonSerializer implements JsonSerializer<Person> {
    @Override
    public JsonElement serialize(
            Person person, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", person.getId());
        result.addProperty("login", person.getLogin());
        result.add("deposit", context.serialize(person.getDeposit()));
        return result;
    }
}