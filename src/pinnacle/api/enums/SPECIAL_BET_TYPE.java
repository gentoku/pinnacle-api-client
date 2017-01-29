package pinnacle.api.enums;

public enum SPECIAL_BET_TYPE {

	MULTI_WAY_HEAD_TO_HEAD,
	SPREAD,
	OVER_UNDER,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static SPECIAL_BET_TYPE fromAPI (String text) {
		try {
			return SPECIAL_BET_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return SPECIAL_BET_TYPE.UNDEFINED;
		}
	}
}
