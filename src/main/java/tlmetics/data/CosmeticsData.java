package tlmetics.data;

import net.fabricmc.loader.api.FabricLoader;
import tlmetics.CosmeticsMod;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CosmeticsData {
    public static final List<EntryData> ENTRIES = new ArrayList<>();
    public static String url = "https://gist.githubusercontent.com/PinkGoosik/52ca9c6cb15a7b81352aa8a44134bae1/raw";

    public static void reload() {
        try {
            ENTRIES.clear();
            URL url = new URL(CosmeticsData.url);
            URLConnection request = url.openConnection();
            request.connect();
            InputStreamReader reader = new InputStreamReader(request.getInputStream());
            EntryData[] entries = CosmeticsMod.GSON.fromJson(reader, EntryData[].class);
            ENTRIES.addAll(Arrays.asList(entries));

            ENTRIES.forEach(e -> {
                System.out.println("user: " + e.user.name + ", cape: " + e.cape.name + ", cosmetics: " + e.cosmetics);
            });
        }
        catch(Exception e) {
            CosmeticsData.ENTRIES.clear();
            if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
                CosmeticsMod.LOGGER.warn("Failed to load entries due to an exception:\n" + e);
            }
        }
        finally {
            if(!CosmeticsData.ENTRIES.isEmpty()) {
                if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
                    CosmeticsMod.LOGGER.info("Entries successfully loaded");
                }
            }
        }
    }

    public static Optional<EntryData> getEntry(UUID uuid, String name) {
        for(var entry : ENTRIES) {
            if(uuid.equals(UUID.fromString(entry.user.uuid)) || name.equals(entry.user.name)) {
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }
}
