package tlmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tlmetics.command.CosmeticCommands;
import tlmetics.cosmetics.Capes;
import tlmetics.cosmetics.Cosmetics;
import tlmetics.data.Entries;
import tlmetics.data.Meta;
import tlmetics.event.ClientTickEvent;
import tlmetics.model.CosmeticModelLayers;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class Mod implements ClientModInitializer {
    public static final String MOD_ID = "tlmetics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public static final int FORMAT = 0;

    @Override
    public void onInitializeClient() {
        Mod.fetchMeta().ifPresent(meta -> {
            if(meta.format == FORMAT) {
                Cosmetics.init();
                Capes.init();
                CosmeticModelLayers.init();
                CosmeticCommands.init();
                if(!meta.redirect.isBlank()) Entries.url = meta.redirect;
                ClientTickEvents.END_CLIENT_TICK.register(client -> ClientTickEvent.onTick());
            }
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static void log(String message) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Mod.LOGGER.info(message);
        }
    }

    private static Optional<Meta> fetchMeta() {
        try {
            URL url = new URL("https://gist.githubusercontent.com/PinkGoosik/f0bf37f7644821da7498e0980724a759/raw");
            URLConnection request = url.openConnection();
            request.connect();
            InputStreamReader reader = new InputStreamReader(request.getInputStream());
            Meta meta = Mod.GSON.fromJson(reader, Meta.class);
            return Optional.of(meta);
        }
        catch(Exception e) {
            Mod.log("Failed to load meta due to an exception:\n" + e);
        }
        return Optional.empty();
    }
}
