package pinnacle.api;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import pinnacle.api.dataobjects.Bets;
import pinnacle.api.dataobjects.CancellationReasons;
import pinnacle.api.dataobjects.ClientBalance;
import pinnacle.api.dataobjects.Currencies;
import pinnacle.api.dataobjects.Fixtures;
import pinnacle.api.dataobjects.Inrunning;
import pinnacle.api.dataobjects.Leagues;
import pinnacle.api.dataobjects.Line;
import pinnacle.api.dataobjects.Odds;
import pinnacle.api.dataobjects.ParlayLines;
import pinnacle.api.dataobjects.Periods;
import pinnacle.api.dataobjects.PlacedBet;
import pinnacle.api.dataobjects.PlacedParlayBet;
import pinnacle.api.dataobjects.PlacedSpecialBet;
import pinnacle.api.dataobjects.PlacedTeaserBet;
import pinnacle.api.dataobjects.SettledFixtures;
import pinnacle.api.dataobjects.SettledSpecialFixtures;
import pinnacle.api.dataobjects.SpecialFixtures;
import pinnacle.api.dataobjects.SpecialLines;
import pinnacle.api.dataobjects.SpecialOdds;
import pinnacle.api.dataobjects.Sports;
import pinnacle.api.dataobjects.TeaserGroups;
import pinnacle.api.dataobjects.TeaserLines;
import pinnacle.api.dataobjects.TeaserOdds;
import pinnacle.api.dataobjects.Translations;

public class PinnacleAPI {

	/**
	 * Base64 value of UTF-8 encoded "username:password" for authentication.
	 */
	private String credentials;

	/**
	 * Constructor with credentials.
	 * 
	 * @param credentials
	 */
	private PinnacleAPI(String credentials) {
		this.credentials = credentials;
	}

	/**
	 * Factory with credentials.
	 * 
	 * @param credentials
	 * @return
	 */
	public static PinnacleAPI open(String credentials) {
		return new PinnacleAPI(credentials);
	}

	/**
	 * Factory with raw username and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws PinnacleException
	 */
	public static PinnacleAPI open(String username, String password) throws PinnacleException {
		String credentials = encode(username, password);
		return new PinnacleAPI(credentials);
	}

	/**
	 * Encodes username and password by Base64.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws PinnacleException
	 * @throws UnsupportedEncodingException
	 */
	private static String encode(String username, String password) throws PinnacleException {
		try {
			String id = username + ":" + password;
			byte[] bytes = id.getBytes(Constants.CHARSET);
			return Base64.getEncoder().encodeToString(bytes);
		} catch (UnsupportedEncodingException e) {
			throw PinnacleException.parameterInvalid(
					"Couldn't encode your username and password. Caught UnsupportedEncodingException: "
							+ e.getMessage());
		}
	}

	/**
	 * Returns URL from string. Converts MalformedURLException to
	 * PinnacleException if catches.
	 * 
	 * @param url
	 * @return
	 * @throws PinnacleException
	 */
	private static URL getUrl(String url) throws PinnacleException {
		try {
			return new URL(url);
		} catch (MalformedURLException e) { // should not catch
			throw PinnacleException
					.parameterInvalid("Malformed URL created in the code. Not from input." + e.getMessage());
		}
	}

	/**
	 * Sends requests to 'GetSports' and returns its response.
	 * 
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSports() throws PinnacleException {
		URL url = getUrl(Constants.BASE_URL + "/v2/sports");
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSports' and returns its response as JSON plain
	 * text.
	 * 
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSportsAsJson() throws PinnacleException {
		return this.getSports().text();
	}

	/**
	 * Sends requests to 'GetSports' and returns its response as mapped Object.
	 * 
	 * @return Sports - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Sports getSportsAsObject() throws PinnacleException {
		return Sports.parse(this.getSportsAsJson());
	}

	/**
	 * Sends requests to 'GetLeagues' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getLeagues(Parameter parameter) throws PinnacleException {
		Validators.toGetLeagues(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v2/leagues?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetLeagues' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getLeaguesAsJson(Parameter parameter) throws PinnacleException {
		return this.getLeagues(parameter).text();
	}

	/**
	 * Sends requests to 'GetLeagues' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Leagues - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Leagues getLeaguesAsObject(Parameter parameter) throws PinnacleException {
		return Leagues.parse(this.getLeaguesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetFixtures' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getFixtures(Parameter parameter) throws PinnacleException {
		Validators.toGetFixtures(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/fixtures?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetFixtures' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getFixturesAsJson(Parameter parameter) throws PinnacleException {
		return this.getFixtures(parameter).text();
	}

	/**
	 * Sends requests to 'GetFixtures' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Fixtures - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Fixtures getFixturesAsObject(Parameter parameter) throws PinnacleException {
		return Fixtures.parse(this.getFixturesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetSettledFixtures' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSettledFixtures(Parameter parameter) throws PinnacleException {
		Validators.toGetSettledFixtures(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/fixtures/settled?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSettledFixtures' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSettledFixturesAsJson(Parameter parameter) throws PinnacleException {
		return this.getSettledFixtures(parameter).text();
	}

	/**
	 * Sends requests to 'GetSettledFixtures' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return SettledFixtures - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public SettledFixtures getSettledFixturesAsObject(Parameter parameter) throws PinnacleException {
		return SettledFixtures.parse(this.getSettledFixturesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetSpecialFixtures' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSpecialFixtures(Parameter parameter) throws PinnacleException {
		Validators.toGetSpecialFixtures(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/fixtures/special?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSpecialFixtures' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSpecialFixturesAsJson(Parameter parameter) throws PinnacleException {
		return this.getSpecialFixtures(parameter).text();
	}

	/**
	 * Sends requests to 'GetSpecialFixtures' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return SpecialFixtures - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public SpecialFixtures getSpecialFixturesAsObject(Parameter parameter) throws PinnacleException {
		return SpecialFixtures.parse(this.getSpecialFixturesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetSettledSpecialFixtures' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSettledSpecialFixtures(Parameter parameter) throws PinnacleException {
		Validators.toGetSettledSpecialFixtures(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/fixtures/special/settled?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSettledSpecialFixtures' and returns its response as
	 * JSON plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSettledSpecialFixturesAsJson(Parameter parameter) throws PinnacleException {
		return this.getSettledSpecialFixtures(parameter).text();
	}

	/**
	 * Sends requests to 'GetSettledSpecialFixtures' and returns its response as
	 * mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return SettledSpecialFixtures - isEmpty will be set true when JSON text
	 *         is empty and isFlawed will be set when JSON text doesn't have all
	 *         of required keys.
	 * @throws PinnacleException
	 */
	public SettledSpecialFixtures getSettledSpecialFixturesAsObject(Parameter parameter) throws PinnacleException {
		return SettledSpecialFixtures.parse(this.getSettledSpecialFixturesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetTeaserGroups' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getTeaserGroups(Parameter parameter) throws PinnacleException {
		Validators.toGetTeaserGroups(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/teaser/groups?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetTeaserGroups' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getTeaserGroupsAsJson(Parameter parameter) throws PinnacleException {
		return this.getTeaserGroups(parameter).text();
	}

	/**
	 * Sends requests to 'GetTeaserGroups' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return TeaserGroups - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public TeaserGroups getTeaserGroupsAsObject(Parameter parameter) throws PinnacleException {
		return TeaserGroups.parse(this.getTeaserGroupsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetOdds' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getOdds(Parameter parameter) throws PinnacleException {
		Validators.toGetOdds(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/odds?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetOdds' and returns its response as JSON plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getOddsAsJson(Parameter parameter) throws PinnacleException {
		return this.getOdds(parameter).text();
	}

	/**
	 * Sends requests to 'GetOdds' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Odds - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Odds getOddsAsObject(Parameter parameter) throws PinnacleException {
		return Odds.parse(this.getOddsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetParlayOdds' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getParlayOdds(Parameter parameter) throws PinnacleException {
		Validators.toGetParlayOdds(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/odds/parlay?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetParlayOdds' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getParlayOddsAsJson(Parameter parameter) throws PinnacleException {
		return this.getParlayOdds(parameter).text();
	}

	/**
	 * Sends requests to 'GetParlayOdds' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Odds - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Odds getParlayOddsAsObject(Parameter parameter) throws PinnacleException {
		return Odds.parse(this.getParlayOddsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetTeaserOdds' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getTeaserOdds(Parameter parameter) throws PinnacleException {
		Validators.toGetTeaserOdds(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/odds/teaser?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetTeaserOdds' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getTeaserOddsAsJson(Parameter parameter) throws PinnacleException {
		return this.getTeaserOdds(parameter).text();
	}

	/**
	 * Sends requests to 'GetTeaserOdds' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return TeaserOdds - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public TeaserOdds getTeaserOddsAsObject(Parameter parameter) throws PinnacleException {
		return TeaserOdds.parse(this.getTeaserOddsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetSpecialOdds' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSpecialOdds(Parameter parameter) throws PinnacleException {
		Validators.toGetSpecialOdds(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/odds/special?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get().fairUse(parameter.withSince());
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSpecialOdds' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSpecialOddsAsJson(Parameter parameter) throws PinnacleException {
		return this.getSpecialOdds(parameter).text();
	}

	/**
	 * Sends requests to 'GetSpecialOdds' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return SpecialOdds - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public SpecialOdds getSpecialOddsAsObject(Parameter parameter) throws PinnacleException {
		return SpecialOdds.parse(this.getSpecialOddsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetCurrencies' and returns its response.
	 * 
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getCurrencies() throws PinnacleException {
		URL url = getUrl(Constants.BASE_URL + "/v2/currencies");
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetCurrencies' and returns its response as JSON plain
	 * text.
	 * 
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getCurrenciesAsJson() throws PinnacleException {
		return this.getCurrencies().text();
	}

	/**
	 * Sends requests to 'GetCurrencies' and returns its response as mapped
	 * Object.
	 * 
	 * @return Currencies - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Currencies getCurrenciesAsObject() throws PinnacleException {
		return Currencies.parse(this.getCurrenciesAsJson());
	}

	/**
	 * Sends requests to 'GetClientBalance' and returns its response.
	 * 
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getClientBalance() throws PinnacleException {
		URL url = getUrl(Constants.BASE_URL + "/v1/client/balance");
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetClientBalance' and returns its response as JSON
	 * plain text.
	 * 
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getClientBalanceAsJson() throws PinnacleException {
		return this.getClientBalance().text();
	}

	/**
	 * Sends requests to 'GetClientBalance' and returns its response as mapped
	 * Object.
	 * 
	 * @return ClientBalance - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public ClientBalance getClientBalanceAsObject() throws PinnacleException {
		return ClientBalance.parse(this.getClientBalanceAsJson());
	}

	/**
	 * Sends requests to 'GetLine' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getLine(Parameter parameter) throws PinnacleException {
		Validators.toGetLine(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/line?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetLine' and returns its response as JSON plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getLineAsJson(Parameter parameter) throws PinnacleException {
		return this.getLine(parameter).text();
	}

	/**
	 * Sends requests to 'GetLine' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Line - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Line getLineAsObject(Parameter parameter) throws PinnacleException {
		return Line.parse(this.getLineAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetParlayLines' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getParlayLines(Parameter parameter) throws PinnacleException {
		Validators.toGetParlayLines(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/line/parlay");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetParlayLines' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getParlayLinesAsJson(Parameter parameter) throws PinnacleException {
		return this.getParlayLines(parameter).text();
	}

	/**
	 * Sends requests to 'GetParlayLines' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return ParlayLines - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public ParlayLines getParlayLinesAsObject(Parameter parameter) throws PinnacleException {
		return ParlayLines.parse(this.getParlayLinesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetTeaserLines' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getTeaserLines(Parameter parameter) throws PinnacleException {
		Validators.toGetTeaserLines(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/line/teaser");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetTeaserLines' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getTeaserLinesAsJson(Parameter parameter) throws PinnacleException {
		return this.getTeaserLines(parameter).text();
	}

	/**
	 * Sends requests to 'GetTeaserLines' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return TeaserLines - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public TeaserLines getTeaserLinesAsObject(Parameter parameter) throws PinnacleException {
		return TeaserLines.parse(this.getTeaserLinesAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetSpecialLines' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getSpecialLines(Parameter parameter) throws PinnacleException {
		Validators.toGetSpecialLines(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/line/special?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetSpecialLines' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getSpecialLinesAsJson(Parameter parameter) throws PinnacleException {
		return this.getSpecialLines(parameter).text();
	}

	/**
	 * Sends requests to 'GetSpecialLines' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return SpecialLines - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public SpecialLines getSpecialLinesAsObject(Parameter parameter) throws PinnacleException {
		return SpecialLines.parse(this.getSpecialLinesAsJson(parameter));
	}

	/**
	 * Sends requests to 'PlaceBet' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response placeBet(Parameter parameter) throws PinnacleException {
		Validators.toPlaceBet(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/bets/place");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'PlaceBet' and returns its response as JSON plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String placeBetAsJson(Parameter parameter) throws PinnacleException {
		return this.placeBet(parameter).text();
	}

	/**
	 * Sends requests to 'PlaceBet' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return PlacedBet - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public PlacedBet placeBetAsObject(Parameter parameter) throws PinnacleException {
		return PlacedBet.parse(this.placeBetAsJson(parameter));
	}

	/**
	 * Sends requests to 'PlaceParlayBet' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response placeParlayBet(Parameter parameter) throws PinnacleException {
		Validators.toPlaceParlayBet(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/bets/parlay");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'PlaceParlayBet' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String placeParlayBetAsJson(Parameter parameter) throws PinnacleException {
		return this.placeParlayBet(parameter).text();
	}

	/**
	 * Sends requests to 'PlaceParlayBet' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return PlacedParlayBet - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public PlacedParlayBet placeParlayBetAsObject(Parameter parameter) throws PinnacleException {
		return PlacedParlayBet.parse(this.placeParlayBetAsJson(parameter));
	}

	/**
	 * Sends requests to 'PlaceTeaserBet' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response placeTeaserBet(Parameter parameter) throws PinnacleException {
		Validators.toPlaceTeaserBet(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/bets/teaser");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'PlaceTeaserBet' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String placeTeaserBetAsJson(Parameter parameter) throws PinnacleException {
		return this.placeTeaserBet(parameter).text();
	}

	/**
	 * Sends requests to 'PlaceTeaserBet' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return PlacedTeaserBet - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public PlacedTeaserBet placeTeaserBetAsObject(Parameter parameter) throws PinnacleException {
		return PlacedTeaserBet.parse(this.placeTeaserBetAsJson(parameter));
	}

	/**
	 * Sends requests to 'PlaceSpecialBet' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response placeSpecialBet(Parameter parameter) throws PinnacleException {
		Validators.toPlaceSpecialBet(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/bets/special");
		Request request = Request.newRequest(url).post(parameter);
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'PlaceSpecialBet' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String placeSpecialBetAsJson(Parameter parameter) throws PinnacleException {
		return this.placeSpecialBet(parameter).text();
	}

	/**
	 * Sends requests to 'PlaceSpecialBet' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return PlacedSpecialBet - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public PlacedSpecialBet placeSpecialBetAsObject(Parameter parameter) throws PinnacleException {
		return PlacedSpecialBet.parse(this.placeSpecialBetAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetBets' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getBets(Parameter parameter) throws PinnacleException {
		Validators.toGetBets(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/bets?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetBets' and returns its response as JSON plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getBetsAsJson(Parameter parameter) throws PinnacleException {
		return this.getBets(parameter).text();
	}

	/**
	 * Sends requests to 'GetBets' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Bets - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Bets getBetsAsObject(Parameter parameter) throws PinnacleException {
		return Bets.parse(this.getBetsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetInrunning' and returns its response.
	 * 
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getInrunning() throws PinnacleException {
		URL url = getUrl(Constants.BASE_URL + "/v1/inrunning");
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetInrunning' and returns its response as JSON plain
	 * text.
	 * 
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getInrunningAsJson() throws PinnacleException {
		return this.getInrunning().text();
	}

	/**
	 * Sends requests to 'GetInrunning' and returns its response as mapped
	 * Object.
	 * 
	 * @return Inrunning - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Inrunning getInrunningAsObject() throws PinnacleException {
		return Inrunning.parse(this.getInrunningAsJson());
	}

	/**
	 * Sends requests to 'GetTranslations' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getTranslations(Parameter parameter) throws PinnacleException {
		Validators.toGetTranslations(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/translations?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetTranslations' and returns its response as JSON
	 * plain text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getTranslationsAsJson(Parameter parameter) throws PinnacleException {
		return this.getTranslations(parameter).text();
	}

	/**
	 * Sends requests to 'GetTranslations' and returns its response as mapped
	 * Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Translations - isEmpty will be set true when JSON text is empty
	 *         and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public Translations getTranslationsAsObject(Parameter parameter) throws PinnacleException {
		return Translations.parse(this.getTranslationsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetPeriods' and returns its response.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getPeriods(Parameter parameter) throws PinnacleException {
		Validators.toGetPeriods(parameter);
		URL url = getUrl(Constants.BASE_URL + "/v1/periods?" + parameter.toUrlQuery());
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetPeriods' and returns its response as JSON plain
	 * text.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getPeriodsAsJson(Parameter parameter) throws PinnacleException {
		return this.getPeriods(parameter).text();
	}

	/**
	 * Sends requests to 'GetPeriods' and returns its response as mapped Object.
	 * 
	 * @param parameter
	 *            - must be set proper values.
	 * @return Periods - isEmpty will be set true when JSON text is empty and
	 *         isFlawed will be set when JSON text doesn't have all of required
	 *         keys.
	 * @throws PinnacleException
	 */
	public Periods getPeriodsAsObject(Parameter parameter) throws PinnacleException {
		return Periods.parse(this.getPeriodsAsJson(parameter));
	}

	/**
	 * Sends requests to 'GetCancellationReasons' and returns its response.
	 * 
	 * @return Response
	 * @throws PinnacleException
	 */
	private Response getCancellationReasons() throws PinnacleException {
		URL url = getUrl(Constants.BASE_URL + "/v1/cancellationreasons");
		Request request = Request.newRequest(url).get();
		return request.execute(this.credentials);
	}

	/**
	 * Sends requests to 'GetCancellationReasons' and returns its response as
	 * JSON plain text.
	 * 
	 * @return JSON text
	 * @throws PinnacleException
	 */
	public String getCancellationReasonsAsJson() throws PinnacleException {
		return this.getCancellationReasons().text();
	}

	/**
	 * Sends requests to 'GetCancellationReasons' and returns its response as
	 * mapped Object.
	 * 
	 * @return CancellationReasons - isEmpty will be set true when JSON text is
	 *         empty and isFlawed will be set when JSON text doesn't have all of
	 *         required keys.
	 * @throws PinnacleException
	 */
	public CancellationReasons getCancellationReasonsAsObject() throws PinnacleException {
		return CancellationReasons.parse(this.getCancellationReasonsAsJson());
	}

}
