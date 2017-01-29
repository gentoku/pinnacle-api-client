package pinnacle.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import pinnacle.api.Connection.Request;
import pinnacle.api.Connection.Response;

/**
 * This is the main class for using Pinnacle Sports API. Create an instance
 * with username and password, then invoke an arbitrary method to get a response.
 * @author gentoku@gmail.com
 *
 */
public class PinnacleAPI {
	
	static final String BASE_URL = "https://api.pinnaclesports.com/v1";

	/**
	 * Time zone pinnacle sports uses. 
	 * All dates and times are in UTC time zone, ISO 8601 format.
	 * (Some responses are without last 'Z' ISO 8601 defined.)
	 */
	public static final String TIME_ZONE ="UTC";

	static final String CHARSET = "UTF-8";

	/**
	 * Requests for the 'feed' are limited explicitly by PinnacleSports.com. 
	 * See http://www.pinnaclesports.com/en/api/manual#fair-use
	 * 	Requests made for the "feed" command without the "last" parameter 
	 * 		must be restricted to once every 60 seconds
	 * 	Requests made for the "feed" command with the "last" 
	 * 		must be restricted to once every 5 seconds
	 */
	@Deprecated // only for 'feed' request, which is obsolete.
	static final int THROTTLE_WITHOUT_LAST	= 60000;
	@Deprecated // only for 'feed' request, which is obsolete.
	static final int THROTTLE_WITH_LAST		= 5000;

	/**
	 * Voluntary throttling rate or waiting time per a request.
	 */
	static final int THROTTLE = 1000;
	
	/**
	 * This will be done when opening connection to API. Define as you like.
	 * @param message
	 */
	static void access (String message) {
		//debug.Logger.access(message);
	}
	
	private String credentials;
	
	/**
	 * Constructor with username and password.
	 * @param username
	 * @param password
	 * @throws UnsupportedEncodingException
	 */
	public PinnacleAPI (String username, String password) throws UnsupportedEncodingException {
		this.credentials = this.encode(username, password);
	}
	
	/**
	 * Encodes username and password.
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encode (String username, String password) throws UnsupportedEncodingException {
		String id = username + ":" + password;
        byte[] bytes = id.getBytes(CHARSET);
        return Base64.getEncoder().encodeToString(bytes);
	}
	
	/**
	 * Constructor with encoded ID.
	 * @param encodedId
	 */
	public PinnacleAPI (String encodedId) {
		this.credentials = encodedId;
	}
	
	/**
	 * Executes the request and gets response and wraps IOException.
	 * @param request
	 * @return
	 * @throws PinnacleException
	 */
	private Response execute (Request request) throws PinnacleException {
		try {
			return Connection.newInstance(this.credentials).execute(request);
		} catch (IOException e) {
			throw new PinnacleException(e.getMessage());
		}
	}
	
	/**
	 * Get Sports
	 * 	http://www.pinnaclesports.com/en/api/manual#Gsports
	 * @return AsXml
	 * @throws PinnacleException
	 */
	public String getSportsAsXml () throws PinnacleException {  
		Request request = Requesters.getSports().xml();
		return this.execute(request).text();
	}
	
	public List<Sport> getSportsAsList () throws PinnacleException {
		return Sport.parse(getSportsAsXml());
	}
	
	/**
	 * Get Leagues
	 *	http://www.pinnaclesports.com/en/api/manual#Gleagues
	 * @param parameter
	 * @return AsXml
	 * @throws PinnacleException
	 */
	public String getLeaguesAsXml (Parameter parameter) throws PinnacleException {
		Request request = Requesters.getLeagues(parameter).xml();
		return this.execute(request).text();
	}
	
	public List<League> getLeaguesAsList (Parameter parameter) throws PinnacleException {
		return League.parse(getLeaguesAsXml(parameter));
	}
	
	/**
	 * Get Feed
	 * 	http://www.pinnaclesports.com/en/api/manual#Gfeed
	 * 
	 * This operation is obsolete, please check section Migration 
	 * from Get Feed to Get Odds and Get Fixtures operations.
	 * 
	 * @param parameter
	 * @return AsXml
	 * @throws PinnacleException
	 */
	@Deprecated
	public String getFeedAsXml (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getFeed(parameter).xml();
		return this.execute(request).text();
	}
	
	/**
	 * Get Fixtures
	 * 	http://www.pinnaclesports.com/en/api/manual#GetFixtures
	 * @param parameter
	 * @return AsJson
	 * @throws PinnacleException
	 */
	public String getFixturesAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getFixtures(parameter).json();
		return this.execute(request).text();
	}
	
	public Fixtures getFixtures (Parameter parameter) throws PinnacleException {
		return Fixtures.parse(getFixturesAsJson(parameter));
	}
	
	public List<Fixture> getFixturesAsList (Parameter parameter) throws PinnacleException {
		return Fixture.toList(getFixtures(parameter));
	}
	
	/**
	 * Get Odds
	 * 	http://www.pinnaclesports.com/en/api/manual#GetOdds
	 * @param parameter
	 * @return AsJson
	 * @throws PinnacleException
	 */
	public String getOddsAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getOdds(parameter).json();
		return this.execute(request).text();
	}
	
	public Odds getOdds (Parameter parameter) throws PinnacleException { 
		return Odds.parse(getOddsAsJson(parameter));
	}

	/**
	 * Get Currencies
	 *  http://www.pinnaclesports.com/en/api/manual#Gcurr
	 * @return AsXml
	 * @throws PinnacleException
	 */
	public String getCurrenciesAsXml () throws PinnacleException { 
		Request request = Requesters.getCurrencies().xml();
		return this.execute(request).text();
	}

	public List<Currency> getCurrenciesAsList () throws PinnacleException { 
		return Currency.parse(getCurrenciesAsXml());
	}
	
	/**
	 * Get Client Balance
	 * 	http://www.pinnaclesports.com/en/api/manual#GCbal
	 * @return AsXml or AsJson
	 * @throws PinnacleException
	 */
	public String getClientBalanceAsJson () throws PinnacleException { 
		Request request = Requesters.getClientBalance().json();
		return this.execute(request).text();
	}

	public String getClientBalanceAsXml () throws PinnacleException { 
		Request request = Requesters.getClientBalance().xml();
		return this.execute(request).text();
	}

	public ClientBalance getClientBalance () throws PinnacleException { 
		return ClientBalance.parse(getClientBalanceAsJson());
	}

	/**
	 * Place Bet
	 * 	http://www.pinnaclesports.com/en/api/manual#pbet
	 * @param parameter
	 * @return XML, JSON, or <code>PlacedBet</code>
	 * @throws PinnacleException
	 */
	public String placeBetAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.placeBet(parameter).json();
		return this.execute(request).text();
	}

	public String placeBetAsXml (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.placeBet(parameter).xml();
		return this.execute(request).text();
	}

	public PlacedBet placeBet (Parameter parameter) throws PinnacleException {
		return PlacedBet.parse(placeBetAsJson(parameter));
	}
	
	/**
	 * Place Parlay Bet
	 * 	http://www.pinnaclesports.com/en/api/manual#PlaceParlayBet
	 * @param parameter
	 * @return JSON or <code>PlacedParlayBet</code>
	 * @throws PinnacleException
	 */
	public String placeParlayBetAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.placeParlayBet(parameter).json();
		return this.execute(request).text();
	}

	public PlacedParlayBet placeParlayBet (Parameter parameter) throws PinnacleException {
		return PlacedParlayBet.parse(placeParlayBetAsJson(parameter));
	}
	
	/**
	 * Get Line
	 * 	http://www.pinnaclesports.com/en/api/manual#Gline
	 * @return AsXml or AsJson
	 * @throws PinnacleException
	 */
	public String getLineAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getLine(parameter).json();
		return this.execute(request).text();
	}

	public String getLineAsXml (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getLine(parameter).xml();
		return this.execute(request).text();
	}

	public Line getLine (Parameter parameter) throws PinnacleException {
		return Line.parse(getLineAsJson(parameter));
	}
	
	/**
	 * Get Parlay Line
	 * 	http://www.pinnaclesports.com/en/api/manual#GetParlayLines
	 * @param parameter
	 * @return AsJson
	 * @throws PinnacleException
	 */
	public String getParlayLineAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getParlayLine(parameter).json();
		return this.execute(request).text();
	}
	
	public ParlayLines getParlayLine (Parameter parameter) throws PinnacleException { 
		return ParlayLines.parse(getParlayLineAsJson(parameter));
	}
	
	/**
	 * Get Bets
	 * 	http://www.pinnaclesports.com/en/api/manual#Gbets
	 * @return AsXml or AsJson
	 * @throws PinnacleException
	 */
	public String getBetsAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getBets(parameter).json();
		return this.execute(request).text();
	}

	public String getBetsAsXml (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getBets(parameter).xml();
		return this.execute(request).text();
	}
	
	// Can't parse if bets including Parlay bet. Works if not including.
	public List<Bet> getBetsAsList (Parameter parameter) throws PinnacleException {
		return Bet.parse(getBetsAsJson(parameter));
	}
	
	/**
	 * Get Inrunning
	 * 	http://www.pinnaclesports.com/en/api/manual#GInrunning
	 * @return AsXml or AsJson
	 * @throws PinnacleException
	 */
	public String getInRunningAsJson () throws PinnacleException { 
		Request request = Requesters.getInrunning().json();
		return this.execute(request).text();
	}

	public String getInRunningAsXml () throws PinnacleException { 
		Request request = Requesters.getInrunning().xml();
		return this.execute(request).text();
	}

	public InRunning getInRunning () throws PinnacleException {
		return InRunning.parse(getInRunningAsJson());
	}
	
	public List<InRunning.Event> getInRunningEventsAsList () throws PinnacleException {
		return InRunning.parse(getInRunningAsJson())
				.sports()
				.parallelStream()
				.flatMap(InRunning.Sport::streamOfLeagues)
				.flatMap(InRunning.League::streamOfEvents)
				.collect(Collectors.toList());
	}
	
	/**
	 * Get Translations
	 * 	http://www.pinnaclesports.com/en/api/manual#GTranslations
	 * @return AsXml or AsJson
	 * @throws PinnacleException
	 */
	public String getTranslationsAsJson (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getTranslations(parameter).json();
		return this.execute(request).text();
	}

	public String getTranslationsAsXml (Parameter parameter) throws PinnacleException { 
		Request request = Requesters.getTranslations(parameter).xml();
		return this.execute(request).text();
	}
	
	public List<Translations> getTranslationsAsList (Parameter parameter) throws PinnacleException {
		return Translations.parse(getTranslationsAsJson(parameter));
	}
}
