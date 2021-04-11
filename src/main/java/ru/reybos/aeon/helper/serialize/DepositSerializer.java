package ru.reybos.aeon.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.reybos.aeon.model.Deposit;

import java.lang.reflect.Type;

public class DepositSerializer implements JsonSerializer<Deposit> {
    @Override
    public JsonElement serialize(
            Deposit deposit, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", deposit.getId());
        result.addProperty("balance", deposit.getBalance());
        return result;
    }
}
