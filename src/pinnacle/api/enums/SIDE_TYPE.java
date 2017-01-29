package pinnacle.api.enums;

public enum SIDE_TYPE {

	OVER,
	UNDER,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static SIDE_TYPE fromAPI (String text) {
		try {
			return SIDE_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return SIDE_TYPE.UNDEFINED;
		}
	}
}
