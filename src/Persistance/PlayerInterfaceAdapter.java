package Persistance;

import Business.players.Doctor;
import Business.players.Enginyer;
import Business.players.Master;
import Business.players.Player;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PlayerInterfaceAdapter implements JsonDeserializer<Player>, JsonSerializer<Player> {

    @Override
    public Player deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement element = jsonObject.get("type");
        JsonPrimitive prim = (JsonPrimitive) element;
        String className = prim.getAsString();

        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonElement, klass);
    }

    private Class getObjectClass(String className) {
        switch (className) {
            case Doctor.TYPE -> {
                return Doctor.class;
            }
            case Enginyer.TYPE -> {
                return Enginyer.class;
            }
            case Master.TYPE -> {
                return Master.class;
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext context) {

        return context.serialize(player, getObjectClass(player.getType()));
    }
}
