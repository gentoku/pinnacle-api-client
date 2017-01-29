package pinnacle.api;

import java.util.Arrays;

import pinnacle.api.PinnacleException.NoEnumException;

/**
 * Official Enums provided by PinnacleSports.com.
 * See http://www.pinnaclesports.com/en/api/manual#Enums
 * @author Gentoku Tanaka
 */
public class Enums {

	public enum BET_STATUS {
		ACCEPTED,
		PENDING_ACCEPTANCE, // Live bets in danger zone
		REJECTED,	
		REFUNDED,	
		CANCELLED,	
		LOSE,
		WON;
	}
	
	public enum ODDS_FORMAT {
		AMERICAN,	
		DECIMAL,	
		HONGKONG,	
		INDONESIAN,	
		MALAY;
	}	
	
	public enum BET_TYPE {
		SPREAD(true, false, true),	
		MONEYLINE(true, false, false),	
		TOTAL_POINTS(false, true, true),	
		TEAM_TOTAL_POINTS(true, true, true);
		private final boolean requiresTeam;
		private final boolean requiresSide;
		private final boolean requiresHandicap;
		private BET_TYPE (final boolean team, final boolean side, final boolean handicap) { 
			this.requiresTeam = team;
			this.requiresSide = side;
			this.requiresHandicap = handicap;
		}
		public boolean requiresTeam () { return this.requiresTeam; }
		public boolean requiresSide () { return this.requiresSide; }
		public boolean requiresHandicap () { return this.requiresHandicap; }
	}
	
	public enum SIDE_TYPE {
		OVER,	
		UNDER;
	}

	@Deprecated // Uses primitive boolean or Boolean instead with functions following.
	public enum BOOLAEN {
		TRUE,
		FALSE;
	}

	static String boolean1 (boolean b) {
		return b ? "TRUE" : "FALSE";
	}
	
	static Boolean boolean1 (String value) {
		if (value.equals("TRUE")) return new Boolean(true);
		if (value.equals("FALSE")) return new Boolean(false);
		return null;
	}
	
	public enum WIN_RISK_TYPE {
		WIN,	// Stake is win amount
		RISK;	// Stake is risk amount
	}
	
	public enum PLACEBET_RESPONSE_STATUS {
		ACCEPTED, // Accepted
		PENDING_ACCEPTANCE, // Pending Acceptance. This is for live bets in danger zone
		PROCESSED_WITH_ERROR; // Processed with error
	}
	
	public enum PLACEBET_ERROR_CODE {
		ALL_BETTING_CLOSED, // Betting is not allowed at this moment
		ALL_LIVE_BETTING_CLOSED, // Live betting is not allowed at this moment
		BLOCKED_CLIENT, // Client is no longer active
		INVALID_COUNTRY, // Client country is not allowed for betting
		BLOCKED_BETTING, // Not allowed betting for the client
		INVALID_EVENT, // Invalid eventid
		ABOVE_MAX_BET_AMOUNT, // Stake is above allowed maximum amount
		BELOW_MIN_BET_AMOUNT, // Stake is below allowed minimum amount
		OFFLINE_EVENT, // Bet is submitted on a event that is offline
		INSUFFICIENT_FUNDS, // Bet is submitted by a client with insufficient funds
		LINE_CHANGED, // Bet is submitted on a line that has changed
		RED_CARDS_CHANGED, // Bet is submitted on a live soccer event with changed red card count
		SCORE_CHANGED, // Bet is submitted on a live soccer event with changed score
		TIME_RESTRICTION, // Bet is submitted within too short of a period from the same bet previously placed by a client
		PAST_CUTOFFTIME, // Bet is submitted on a game after the betting cutoff time
		ABOVE_EVENT_MAX, // Bet cannot be placed because client exceeded allowed maximum of risk on a line
		INVALID_ODDS_FORMAT, // If a bet was submitted with the odds format that is not allowed for the client
		LISTED_PITCHERS_SELECTION_ERROR; // If bet was submitted with pitcher1MustStart and/or pitcher2MustStart parameters in Place Bet request with values that are not allowed.
	}
	
	public enum PLACE_PARLAY_BET_RESPONSE_STATUS {
		ACCEPTED, // Accepted
		PENDING_ACCEPTANCE, // Pending Acceptance. This is for live bets in danger zone
		PROCESSED_WITH_ERROR; // Processed with error
	}
	
	public enum PLACE_PARLAY_BET_ERROR_CODE {
		ABOVE_MAX_BET_AMOUNT, // Stake is above allowed maximum amount
		ALL_BETTING_CLOSED, // Betting is not allowed at this moment
		BELOW_MIN_BET_AMOUNT, // Stake is below allowed minimum amount
		BLOCKED_BETTING, // Not allowed betting for the client
		BLOCKED_CLIENT, // Client is no longer active
		INSUFFICIENT_FUNDS, // Bet is submitted by a client with insufficient funds
		INVALID_COUNTRY, // Client country is not allowed for betting
		INVALID_LEGS, // One or more legs are invalid
		INVALID_ODDS_FORMAT, // If a bet was submitted with the odds format that is not allowed for the client
		INVALID_ROUND_ROBIN_OPTIONS, // Round robin options are invalid (i.e. does not match with number of legs)
		ROUND_ROBIN_DISALLOWED, // Round robin is disallowed for one of the leagues
		TOO_MANY_LEGS, // Maximum of 10 legs can be specified
		TOO_FEW_LEGS; // At least 2 legs are required for Parlay
	}
	
	public enum PLACE_PARLAY_LEG_RESPONSE_STATUS {
		VALID, // Valid leg
		PROCESSED_WITH_ERROR; // Processed with error
	}
	
	public enum PLACE_PARLEY_LEG_ERROR_CODE {
		CANNOT_PARLAY_LIVE_GAME, // The wager is placed on Live game
		CORRELATED, // The leg is correlated with another one
		EVENT_NOT_OFFERED_FOR_PARLAY, // The event is not offered for Parlays
		EVENT_NO_LONGER_AVAILABLE_FOR_BETTING, // The event is no longer offered for Parlays
		INVALID_EVENT, // Live betting is not allowed at this moment
		INVALID_LEG_BET_TYPE, // Leg bet type is not accepted for Parlays, Accepted values are: SPREAD, MONEYLINE, TOTAL_POINTS
		INVALID_PARLAY_BET, // The leg did not validated due to error on Parlay Bet. Check the error PlaceParlayBet response for error details
		LINE_CHANGED, // Bet is submitted on a line that has changed
		LINE_DOES_NOT_BELONG_TO_EVENT, // LineId does not match the EventId specified in the request
		LISTED_PITCHERS_SELECTION_ERROR, // If bet was submitted with pitcher1MustStart and/or pitcher2MustStart parameters with values that are not allowed
		ODDS_NO_LONGER_OFFERED_FOR_PARLAY_1, //Due to line change odds are not offered for Parlay anymore
		ODDS_NO_LONGER_OFFERED_FOR_PARLAY_2,
		ODDS_NO_LONGER_OFFERED_FOR_PARLAY_3,
		OFFLINE_EVENT, // Bet is submitted on an event that is offline
		PAST_CUTOFFTIME, //	Bet is submitted on a game after the betting cutoff time
		SYSTEM_ERROR_1,
		SYSTEM_ERROR_2,
		SYSTEM_ERROR_3;
	}
	
	public enum PARLAY_RESTRICTION {
		ALLOWED("0"), // Allowed to parlay
		NOT_AVAILABLE("1"), // Event is not available for Parlay
		ALLOWED_TO_BET_ONE_LEG("2"); // Allowed to bet only one leg on this event. Please note that this also applies on multiple events with same rotation number.
		private final String value; 
		private PARLAY_RESTRICTION (final String value) { this.value = value; }
		public String value () { return this.value; }
		public static PARLAY_RESTRICTION of (String value) {
			return Arrays.stream(PARLAY_RESTRICTION.values())
					.filter(e -> e.value().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("PARLAY_RESTRICTION", value));
		}
	}
	
	public enum GETLINE_RESPONSE_STATUS {
		SUCCESS, // OK
		NOT_EXISTS; // Line not offered anymore
	}
	
	public enum TEAM_TYPE {
		Team1, // Team 1
		Team2, // Team 2
		Draw; // Draw. This is used for MONEYLINE bet type only.
	}
	
	public enum BETLIST_TYPE {
		settled, // Settled bets
		running; // Running bets
	}

	public enum EVENT_STATUS {
		O, // This is the starting status of a game. It means that the lines are open for betting.
		I, // This status indicates that one or more lines have a red circle (a lower maximum bet amount).
		H; // This status indicates that the lines are temporarily unavailable for betting.
	}
	
	public enum LIVE_STATUS {
		NO_LIVE_BETTING("0"), // No live betting will be offered on this event.
		LIVE_BETTING("1"), // Live betting event.
		WILL_BE_OFFERED("2"); // Live betting will be offered on this event.
		private final String value; 
		private LIVE_STATUS (final String value) { this.value = value; }
		public String value () { return this.value; }
		public static LIVE_STATUS of (String value) {
			return Arrays.stream(LIVE_STATUS.values())
					.filter(e -> e.value().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("LIVE_STATUS", value));
		}
	}
	
	public enum YES_NO_TYPE {
		Yes,
		No;
	}

	@Deprecated // Uses primitive boolean or Boolean instead with functions following. 
	public enum BOOLEAN2 {
		FALSE("0"),
		TRUE("1");
		private final String value; 
		private BOOLEAN2 (final String value) { this.value = value; }
		public String value () { return this.value; }
		public static BOOLEAN2 of (String value) {
			return Arrays.stream(BOOLEAN2.values())
					.filter(e -> e.value().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("BOOLEAN2", value));
		}
	}

	static String boolean2 (boolean b) {
		return b ? "1" : "0";
	}
	
	static Boolean boolean2 (String value) {
		if (value.equals("1")) return new Boolean(true);
		if (value.equals("0")) return new Boolean(false);
		return null;
	}

	
	@Deprecated // only for 'feed', which is obsolete.
	public enum FEED_ODDS_FORMAT_TYPE {
		AMERICAN("0"),
		DECIMAL("1"),	
		HONGKONG("2"),	
		INDONESIAN("3"),	
		MALAY("4"),
		FRACTION("5");
		private final String value; 
		private FEED_ODDS_FORMAT_TYPE (final String value) { this.value = value; }
		public String value () { return this.value; }
		public static FEED_ODDS_FORMAT_TYPE of (String value) {
			return Arrays.stream(FEED_ODDS_FORMAT_TYPE.values())
					.filter(e -> e.value().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("FEED_ODDS_FORMAT_TYPE", value));
		}
	}
	
	@Deprecated // Xml checks this status in its method.
	public enum FEED_STATUS_RESPONSE {
		ok, // Feed has response
		fail; // There was an error
	}
	
	public enum INRUNNING_STATE {
		FIRST_HALF_IN_PROGRESS("1"),
		HALF_TIME_IN_PROGRESS("2"),
		SECOND_HALF_IN_PROGRESS("3"),
		END_OF_REGULAR_TIME("4"),
		EXTRA_FIRST_HALF_IN_PROGRESS("5"),
		EXTRA_HALF_TIME_IN_PROGRESS("6"),
		EXTRA_SECOND_HALF_IN_PROGRESS("7"),
		END_OF_EXTRA_TIME("8"),
		END_OF_GAME("9"),
		TEMPORARY_SUSPENDED("10"),
		PENALTIES_IN_PROGRESS("11");
		private final String value; 
		private INRUNNING_STATE (final String value) { this.value = value; }
		public String value () { return this.value; }
		public static INRUNNING_STATE of (String value) {
			return Arrays.stream(INRUNNING_STATE.values())
					.filter(e -> e.value().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("INRUNNING_STATE", value));
		}
	}
	
	public enum ROUND_ROBIN_OPTIONS {
		Parlay, // Single parlay that include all wagers (No Round Robin)
		TwoLegRoundRobin, // Multiple parlays having 2 wagers each (round robin style). Can be used if number of legs is greater or equal to 3
		ThreeLegRoundRobin, // Multiple parlays having 3 wagers each (round robin style). Can be used if number of legs is greater or equal to 3
		FourLegRoundRobin, // Multiple parlays having 4 wagers each (round robin style). Can be used if number of legs is greater or equal to 4
		FiveLegRoundRobin, // Multiple parlays having 5 wagers each (round robin style). Can be used if number of legs is greater or equal to 5
		SixLegRoundRobin, // Multiple parlays having 6 wagers each (round robin style). Can be used if number of legs is greater or equal to 6
		SevenLegRoundRobin, // Multiple parlays having 7 wagers each (round robin style). Can be used if number of legs is greater or equal to 7
		EightLegRoundRobin, // Multiple parlays having 8 wagers each (round robin style). Can be used if number of legs is  equal to 8
	}
	
	// Following three enums are not official but used in API, as of June 2015. 
	// They may support more or less items in future.

	public enum CURRENCY_CODE {
		AUD,
		CAD,
		CNY,
		CSK,
		DKK,
		EUR,
		GBP,
		HKD,
		JPY,
		KRW,
		MXP,
		MYR,
		NOK,
		NZD,
		PLZ,
		SEK,
		SGD,
		THB,
		TWD,
		USD;
	}

	public enum LANGUAGE {
		ENGLISH("en-US"),
		BRITISH("en-GB"),
		CHINESE_SI("zh-CN"),
		CHINESE_TR("zh-TW"),
		FINNISH("fi-FI"),
		GERMAN("de-DE"),
		HEBREW("he-IL"),
		ITALIAN("it-IT"),
		NORWEGIAN("nb-NO"),
		PORTUGUESE("pt-BR"),
		RUSSIAN("ru-RU"),
		SPANISH("es-ES"),
		SWEDISH("sv-SE"),
		THAI("th-TH"),
		POLISH("pl-PL"),
		FRENCH("fr-FR"),
		GREEK("el-GR"),
		JAPANESE("ja-JP"),
		KOREAN("ko-KR"),
		VIETNAMESE("vi-VN"),
		INDONESIAN("id-ID"),
		CZECH("cs-CZ"),
		NON_SUPPORTED("Non-supported-Culture");
		private final String code; 
		private LANGUAGE (final String code) { this.code = code; }
		public String code () { return this.code; }
		public static LANGUAGE of (String value) {
			return Arrays.stream(LANGUAGE.values())
					.filter(e -> e.code().equals(value))
					.findAny()
					.orElseThrow(() -> NoEnumException.of("LANGUAGE", value));
		}
	}
	
	public enum PERIOD {
		BADMINTON_MATCH("0"),
		BADMINTON_1ST_GAME("1"),
		BADMINTON_2ND_GAME("2"),
		BADMINTON_3RD_GAME("3"),
		BANDY_MATCH("0"),
		BANDY_1ST_HALF("1"),
		BANDY_2ND_HALF("2"),
		BASEBALL_GAME("0"),
		BASEBALL_1ST_HALF("1"),
		BASEBALL_2ND_HALF("2"),
		BASKETBALL_GAME("0"),
		BASKETBALL_1ST_HALF("1"),
		BASKETBALL_2ND_HALF("2"),
		BASKETBALL_1ST_QUARTER("3"),
		BASKETBALL_2ND_QUARTER("4"),
		BASKETBALL_3RD_QUARTER("5"),
		BASKETBALL_4TH_QUARTER("6"),
		BEACH_VOLLEYBALL_MATCH("0"),
		BEACH_VOLLEYBALL_1ST_SET("1"),
		BEACH_VOLLEYBALL_2ND_SET("2"),
		BEACH_VOLLEYBALL_3RD_SET("3"),
		BOXING_FIGHT("0"),
		CHESS_MATCH("0"),
		CRICKET_MATCH("0"),
		CRICKET_1ST_INNING("1"),
		CRICKET_2ND_INNING("2"),
		CURLING_GAME("0"),
		CURLING_1ST_END("1"),
		DARTS_MATCH("0"),
		DARTS_1ST_SET("1"),
		DARTS_2ND_SET("2"),
		DARTS_3RD_SET("3"),
		DARTS_4TH_SET("4"),
		DARTS_5TH_SET("5"),
		DARTS_LEGS_MATCH("0"),
		DARTS_LEGS_1ST_LEG("1"),
		DARTS_LEGS_2ND_LEG("2"),
		DARTS_LEGS_3RD_LEG("3"),
		DARTS_LEGS_4TH_LEG("4"),
		DARTS_LEGS_5TH_LEG("5"),
		DARTS_LEGS_6TH_LEG("6"),
		E_SPORTS_MATCH("0"),
		E_SPORTS_1ST_PERIOD("1"),
		FIELD_HOCKEY_MATCH("0"),
		FIELD_HOCKEY_1ST_HALF("1"),
		FIELD_HOCKEY_2ND_HALF("2"),
		FLOORBALL_MATCH("0"),
		FLOORBALL_1ST_PERIOD("1"),
		FLOORBALL_2ND_PERIOD("2"),
		FLOORBALL_3RD_PERIOD("3"),
		FOOTBALL_GAME("0"),
		FOOTBALL_1ST_HALF("1"),
		FOOTBALL_2ND_HALF("2"),
		FOOTBALL_1ST_QUARTER("3"),
		FOOTBALL_2ND_QUARTER("4"),
		FOOTBALL_3RD_QUARTER("5"),
		FOOTBALL_4TH_QUARTER("6"),
		FUTSAL_MATCH("0"),
		FUTSAL_1ST_HALF("1"),
		FUTSAL_2ND_HALF("2"),
		GOLF_MATCHUPS("0"),
		HANDBALL_MATCH("0"),
		HANDBALL_1ST_HALF("1"),
		HANDBALL_2ND_HALF("2"),
		HOCKEY_GAME("0"),
		HOCKEY_1ST_PERIOD("1"),
		HOCKEY_2ND_PERIOD("2"),
		HOCKEY_3RD_PERIOD("3"),
		HORSE_RACING_RACE("0"),
		LACROSSE_MATCH("0"),
		LACROSSE_1ST_HALF("1"),
		LACROSSE_2ST_HALF("2"),
		LACROSSE_1ST_QUARTER("3"),
		LACROSSE_2ND_QUARTER("4"),
		LACROSSE_3RD_QUARTER("5"),
		LACROSSE_4TH_QUARTER("6"),
		MIXED_MARTIAL_ARTS_FIGHT("0"),
		MIXED_MARTIAL_ARTS_ROUND_1("1"),
		MIXED_MARTIAL_ARTS_ROUND_2("2"),
		MIXED_MARTIAL_ARTS_ROUND_3("3"),
		MIXED_MARTIAL_ARTS_ROUND_4("4"),
		MIXED_MARTIAL_ARTS_ROUND_5("5"),
		OTHER_SPORTS_GAME("0"),
		POLITICS_ELECTION("0"),
		RINK_HOCKEY_MATCH("0"),
		RINK_HOCKEY_1ST_PERIOD("1"),
		RINK_HOCKEY_2ND_PERIOD("2"),
		RUGBY_LEAGUE_MATCH("0"),
		RUGBY_LEAGUE_1ST_HALF("1"),
		RUGBY_LEAGUE_2ND_HALF("2"),
		RUGBY_UNION_MATCH("0"),
		RUGBY_UNION_1ST_HALF("1"),
		RUGBY_UNION_2ND_HALF("2"),
		SNOOKER_MATCH("0"),
		SNOOKER_1ST_FRAME("1"),
		SOCCER_MATCH("0"),
		SOCCER_1ST_HALF("1"),
		SOCCER_2ND_HALF("2"),
		SOFTBALL_GAME("0"),
		SOFTBALL_1ST_HALF("1"),
		SOFTBALL_2ST_HALF("2"),
		SQUASH_MATCH("0"),
		SQUASH_1ST_GAME("1"),
		SQUASH_2ND_GAME("2"),
		SQUASH_3RD_GAME("3"),
		SQUASH_4TH_GAME("4"),
		SQUASH_5TH_GAME("5"),
		TABLE_TENNIS_MATCH("0"),
		TABLE_TENNIS_1ST_GAME("1"),
		TABLE_TENNIS_2ND_GAME("2"),
		TABLE_TENNIS_3RD_GAME("3"),
		TABLE_TENNIS_4TH_GAME("4"),
		TABLE_TENNIS_5TH_GAME("5"),
		TABLE_TENNIS_6TH_GAME("6"),
		TENNIS_MATCH("0"),
		TENNIS_1ST_SET_WINNER("1"),
		TENNIS_2ND_SET_WINNER("2"),
		TENNIS_3RD_SET_WINNER("3"),
		TENNIS_4TH_SET_WINNER("4"),
		TENNIS_5TH_SET_WINNER("5"),
		VOLLEYBALL_MATCH("0"),
		VOLLEYBALL_1ST_SET("1"),
		VOLLEYBALL_2ND_SET("2"),
		VOLLEYBALL_3RD_SET("3"),
		VOLLEYBALL_4TH_SET("4"),
		VOLLEYBALL_5TH_SET("5"),
		VOLLEYBALL_POINTS_GAME("0"),
		VOLLEYBALL_POINTS_1ST_SET("1"),
		VOLLEYBALL_POINTS_2ND_SET("2"),
		VOLLEYBALL_POINTS_3RD_SET("3"),
		VOLLEYBALL_POINTS_4TH_SET("4"),
		VOLLEYBALL_POINTS_5TH_SET("5"),
		WATER_POLO_MATCH("0"),
		WATER_POLO_1ST_PERIOD("1"),
		WATER_POLO_2ND_PERIOD("2"),
		WATER_POLO_3RD_PERIOD("3"),
		WATER_POLO_4TH_PERIOD("4"),
		PADEL_TENNIS_MATCH("0"),
		PADEL_TENNIS_1ST_SET_WINNER("1"),
		PADEL_TENNIS_2ND_SET_WINNER("2"),
		PADEL_TENNIS_3RD_SET_WINNER("3"),
		AUSSIE_RULES_FOOTBAL_GAME("0"),
		AUSSIE_RULES_FOOTBAL_1ST_HALF("1"),
		AUSSIE_RULES_FOOTBAL_2ND_HALF("2"),
		AUSSIE_RULES_FOOTBAL_1ST_QUARTER("3"),
		AUSSIE_RULES_FOOTBAL_2ND_QUARTER("4"),
		AUSSIE_RULES_FOOTBAL_3RD_QUARTER("5"),
		AUSSIE_RULES_FOOTBAL_4TH_QUARTER("6"),
		AUSSIE_RULES_GAME("0"),
		AUSSIE_RULES_1ST_HALF("1"),
		AUSSIE_RULES_2ND_HALF("2"),
		AUSSIE_RULES_1ST_QUARTER("3"),
		AUSSIE_RULES_2ND_QUARTER("4"),
		AUSSIE_RULES_3RD_QUARTER("5"),
		AUSSIE_RULES_4TH_QUARTER("6"),
		ALPINE_SKIING_MATCHUPS("0"),
		BIATHLON_MATCHUPS("0"),
		SKI_JUMPING_MATCHUPS("0"),
		CROSS_COUNTRY_MATCHUPS("0"),
		FORMULA_1_MATCHUPS("0"),
		CYCLING_MATCHUPS("0"),
		BOBSLEIGH_MATCHUPS("0"),
		FIGURE_SKATING_MATCHUPS("0"),
		FREESTYLE_SKIING_MATCHUPS("0"),
		LUGE_MATCHUPS("0"),
		NORDIC_COMBINED_MATCHUPS("0"),
		SHORT_TRACK_MATCHUPS("0"),
		SKELETON_MATCHUPS("0"),
		SNOW_BOARDING_MATCHUPS("0"),
		SPEED_SKATING_MATCHUPS("0");
		private final String value; 
		private PERIOD (final String value) { this.value = value; }
		public String value () { return this.value; }
	}
	
}

