package Persistance;

import Business.players.Doctor;
import Business.players.Enginyer;
import Business.players.Master;
import Business.players.Player;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Allows serialization and deserialization of a Player (solving inheritance) for a JSON file.
 */
public class PlayerInterfaceAdapter implements JsonDeserializer<Player>, JsonSerializer<Player> {

    /**
     * Deserializes json allowing us to read different types of players from the same inheritance.
     * @param jsonElement node of a json structure.
     * @param type type of player (Doctor, Enginyer or Master).
     * @param jsonDeserializationContext context for deserialization that is passed to a custom deserializer.
     * @return instance of Player
     * @throws JsonParseException standard exception.
     */
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

    /**
     * Serializes json allowing us to save different types of players from the same inheritance.
     * @param player instance of Player.
     * @param type type of player (Doctor, Enginyer or Master).
     * @param context context for deserialization that is passed to a custom deserializer.
     * @return node of a json structure.
     */
    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext context) {

        return context.serialize(player, getObjectClass(player.getType()));
    }
}
