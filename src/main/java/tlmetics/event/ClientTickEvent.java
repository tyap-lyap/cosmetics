package tlmetics.event;

import net.minecraft.client.MinecraftClient;
import tlmetics.animation.AnimationStateStuck;
import tlmetics.data.Entries;

public class ClientTickEvent {
	private static boolean isLoaded = false;
	private static boolean isStopped = true;

	public static void onWorldJoin() {
		Entries.reload();
	}

	public static void onWorldLeave() {
		Entries.clear();
		AnimationStateStuck.clear();
	}

	public static void onTick() {
		if(MinecraftClient.getInstance().world != null) {
			if(!isLoaded) {
				onWorldJoin();
				isLoaded = true;
			}
			isStopped = true;
		}
		else {
			if(isStopped) {
				onWorldLeave();
				isStopped = false;
			}
			isLoaded = false;
		}
	}
}
