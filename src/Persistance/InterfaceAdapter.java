package Persistance;

import Business.trials.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter implements JsonDeserializer<Trial>, JsonSerializer<Trial> {

    @Override
    public Trial deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement element = jsonObject.get("type");
        JsonPrimitive prim = (JsonPrimitive) element;
        String className = prim.getAsString();

        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonElement, klass);
    }

    private Class getObjectClass(String className) {
        switch (className) {
            case Article.TYPE -> {
                return Article.class;
            }
            case Defensa.TYPE -> {
                return Defensa.class;
            }
            case Estudi.TYPE -> {
                return Estudi.class;
            }
            case Solicitud.TYPE -> {
                return Solicitud.class;
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize(Trial trial, Type type, JsonSerializationContext context) {

        return context.serialize(trial, getObjectClass(trial.getType()));
    }
}
