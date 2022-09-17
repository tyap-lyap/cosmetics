package tlmetics.data;

import java.util.ArrayList;

public class Meta {
	public int format = 0;
	public String redirect = "";
	public ArrayList<RawCape> capes = new ArrayList<>();

	public static class RawCape {
		public String name = "";
		public String code = "";
	}
}
