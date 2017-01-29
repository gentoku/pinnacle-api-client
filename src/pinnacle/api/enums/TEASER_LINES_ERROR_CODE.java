package pinnacle.api.enums;

public enum TEASER_LINES_ERROR_CODE {

	INVALID_LEGS,
	SAME_EVENT_ONLY_REQUIRED,
	TEASER_DISABLED,
	TEASER_DOES_NOT_EXIST,
	TOO_FEW_LEGS,
	TOO_MANY_LEGS,
	UNKNOWN,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static TEASER_LINES_ERROR_CODE fromAPI (String text) {
		try {
			return TEASER_LINES_ERROR_CODE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return TEASER_LINES_ERROR_CODE.UNDEFINED;
		}
	}
}
