package tlmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tlmetics.cosmetics.Capes;
import tlmetics.cosmetics.Cosmetics;
import tlmetics.data.CosmeticsData;
import tlmetics.data.Meta;
import tlmetics.event.ClientTickEvent;
import tlmetics.model.CosmeticModelLayers;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public class CosmeticsMod implements ClientModInitializer {
    public static final String MOD_ID = "tlmetics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public static final int FORMAT = 0;

    @Override
    public void onInitializeClient() {
        CosmeticsMod.getMeta().ifPresent(meta -> {
            if(meta.format == FORMAT) {
                Capes.init();
                Cosmetics.init();
                CosmeticModelLayers.LAYERS.forEach((layer, data) -> EntityModelLayerRegistry.registerModelLayer(layer, () -> data));
                if(!meta.redirect.isBlank()) {
                    CosmeticsData.url = meta.redirect;
                }
                ClientTickEvents.END_CLIENT_TICK.register(client -> ClientTickEvent.onTick());
            }
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    private static Optional<Meta> getMeta() {
        try {
            URL url = new URL("https://gist.githubusercontent.com/PinkGoosik/b28b40592f846c40d8ddc6d8fc7a260a/raw/7b6e835f26629e838661b4d1f335e441b1d71343/meta.json");
            URLConnection request = url.openConnection();
            request.connect();
            InputStreamReader reader = new InputStreamReader(request.getInputStream());
            Meta meta = CosmeticsMod.GSON.fromJson(reader, Meta.class);
            return Optional.of(meta);
        }
        catch(Exception e) {
            if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
                CosmeticsMod.LOGGER.warn("Failed to load meta due to an exception:\n" + e);
            }
        }
        return Optional.empty();
    }
}
