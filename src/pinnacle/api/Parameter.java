package pinnacle.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.Gson;

import pinnacle.api.Enums.*;

public class Parameter {
	
	private Map<String, Object> parameters;
	
	/**
	 * The parameter has 'last' to determine throttling rate for fair use. 
	 */
	@Deprecated // only for 'feed' request, which is obsolete.
	private boolean hasLast = false;
	@Deprecated // only for 'feed' request, which is obsolete.
	boolean hasLast () {
		return this.hasLast;
	}
	
	/**
	 * legs will be set as array of parameter object.
	 */
	private List<Parameter> legs;
	
	/**
	 * Returns legs. This should be used when the parameter must have 'legs'
	 * so throws exception when legs is null. 
	 * @return
	 */
	List<Parameter> legs () {
		if (this.legs == null) throw new IllegalArgumentException("legs must be set in parameters.");
		return this.legs;
	}
	
	/**
	 * Constructor and Factory.
	 */
	private Parameter () {
		this.parameters = new ConcurrentHashMap<>(); // to lower-case key names by stream
		this.legs = new ArrayList<>();
	}
	public static Parameter newInstance () {
		return new Parameter();
	}
	
	/**
	 * Unites key and value with '=' and joins them with '&' for GET method request.
	 * Not support nested parameters like legs.
	 * @return
	 */
	String get () {
		return this.parameters.entrySet()
				.parallelStream()
				.map(p -> p.getKey() + "=" + p.getValue().toString())
				.collect(Collectors.joining("&"));
	}
	
	/**
	 * Converts parameters to Json. This is currently just for POST method request
	 * related with Parlay Bet.   
	 * @return
	 */
	String json () {
		if (this.legs.size() > 0) {
			List<Map<String, Object>> list = this.legs.parallelStream()
												.map(leg -> leg.parameters)
												.collect(Collectors.toList());
			this.parameters.put("legs", list);
		}
		Gson gson = new Gson();
		return gson.toJson(this.parameters);
	}
	
	/**
	 * Changes camel case of keys into lower case.
	 * 
	 * Pinnaclesports.com defines every name of parameters as 'camelCase' by default,
	 * but 'Get League' operation uses not 'sportId' but 'sportid' in request URL.
	 * This is for such a awkward case.  
	 */
	void toLowerCase () {
		this.parameters.keySet().forEach(this::renameKey);
	}
	
	private void renameKey (String key) {
		Object value = this.parameters.remove(key);
		this.parameters.put(key.toLowerCase(), value);
	}
	
	/**
	 * Returns <code>Validator</code> to validate and verify.  
	 * @return
	 */
	Validator getValidator () {
		return new Validator(this.parameters);
	}
	
	/**
	 * (Integer)
	 * The id of target sport. 
	 * @param id
	 */
	public void sportId (int id) {
		this.parameters.put("sportId", Integer.valueOf(id));
	}
	
	/**
	 * (Integer)
	 * The league id that belongs to the same sport Id. If sportid/leagueid
	 * is an invalid pair, no results will be returned. 
	 * @param id
	 */
	public void leagueId (int id) {
		this.parameters.put("leagueId", Integer.toString(id));
	}

	/**
	 * (String) 
	 * You can also pass multiple leagues separated by a dash "-" for 'Get Feed' request. 
	 * @param ids
	 * @return
	 */
	@Deprecated // only for 'feed' which is obsolete. (Actually valid for get fixtures but leagueIds should be used.)
	public void leagueId (int... ids) {
		String leagueIds = IntStream.of(ids)
							.mapToObj(Integer::toString)
							.collect(Collectors.joining("-"));
		this.parameters.put("leagueId", leagueIds);
		this.parameters.put("multipleLeagueId", "here"); // no mean for 'here', just not to be null.
	}
	
	/**
	 * (Array/Comma separated String) 
	 * The leagueIds array may contain a list of comma separated league ids.
	 * @param ids
	 * @return
	 */
	public void leagueIds (int... ids) {
		String leagueIds = IntStream.of(ids)
							.mapToObj(Integer::toString)
							.collect(Collectors.joining(","));
		this.parameters.put("leagueIds", leagueIds);
	}
	
	/**
	 * (Integer)
	 * The id of the event.
	 * @param id
	 */
	public void eventId (int id) {
		this.parameters.put("eventId", Integer.valueOf(id));
	}

	/**
	 * (Long)
	 * This is used to receive incremental updates. Use the value of last 
	 * from previous fixtures response. When since parameter is not provided, 
	 * the fixtures are delayed up to 1 min to encourage the use of the parameter.
	 * 
	 * Please note that when using since parameter to get odds you will get 
	 * in the response ONLY changed periods. If a period didn’t have any changes 
	 * it will not be in the response.
	 * @param since
	 * @return
	 */
	public void since (long since) {
		this.parameters.put("since", Long.valueOf(since));
	}
	
	/**
	 * (Enum)
	 * Default is American. If otherwise specified, returns odds in different format.
	 * @param type FEED_ODDS_FORMAT_TYPE
	 */
	@Deprecated
	public void oddsFormat (FEED_ODDS_FORMAT_TYPE type) {
		this.parameters.put("oddsFormat", type.value());
	}
	
	/**
	 * (Enum)
	 * Bet is processed with this odds format.
	 * @param type
	 */
	public void oddsFormat (ODDS_FORMAT format) {
		this.parameters.put("oddsFormat", format.name());
	}
	
	/**
	 * (Long)
	 * This is used to receive incremental updates. Use the value of feedTime 
	 * from previous feed response. When last parameter is not provided, 
	 * the response does not return the freshest lines. Please always use 
	 * last parameter to get the freshest line changes. 
	 * @param timestamp
	 */
	@Deprecated
	public void last (long timestamp) {
		this.parameters.put("last", Long.valueOf(timestamp));
		this.hasLast = true;
	}
	
	/**
	 * (BOOLEAN2)
	 * To retrieve ONLY live games set the value to islive=1. If sportid is 
	 * provided along with the request, the result will be related to 
	 * a single sport. islive=0 cannot be used without the sportid parameter. 
	 * This ensures that the speed of the feed is not compromised. Sets isLive. 
	 * This is defined as Enum but just a simple boolean value.   
	 */
	public void isLive (boolean isLive) {
		this.parameters.put("isLive", Enums.boolean2(isLive));
	}
	
	/**
	 * (Enum)
	 * To convert amounts (such as maximum bet amount) to another currency. 
	 * If omitted, the default is United States Dollar (USD). 
	 * The currency code should be one from the Get Currencies operation response.
	 * @param currency
	 * @throws IOException 
	 */
	public void currencyCode (CURRENCY_CODE currencyCode) {
		this.parameters.put("currencyCode", currencyCode.name());
	}
	
	/**
	 * (String)
	 * This unique id of the place bet requests. This is to support idempotent requests.
	 * @param uniqueRequestId
	 */
	public String uniqueRequestId () {
		String uniqueId = UUID.randomUUID().toString();
		this.parameters.put("uniqueRequestId", uniqueId);
		return uniqueId;
	}
	
	/**
	 * (BOOLEAN1)
	 * Whether or not to accept a bet when there is a line change in favor of the client.
	 * @param acceptBetterLine
	 */
	public void acceptBetterLine (boolean acceptBetterLine) {
		this.parameters.put("acceptBetterLine", Enums.boolean1(acceptBetterLine));
	}
	
	/**
	 * (String)
	 * Reference to a customer in third party system.
	 * @param customerReference
	 */
	public void customerReference (String customerReference) {
		if (customerReference != null) {
			this.parameters.put("customerReference", customerReference);
		}
	}
	
	/**
	 * (Decimal)
	 * Wagered amount in Client’s currency.
	 * @param amount
	 */
	public void stake (String stake) {
		BigDecimal decimal = new BigDecimal(stake); // to validate
		this.parameters.put("stake", decimal);
	}
	
	/**
	 * (Enum)
	 * Whether the stake amount is risk or win amount.
	 * @param type
	 */
	public void winRiskStake (WIN_RISK_TYPE type) {
		this.parameters.put("winRiskStake", type.name());
	}
	
	/**
	 * (Integer)
	 * This represents the period of the match.
	 * @param periodNumber
	 */
	public void periodNumber (int periodNumber) {
		this.parameters.put("periodNumber", Integer.valueOf(periodNumber));
	}

	/**
	 * (Enum)
	 * This represents the period of the match.
	 * @param periodNumber
	 */
	public void periodNumber (PERIOD periodNumber) {
		this.parameters.put("periodNumber", periodNumber.value());
	}

	
	/**
	 * (Enum)
	 * The type of bet.
	 * @param type
	 */
	public void betType (BET_TYPE type) {
		this.parameters.put("betType", type);
	}
	
	/**
	 * (Enum)
	 * Only SPREAD, MONEYLINE and TOTAL_POINTS are supported.
	 * @param legBetType
	 * @return
	 */
	public void legBetType (BET_TYPE legBetType) {
		this.parameters.put("legBetType", legBetType);
	}
	
	/**
	 * (Enum)
	 * Chosen team type. 
	 * This is needed only for SPREAD, MONEYLINE and TEAM_TOTAL_POINTS bet types.
	 * @param type
	 */
	public void team (TEAM_TYPE type) {
		this.parameters.put("team", type.name());
	}
	
	/**
	 * (Enum)
	 * Chosen side. 
	 * This is needed only for TOTAL_POINTS and TEAM_TOTAL_POINTS bet type.
	 * @param type
	 */
	public void side (SIDE_TYPE type) {
		this.parameters.put("side", type.name());
	}
	
	/**
	 * (Integer)
	 * Line identification.
	 * @param id
	 */
	public void lineId (int id) {
		this.parameters.put("lineId", Integer.valueOf(id));
	}
	
	/**
	 * (Integer)
	 * Alternate line identification.
	 * @param id
	 */
	public void altLineId (int id) {
		this.parameters.put("altLineId", Integer.valueOf(id));
	}
	
	/**
	 * (BOOLEAN1)
	 * Baseball only. Refers to the pitcher for TEAM_TYPE.Team1. 
	 * This applicable only for MONEYLINE bet type, for all other bet types this has to be TRUE.
	 * @param pitcher1MustStart
	 */
	public void pitcher1MustStart (boolean pitcher1MustStart) {
		this.parameters.put("pitcher1MustStart", Enums.boolean1(pitcher1MustStart));
	}
	
	/**
	 * (BOOLEAN1)
	 * Baseball only. Refers to the pitcher for TEAM_TYPE. Team2. 
	 * This applicable only for MONEYLINE bet type, for all other bet types this has to be TRUE.
	 * @param pitcher2MustStart
	 */
	public void pitcher2MustStart (boolean pitcher2MustStart) {
		this.parameters.put("pitcher2MustStart", Enums.boolean1(pitcher2MustStart));
	}
	
	/**
	 * (Decimal)
	 * Wagered amount in Client’s currency. It is always risk amount when placing Parlay bets.
	 * NOTE: If round robin options is used this amount will apply for all parlays 
	 * so actual amount wagered will be riskAmount X number of Parlays
	 * @param amount
	 * @return
	 */
	public void riskAmount (String amount) {
		BigDecimal decimal = new BigDecimal(amount); // to validate
		this.parameters.put("riskAmount", decimal);
	}
	
	/**
	 * (Array of Enum)
	 * ARRAY of ROUND_ROBIN_OPTIONS. POST JSON request only.
	 * @param roundRobinOptions
	 * @return
	 */
	public void roundRobinOptions (ROUND_ROBIN_OPTIONS... roundRobinOptions) {
		this.parameters.put("roundRobinOptions", Arrays.asList(roundRobinOptions));
	}
	
	/**
	 * (Array of Object)
	 * Array of parlay legs. POST JSON request only.
	 * @param legs
	 * @return
	 */
	public void legs (Parameter... legs) {
		this.legs = Arrays.asList(legs);
	}
	
	/**
	 * (String)
	 * This unique id of the leg. It used to identify and match leg in the response.
	 * @param uniqueLegId
	 */
	public String uniqueLegId () {
		String uniqueId = UUID.randomUUID().toString();
		this.parameters.put("uniqueLegId", uniqueId);
		return uniqueId;
	}
	
	/**
	 * (Decimal)
	 * This is needed for SPREAD, TOTAL_POINTS and TEAM_TOTAL_POINTS bet type.
	 * @param handicap
	 */
	public void handicap (String handicap) {
		BigDecimal decimal = new BigDecimal(handicap); // to validate
		this.parameters.put("handicap", decimal);
	}
	
	/**
	 * (Enum)
	 * Not needed when betids is submitted.
	 * @param type
	 */
	public void betlist (BETLIST_TYPE type) {
		this.parameters.put("betlist", type.name());
	}
	
	/**
	 * (Array of Integer) 
	 * Array of bet ids. When betids is submitted, no other parameter is necessary.
	 * Maximum is 100 ids. 
	 * Works for all non settled bets and all bets settled in the last 30 days.
	 * 
	 * PinnacleSports.com requires this parameter as 'Int[]', but this function
	 * deal it as a comma separated string because this is currently used 
	 * for GET method, no use for POST method with parameters converted as JSON. 
	 *  e.g. https://api.pinnaclesports.com/v1/bets?betids=299633842,299629993
	 * @param ids
	 */
	public void betIds (int... ids) {
		String betIds = IntStream.of(ids)
						.mapToObj(Integer::toString)
						.collect(Collectors.joining(","));
		this.parameters.put("betids", betIds);
	}
	
	/**
	 * (DateTime)
	 * Start date of the requested period. Required when betlist parameter is submitted.
	 * Difference between fromDate and toDdate can’t be more than 30 days.
	 * @param fromDate
	 */
	public void fromDate (LocalDate fromDate) {
		this.parameters.put("fromDate", fromDate); // YYYY-MM-DD
	}

	/**
	 * (DateTime)
	 * End date of the requested period Required when betlist parameter is submitted.
	 * @param toDate
	 */
	public void toDate (LocalDate toDate) {
		this.parameters.put("toDate", toDate); // YYYY-MM-DD
	}
	
	/**
	 * (String)
	 * Must be in "Language Culture Name" format.
	 * @param languages
	 * @return
	 */
	public void cultureCodes (LANGUAGE... languages) {
		String codes = Stream.of(languages)
				.map(LANGUAGE::code)
				.collect(Collectors.joining("|"));
		this.parameters.put("cultureCodes", codes);
	}

	/**
	 * (String)
	 * Array of strings to be translated.
	 * @param baseTexts
	 * @return
	 */
	public void baseTexts (String... baseTexts) {
		String combined = Stream.of(baseTexts)
				.collect(Collectors.joining("|"));
		this.parameters.put("baseTexts", combined);
	}
}
