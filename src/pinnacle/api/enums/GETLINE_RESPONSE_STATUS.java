package pinnacle.api.enums;

public enum GETLINE_RESPONSE_STATUS {

	NOT_EXISTS,
	SUCCESS,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static GETLINE_RESPONSE_STATUS fromAPI (String text) {
		try {
			return GETLINE_RESPONSE_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return GETLINE_RESPONSE_STATUS.UNDEFINED;
		}
	}
}
