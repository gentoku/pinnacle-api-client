package pinnacle.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import pinnacle.api.enums.BETLIST_TYPE;
import pinnacle.api.enums.BET_TYPE;
import pinnacle.api.enums.CULTURE_CODE;
import pinnacle.api.enums.LEG_BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.PERIOD;
import pinnacle.api.enums.ROUND_ROBIN_OPTIONS;
import pinnacle.api.enums.SIDE_TYPE;
import pinnacle.api.enums.TEAM_TYPE;
import pinnacle.api.enums.WIN_RISK_TYPE;

public class Parameter extends ParameterCore {

	/**
	 * Constructor.
	 */
	private Parameter () {
		super();
	}
	
	/**
	 * Factory
	 * 
	 * @return
	 */
	public static Parameter newInstance() {
		return new Parameter();
	}


	/**
	 * ID of the target sports.
	 * 
	 * @param id
	 */
	public void sportId(int id) {
		this.parameters.put("sportId", id);
	}

	/**
	 * The leagueIds array may contain a list of comma separated league ids.
	 * 
	 * @param ids
	 * @return
	 */
	public void leagueIds(long... ids) {
		String leagueIds = LongStream.of(ids).mapToObj(Long::toString).collect(Collectors.joining(","));
		this.parameters.put("leagueIds", leagueIds);
	}

	/**
	 * This accepts a single leagueId.
	 * 
	 * @param ids
	 * @return
	 */
	public void leagueId(long id) {
		this.parameters.put("leagueId", id);
	}

	/**
	 * This is used to receive incremental updates. Use the value of last from
	 * previous fixtures response. When since parameter is not provided, the
	 * fixtures are delayed up to 1 min to encourage the use of the parameter.
	 * 
	 * Please note that when using since parameter to get odds you will get in
	 * the response ONLY changed periods. If a period didn’t have any changes it
	 * will not be in the response.
	 * 
	 * @param since
	 * @return
	 */
	public void since(long since) {
		this.parameters.put("since", since);
		this.withSince = true;
	}

	/**
	 * To retrieve ONLY live games set the value to islive=1. If sportid is
	 * provided along with the request, the result will be related to a single
	 * sport. islive=0 cannot be used without the sportid parameter. This
	 * ensures that the speed of the feed is not compromised. Sets isLive. This
	 * is defined as Enum but just a simple boolean value.
	 */
	public void isLive(boolean isLive) {
		String boolean2 = isLive ? "1" : "0";
		this.parameters.put("isLive", boolean2);
	}

	/**
	 * Category of the special.
	 * 
	 * @param category
	 * @throws PinnacleException
	 */
	public void category(String category) throws PinnacleException {
		if (category == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		category = this.urlEncode(category);
		this.parameters.put("category", category);
	}

	/**
	 * Event id of the linked event
	 * 
	 * @param eventId
	 */
	public void eventId(long eventId) {
		this.parameters.put("eventId", eventId);
	}

	/**
	 * Specific special Id
	 * 
	 * @param specialId
	 */
	public void specialId(long specialId) {
		this.parameters.put("specialId", specialId);
	}

	/**
	 * Format the odds are returned in.
	 * 
	 * @param oddsFormat
	 * @throws PinnacleException 
	 */
	public void oddsFormat(ODDS_FORMAT oddsFormat) throws PinnacleException {
		if (oddsFormat == null || oddsFormat == ODDS_FORMAT.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("oddsFormat", oddsFormat.toAPI());
	}

	/**
	 * This represents the period of the match. You can check the numbers by Get
	 * Periods operation.
	 * 
	 * @param periodNumber
	 */
	public void periodNumber(int periodNumber) {
		this.parameters.put("periodNumber", Integer.valueOf(periodNumber));
	}

	/**
	 * This represents the period of the match. Note that they may change the
	 * definitions.
	 * 
	 * @param period
	 * @throws PinnacleException 
	 */
	public void periodNumber(PERIOD period) throws PinnacleException {
		if (period == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("periodNumber", period.toAPI());
	}

	/**
	 * A bet type of four types.
	 * 
	 * @param type
	 * @throws PinnacleException 
	 */
	public void betType(BET_TYPE type) throws PinnacleException {
		if (type == null || type == BET_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("betType", type.toAPI());
	}

	/**
	 * Chosen team type. This is needed only for SPREAD, MONEYLINE and
	 * TEAM_TOTAL_POINTS bet types.
	 * 
	 * @param type
	 * @throws PinnacleException 
	 */
	public void team(TEAM_TYPE type) throws PinnacleException {
		if (type == null || type == TEAM_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("team", type.toAPI());
	}

	/**
	 * Chosen side. This is needed only for TOTAL_POINTS and TEAM_TOTAL_POINTS
	 * bet type.
	 * 
	 * @param type
	 * @throws PinnacleException 
	 */
	public void side(SIDE_TYPE type) throws PinnacleException {
		if (type == null || type == SIDE_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("side", type.toAPI());
	}

	/**
	 * Handicap value. This is needed for SPREAD, TOTAL_POINTS and
	 * TEAM_TOTAL_POINTS bet type.
	 * 
	 * @param handicap
	 * @throws PinnacleException 
	 */
	public void handicap(String handicap) throws PinnacleException {
		if (handicap == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		BigDecimal decimal = new BigDecimal(handicap); // to validate
		this.parameters.put("handicap", decimal);
	}

	/**
	 * Handicap value. This is needed for SPREAD, TOTAL_POINTS and
	 * TEAM_TOTAL_POINTS bet type.
	 * 
	 * @param handicap
	 * @throws PinnacleException 
	 */
	public void handicap(BigDecimal handicap) throws PinnacleException {
		if (handicap == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("handicap", handicap);
	}

	/**
	 * Collection of legs.
	 * 
	 * @param legs
	 * @throws PinnacleException 
	 */
	public void legs(Parameter... legs) throws PinnacleException {
		if (this.hasNull((Object[]) legs)) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.legs = Arrays.asList(legs);
	}

	/**
	 * Bet type for leg. Note that pinnacle don't use this name for legs in
	 * getTeaserLines operation, which they implemented later.
	 * 
	 * @param legBetType
	 * @throws PinnacleException 
	 */
	public void legBetType(LEG_BET_TYPE type) throws PinnacleException {
		if (type == null || type == LEG_BET_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("legBetType", type.toAPI());
	}

	/**
	 * This unique id of the leg. It used to identify and match leg in the
	 * response.
	 * 
	 * @return random generated UUID
	 */
	public String uniqueLegId() {
		String uuid = UUID.randomUUID().toString();
		this.parameters.put("uniqueLegId", uuid);
		return uuid;
	}

	/**
	 * Client generated GUID for uniquely identifying the leg.
	 * 
	 * @return
	 */
	public String legId() {
		String uuid = UUID.randomUUID().toString();
		this.parameters.put("legId", uuid);
		return uuid;
	}

	/**
	 * Id of the contestant.
	 * 
	 * @param contestantId
	 */
	public void contestantId(long contestantId) {
		this.parameters.put("contestantId", contestantId);
	}

	/**
	 * This unique id of the place bet requests. This is to support idempotent
	 * requests.
	 */
	public void uniqueRequestId() {
		this.parameters.put("uniqueRequestId", UUID.randomUUID().toString());
	}

	/**
	 * Whether or not to accept a bet when there is a line change in favor of
	 * the client.
	 * 
	 * @param acceptBetterLine
	 */
	public void acceptBetterLine(boolean acceptBetterLine) {
		this.parameters.put("acceptBetterLine", acceptBetterLine);
	}

	/**
	 * Reference to a customer in third party system.
	 * 
	 * @param customerReference
	 * @throws PinnacleException
	 */
	public void customerReference(String customerReference) throws PinnacleException {
		if (customerReference == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		customerReference = this.urlEncode(customerReference);
		this.parameters.put("customerReference", customerReference);
	}

	/**
	 * Wagered amount in Client’s currency.
	 * 
	 * @param stake
	 *            as String
	 * @throws PinnacleException 
	 */
	public void stake(String stake) throws PinnacleException {
		if (stake == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		BigDecimal decimal = new BigDecimal(stake); // to validate
		this.parameters.put("stake", decimal);
	}

	/**
	 * Alternate method of stake.
	 * 
	 * @param stake
	 *            as BigDecimal
	 * @throws PinnacleException 
	 */
	public void stake(BigDecimal stake) throws PinnacleException {
		if (stake == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("stake", stake);
	}

	/**
	 * Whether the stake amount is risk or win amount.
	 * 
	 * @param type
	 * @throws PinnacleException 
	 */
	public void winRiskStake(WIN_RISK_TYPE type) throws PinnacleException {
		if (type == null || type == WIN_RISK_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("winRiskStake", type.toAPI());
	}

	/**
	 * Sets lineId as parameter.
	 * 
	 * @param lineId
	 *            - Line identification.
	 */
	public void lineId(long lineId) {
		this.parameters.put("lineId", lineId);
	}

	/**
	 * Sets altLineId as parameter.
	 * 
	 * @param altLineId
	 *            - Alternate line identification.
	 */
	public void altLineId(long altLineId) {
		this.parameters.put("altLineId", altLineId);
	}

	/**
	 * Sets pitcher1MustStart as parameter.
	 * 
	 * @param pitcher1MustStart
	 *            - Baseball only. Refers to the pitcher for TEAM_TYPE.Team1.
	 *            This applicable only for MONEYLINE bet type, for all other bet
	 *            types this has to be TRUE
	 */
	public void pitcher1MustStart(boolean pitcher1MustStart) {
		this.parameters.put("pitcher1MustStart", pitcher1MustStart);
	}

	/**
	 * Sets pitcher2MustStart as parameter.
	 * 
	 * @param pitcher2MustStart
	 *            - Baseball only. Refers to the pitcher for TEAM_TYPE. Team2.
	 *            This applicable only for MONEYLINE bet type, for all other bet
	 *            types this has to be TRUE
	 */
	public void pitcher2MustStart(boolean pitcher2MustStart) {
		this.parameters.put("pitcher2MustStart", pitcher2MustStart);
	}

	/**
	 * Wagered amount in Client’s currency. It is always risk amount when
	 * placing Parlay bets. NOTE: If round robin options is used this amount
	 * will apply for all parlays so actual amount wagered will be riskAmount X
	 * number of Parlays
	 * 
	 * @param riskAmount
	 * @throws PinnacleException 
	 */
	public void riskAmount(String riskAmount) throws PinnacleException {
		if (riskAmount == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("riskAmount", new BigDecimal(riskAmount));
	}

	/**
	 * Alternate method of riskAmount.
	 * 
	 * @param riskAmount
	 * @throws PinnacleException 
	 */
	public void riskAmount(BigDecimal riskAmount) throws PinnacleException {
		if (riskAmount == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("riskAmount", riskAmount);
	}

	/**
	 * ARRAY of ROUND_ROBIN_OPTIONS
	 * 
	 * @param roundRobinOptions
	 * @throws PinnacleException 
	 */
	public void roundRobinOptions(ROUND_ROBIN_OPTIONS... roundRobinOptions) throws PinnacleException {
		if (this.hasNull((Object[]) roundRobinOptions)) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		}
		this.parameters.put("roundRobinOptions", 
				Stream.of(roundRobinOptions)
					.filter(rr -> rr != ROUND_ROBIN_OPTIONS.UNDEFINED)
					.map(ROUND_ROBIN_OPTIONS::toAPI)
					.collect(Collectors.toList()));
	}

	/**
	 * Unique identifier. Teaser details can be retrieved from a call to Get
	 * Teaser Groups endpoint.
	 * 
	 * @param teaserId
	 */
	public void teaserId(long teaserId) {
		this.parameters.put("teaserId", teaserId);
	}

	/**
	 * A list of special bets
	 * @throws PinnacleException 
	 */
	public void bets(Parameter... bets) throws PinnacleException {
		if (this.hasNull((Object[]) bets)) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.bets = Arrays.asList(bets);
	}

	/**
	 * Not needed when betids is submitted.
	 * 
	 * @param betlist
	 * @throws PinnacleException 
	 */
	public void betlist(BETLIST_TYPE betlist) throws PinnacleException {
		if (betlist == null || betlist == BETLIST_TYPE.UNDEFINED) {
			throw PinnacleException.parameterInvalid("Parameter cannot accept null or UNDEFINED.");
		}
		this.parameters.put("betlist", betlist.toAPI());
	}

	/**
	 * Array of bet ids. When betids is submitted, no other parameter is
	 * necessary. Maximum is 100 ids. Works for all non settled bets and all
	 * bets settled in the last 30 days
	 * 
	 * @param ids
	 * @throws PinnacleException
	 */
	public void betids(long... ids) throws PinnacleException {
		if (ids.length > 100) {
			throw PinnacleException.parameterInvalid("Number of 'betids' exceeds 100 as its maximum.");
		}
		String betIds = LongStream.of(ids).mapToObj(Long::toString).collect(Collectors.joining(","));
		this.parameters.put("betids", betIds);
	}

	/**
	 * Start date of the requested period. Required when betlist parameter is
	 * submitted. Difference between fromDate and toDdate can’t be more than 30
	 * days. Expected format is ISO8601 - can be set to just date or date and
	 * time.
	 * @throws PinnacleException 
	 */
	public void fromDate(LocalDateTime fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("fromDate", fromDate.toString() + "Z"); // Z means
																	// UTC
	}

	/**
	 * Alternate method. Time will be set as 00:00:00.
	 * 
	 * @param fromDate
	 * @throws PinnacleException 
	 */
	public void fromDate(LocalDate fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		LocalTime fromTime = LocalTime.of(0, 0, 0);
		this.fromDate(LocalDateTime.of(fromDate, fromTime));
	}

	/**
	 * Alternate method. ZonedDateTime.toString returns like
	 * "2016-12-02T23:59:59Z[UTC]" but Pinnacle API doesn't accept last square
	 * brackets so needs to convert to LocalDateTime.
	 * 
	 * @param fromDate
	 * @throws PinnacleException 
	 */
	public void fromDate(ZonedDateTime fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		ZonedDateTime utc = fromDate.withZoneSameInstant(Constants.TIME_ZONE);
		this.fromDate(utc.toLocalDateTime());
	}

	/**
	 * End date of the requested period Required when betlist parameter is
	 * submitted. Expected format is ISO8601 - can be set to just date or date
	 * and time. toDate value is exclusive, meaning it cannot be equal to
	 * fromDate
	 * @throws PinnacleException 
	 */
	public void toDate(LocalDateTime fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		this.parameters.put("toDate", fromDate.toString() + "Z"); // Z means UTC
	}

	/**
	 * Alternate method. Time will be set as 23:59:59.
	 * 
	 * @param fromDate
	 * @throws PinnacleException 
	 */
	public void toDate(LocalDate fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		LocalTime fromTime = LocalTime.of(23, 59, 59);
		this.toDate(LocalDateTime.of(fromDate, fromTime));
	}

	/**
	 * Alternate method. ZonedDateTime.toString returns like
	 * "2016-12-02T23:59:59Z[UTC]" but Pinnacle API doesn't accept last square
	 * brackets so needs to convert to LocalDateTime.
	 * 
	 * @param fromDate
	 * @throws PinnacleException 
	 */
	public void toDate(ZonedDateTime fromDate) throws PinnacleException {
		if (fromDate == null) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		ZonedDateTime utc = fromDate.withZoneSameInstant(Constants.TIME_ZONE);
		this.toDate(utc.toLocalDateTime());
	}

	public void cultureCodes(CULTURE_CODE... codes) throws PinnacleException {
		if (this.hasNull((Object[]) codes)) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		String combined = Stream.of(codes).map(CULTURE_CODE::toAPI).collect(Collectors.joining("|"));
		this.parameters.put("cultureCodes", combined);
	}

	public void baseTexts(String... baseTexts) throws PinnacleException {
		if (this.hasNull((Object[]) baseTexts)) throw PinnacleException.parameterInvalid("Parameter cannot accept null.");
		List<String> encodedTexts = new ArrayList<>();
		for (String text : baseTexts) {
			String encoded = this.urlEncode(text);
			encodedTexts.add(encoded);
		}
		String combined = encodedTexts.stream().collect(Collectors.joining("|"));
		this.parameters.put("baseTexts", combined);
	}
	
}
