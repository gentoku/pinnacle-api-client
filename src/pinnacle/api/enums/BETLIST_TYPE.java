package pinnacle.api.enums;

public enum BETLIST_TYPE {

	SETTLED,
	RUNNING,
	CANCELLED,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static BETLIST_TYPE fromAPI (String text) {
		try {
			return BETLIST_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return BETLIST_TYPE.UNDEFINED;
		}
	}
}
