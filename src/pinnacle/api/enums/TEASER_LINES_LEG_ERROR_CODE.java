package pinnacle.api.enums;

public enum TEASER_LINES_LEG_ERROR_CODE {

	EVENT_NOT_FOUND,
	POINTS_NO_LONGER_AVAILABLE,
	UNKNOWN,
	WAGER_TYPE_NOT_VALID_FOR_TEASER,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static TEASER_LINES_LEG_ERROR_CODE fromAPI (String text) {
		try {
			return TEASER_LINES_LEG_ERROR_CODE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return TEASER_LINES_LEG_ERROR_CODE.UNDEFINED;
		}
	}
}
