package tlmetics.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import tlmetics.Mod;
import tlmetics.cosmetics.Cape;
import tlmetics.cosmetics.Capes;
import tlmetics.data.Meta;

import java.util.Base64;
import java.io.ByteArrayInputStream;

public class CapeDecoder {

	public static void load(Meta meta) {
		Util.getMainWorkerExecutor().execute(() -> meta.capes.forEach(cape -> {
			try {
				byte[] bytes = Base64.getDecoder().decode(cape.code);
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
				var tex = new NativeImageBackedTexture(NativeImage.read(stream));
				Identifier path = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("tlmetics-cape-" + cape.name, tex);
				Capes.add(new Cape(cape.name, (player, world) -> path));
			}
			catch(Exception e) {
				Mod.log("Failed to decode cape due to an exception: " + e);
			}
		}));
	}
}
