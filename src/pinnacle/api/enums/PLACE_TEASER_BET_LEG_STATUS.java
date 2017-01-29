package pinnacle.api.enums;

public enum PLACE_TEASER_BET_LEG_STATUS {

	PROCESSED_WITH_ERROR,
	VALID,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PLACE_TEASER_BET_LEG_STATUS fromAPI (String text) {
		try {
			return PLACE_TEASER_BET_LEG_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PLACE_TEASER_BET_LEG_STATUS.UNDEFINED;
		}
	}
}
