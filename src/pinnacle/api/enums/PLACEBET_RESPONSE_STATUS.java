package pinnacle.api.enums;

public enum PLACEBET_RESPONSE_STATUS {

	ACCEPTED,
	PENDING_ACCEPTANCE,
	PROCESSED_WITH_ERROR,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PLACEBET_RESPONSE_STATUS fromAPI (String text) {
		try {
			return PLACEBET_RESPONSE_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PLACEBET_RESPONSE_STATUS.UNDEFINED;
		}
	}
}
