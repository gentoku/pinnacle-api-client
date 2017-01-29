package pinnacle.api.enums;

public enum ODDS_FORMAT {

	AMERICAN,
	DECIMAL,
	HONGKONG,
	INDONESIAN,
	MALAY,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static ODDS_FORMAT fromAPI (String text) {
		try {
			return ODDS_FORMAT.valueOf(text);
		} catch (IllegalArgumentException e) {
			return ODDS_FORMAT.UNDEFINED;
		}
	}
}
