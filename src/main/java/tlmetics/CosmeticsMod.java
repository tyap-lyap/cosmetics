package tlmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tlmetics.cosmetics.Capes;
import tlmetics.cosmetics.Cosmetics;
import tlmetics.data.CosmeticsData;
import tlmetics.data.Meta;
import tlmetics.event.ClientTickEvent;
import tlmetics.model.CosmeticModelLayers;
import tlmetics.model.CosmeticModels;

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

//                CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
//                    dispatcher.register(CommandManager.literal("anim")
//                            .then(CommandManager.literal("start")
//                                    .then(CommandManager.argument("time", IntegerArgumentType.integer()).executes(context -> {
//                                        int age = MinecraftClient.getInstance().player.age;
//                                        CosmeticModels.HALO_MODEL.state.start(age);
//                                        return 1;
//                                    }))
//                            ).then(CommandManager.literal("stop").executes(context -> {
//                                CosmeticModels.HALO_MODEL.state.stop();
//                                return 1;
//                            })));
//                });
            }
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    private static Optional<Meta> getMeta() {
        try {
            URL url = new URL("https://gist.githubusercontent.com/PinkGoosik/f0bf37f7644821da7498e0980724a759/raw");
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
