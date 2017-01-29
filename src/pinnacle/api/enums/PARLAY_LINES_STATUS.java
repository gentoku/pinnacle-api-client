package pinnacle.api.enums;

public enum PARLAY_LINES_STATUS {

	PROCESSED_WITH_ERROR,
	VALID,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PARLAY_LINES_STATUS fromAPI (String text) {
		try {
			return PARLAY_LINES_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PARLAY_LINES_STATUS.UNDEFINED;
		}
	}
}
