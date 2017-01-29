package pinnacle.api.enums;

public enum TEASER_LINES_STATUS {

	PROCESSED_WITH_ERROR,
	VALID,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static TEASER_LINES_STATUS fromAPI (String text) {
		try {
			return TEASER_LINES_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return TEASER_LINES_STATUS.UNDEFINED;
		}
	}
}
