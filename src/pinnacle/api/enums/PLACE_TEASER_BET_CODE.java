package pinnacle.api.enums;

public enum PLACE_TEASER_BET_CODE {

	BLOCKED_BETTING,
	DUPLICATED_REQUEST,
	RESUBMIT_REQUEST,
	UNEXPECTED_ERROR,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static PLACE_TEASER_BET_CODE fromAPI (String text) {
		try {
			return PLACE_TEASER_BET_CODE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return PLACE_TEASER_BET_CODE.UNDEFINED;
		}
	}
}
