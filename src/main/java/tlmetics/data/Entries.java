package tlmetics.data;

import org.apache.commons.lang3.RandomStringUtils;
import tlmetics.Mod;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Entries {
	private static final List<EntryData> ENTRIES = new ArrayList<>();
	public static String url = "https://gist.githubusercontent.com/PinkGoosik/52ca9c6cb15a7b81352aa8a44134bae1/raw";

	public static void reload() {
		try {
			ENTRIES.clear();
			String urlStr = Entries.url;
			urlStr = urlStr.replaceAll("%random%", RandomStringUtils.randomAlphabetic(9));
			URL url = new URL(urlStr);
			URLConnection request = url.openConnection();
			request.connect();
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			EntryData[] entries = Mod.GSON.fromJson(reader, EntryData[].class);
			ENTRIES.addAll(Arrays.asList(entries));
			ENTRIES.forEach(e -> Mod.log("user: " + e.user.name + ", cape: " + e.cape.name + ", cosmetics: " + e.cosmetics));
		}
		catch(Exception e) {
			ENTRIES.clear();
			Mod.log("Failed to load entries due to an exception:\n" + e);
		}
	}

	public static void clear() {
		if(!ENTRIES.isEmpty()) {
			ENTRIES.clear();
			Mod.log("Entries data successfully cleared");
		}
	}

	public static Optional<EntryData> get(UUID uuid, String name) {
		for(var entry : ENTRIES) {
			if(uuid.equals(UUID.fromString(entry.user.uuid)) || name.equals(entry.user.name)) {
				return Optional.of(entry);
			}
		}
		return Optional.empty();
	}
}
