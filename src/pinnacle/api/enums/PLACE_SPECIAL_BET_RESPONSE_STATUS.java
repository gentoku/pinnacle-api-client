package pinnacle.api.enums;

public enum PLACE_SPECIAL_BET_RESPONSE_STATUS {

	ACCEPTED,
	PROCESSED_WITH_ERROR,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PLACE_SPECIAL_BET_RESPONSE_STATUS fromAPI (String text) {
		try {
			return PLACE_SPECIAL_BET_RESPONSE_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PLACE_SPECIAL_BET_RESPONSE_STATUS.UNDEFINED;
		}
	}
}
