package Persistance;

import Business.trials.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter implements JsonDeserializer<Trial>, JsonSerializer<Trial> {

    /**
     * Deserializes json allowing us to read diferent types of Trials from the same interface.
     * @param jsonElement node of a json structure.
     * @param type type of trial (Article, Estudi, Defensa or Solicitud).
     * @param jsonDeserializationContext context for deserialization that is passed to a custom deserializer.
     * @return instance of Trial.
     * @throws JsonParseException standard exception.
     */
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

    /**
     * Serializes json allowing us to save diferent types of Trials from the same interface.
     * @param trial instance of trial to serialize.
     * @param type type of trial (Article, Estudi, Defensa or Solicitud).
     * @param context context for deserialization that is passed to a custom deserializer.
     * @return node of a json structure.
     */
    @Override
    public JsonElement serialize(Trial trial, Type type, JsonSerializationContext context) {

        return context.serialize(trial, getObjectClass(trial.getType()));
    }
}
