package tlmetics.cosmetics;

import tlmetics.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Capes {
	private static final List<Cape> CAPES = new ArrayList<>();

	public static void init() {
//        add(new UniCloak());
		addGrouped("colored", "azure", "crimson", "flamingo", "golden", "lapis", "military", "mint", "mystic", "pumpkin", "smoky", "turtle", "violet", "void", "coffee");
//        addGrouped("pride", "pride", "trans", "lesbian", "gay", "pan", "bi", "non-binary", "genderfluid", "aromantic", "demiromantic", "asexual", "demisexual");
//        addGrouped("patterned", "space");

//        add(new Cape("jeb", new JebCloakRenderer(false)));
//        add(new Cape("enchanted-jeb", new JebCloakRenderer(true)));
//        add(new Cape("cosmic", new CosmicCloakRenderer()));
//        add(new Cape("swirly", new SwirlyCloakRenderer()));
//        add(new Cape("glowing", new GlowingCloakRenderer()));
	}

	private static void addGrouped(String group, String... names) {
		for(String name : names) {
			add(new Cape(name, (player, world) -> Mod.id("textures/cape/" + group + "/" + name + ".png")));
		}
	}

	public static void add(Cape cape) {
		CAPES.removeIf(c -> c.getName().equals(cape.getName()));
		CAPES.add(cape);
	}

	public static Optional<Cape> get(String name) {
		for(Cape cape : CAPES) if(cape.getName().equals(name)) return Optional.of(cape);
		return Optional.empty();
	}
}
