package tlmetics.event;

import net.minecraft.client.MinecraftClient;
import tlmetics.data.CosmeticsData;

public class ClientTickEvent {
    private static boolean isLoaded = false;

    public static void onTick() {
        if(MinecraftClient.getInstance().world != null) {
            if(!isLoaded) {
                CosmeticsData.reload();
                isLoaded = true;
            }
        }
        else isLoaded = false;
    }
}
