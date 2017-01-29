package pinnacle.api.enums;

public enum TEASER_REQUEST_CODE {

	INVALID_REQUEST_DATA,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static TEASER_REQUEST_CODE fromAPI (String text) {
		try {
			return TEASER_REQUEST_CODE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return TEASER_REQUEST_CODE.UNDEFINED;
		}
	}
}
