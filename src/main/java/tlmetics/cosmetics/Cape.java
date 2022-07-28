package tlmetics.cosmetics;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tlmetics.render.cape.CapeRenderer;

public class Cape {
	private final String name;
	private final LocationProvider locationProvider;
	private final CapeRenderer renderer;

	public Cape(String name, LocationProvider locationProvider) {
		this.name = name;
		this.locationProvider = locationProvider;
		this.renderer = null;
	}

	public Cape(String name, CapeRenderer renderer) {
		this.name = name;
		this.renderer = renderer;
		this.locationProvider = null;
	}

	public String getName() {
		return name;
	}

	@Nullable
	public LocationProvider getLocationProvider() {
		return locationProvider;
	}

	@Nullable
	public CapeRenderer getRenderer() {
		return renderer;
	}

	@FunctionalInterface
	public interface LocationProvider {
		Identifier locate(PlayerEntity player, World world);
	}
}
