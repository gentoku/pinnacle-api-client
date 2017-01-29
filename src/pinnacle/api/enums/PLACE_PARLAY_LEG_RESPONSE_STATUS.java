package pinnacle.api.enums;

public enum PLACE_PARLAY_LEG_RESPONSE_STATUS {

	PROCESSED_WITH_ERROR,
	VALID,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PLACE_PARLAY_LEG_RESPONSE_STATUS fromAPI (String text) {
		try {
			return PLACE_PARLAY_LEG_RESPONSE_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PLACE_PARLAY_LEG_RESPONSE_STATUS.UNDEFINED;
		}
	}
}
