package MainFiles;

import java.util.HashMap;
import java.util.List;

public interface HouseSearch {
        public void searchHouses(HashMap<String, String> searchKeys);

	public final String ZIP_CODE = "ZIP_CODE";
        public final String MIN_PRICE = "MIN_PRICE";
        public final String MAX_PRICE = "MAX_PRICE";
	public final String MIN_BATH = "MIN_BATH";
        public final String MIN_BED = "MIN_BED";
        
	public List<HouseInfo> getHouses();
}
