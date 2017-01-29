package pinnacle.api;

import java.net.MalformedURLException;
import java.net.URL;

import pinnacle.api.Connection.Request;

public class Requesters {

	private static URL getUrl (String url) throws PinnacleException {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new PinnacleException("Malformed URL:" + e.getMessage());
		}
	}
	
	static Request getSports () throws PinnacleException {
		URL url = getUrl(PinnacleAPI.BASE_URL + "/sports");
		return Request.newInstance(url).get();
	}
	
	static Request getLeagues (Parameter parameter) throws PinnacleException {
		parameter.toLowerCase(); // this may need to be deleted or modified some day.
		Validator validator = parameter.getValidator();
		validator.requiredKey("sportid"); // not camelCase
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/leagues?" + parameter.get());
		return Request.newInstance(url).get();
	}
	
	@Deprecated // get feed operation is obsoleted.
	static Request getFeed (Parameter parameter) throws PinnacleException {
		Validator validator = parameter.getValidator();
		validator.requiredKey("sportId"); 
		validator.optionalKey("leagueId");
		validator.optionalKey("multipleLeagueId");
		validator.optionalKey("oddsFormat");
		validator.optionalKey("last");
		validator.optionalKey("isLive");
		validator.optionalKey("currencyCode");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/feed?" + parameter.get());
		Request request = Request.newInstance(url).get();
		if (parameter.hasLast()) {
			return request.throttle(PinnacleAPI.THROTTLE_WITH_LAST);
		} else {
			return request.throttle(PinnacleAPI.THROTTLE_WITHOUT_LAST);
		} 
	}
	
	static Request getFixtures (Parameter parameter) throws PinnacleException {
		Validator validator = parameter.getValidator();
		validator.requiredKey("sportId");
		validator.optionalKey("leagueIds");
		validator.optionalKey("since");
		validator.optionalKey("isLive");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/fixtures?" + parameter.get());
		return Request.newInstance(url).get();
	}
	
	static Request getOdds (Parameter parameter) throws PinnacleException {
		Validator validator = parameter.getValidator();
		validator.requiredKey("sportId");
		validator.optionalKey("leagueIds");
		validator.optionalKey("since");
		validator.optionalKey("isLive");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/odds?" + parameter.get());
		return Request.newInstance(url).get();
	}
	
	static Request getCurrencies () throws PinnacleException {
		URL url = getUrl(PinnacleAPI.BASE_URL + "/currencies");
		return Request.newInstance(url).get();
	}
	
	static Request getClientBalance () throws PinnacleException {
		URL url = getUrl(PinnacleAPI.BASE_URL + "/client/balance");
		return Request.newInstance(url).get();
	}
	
	static Request placeBet (Parameter parameter) throws PinnacleException {
		parameter.getValidator().validatePlaceBet();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/bets/place");
		return Request.newInstance(url).post(parameter);
	}
	
	static Request placeParlayBet (Parameter parameter) throws PinnacleException {
		parameter.legs()
			.parallelStream()
			.map(Parameter::getValidator)
			.forEach(Validator::validateLegToPlace);
		Validator validator = parameter.getValidator();
		validator.requiredKey("uniqueRequestId");
		validator.requiredKey("acceptBetterLine");
		validator.requiredKey("oddsFormat");
		validator.requiredKey("riskAmount");
		validator.requiredKey("roundRobinOptions");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/bets/parlay");
		return Request.newInstance(url).post(parameter);
	}
	
	static Request getLine (Parameter parameter) throws PinnacleException {
		parameter.getValidator().validateLine();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/line?" + parameter.get());
		return Request.newInstance(url).get();
	}
	
	static Request getParlayLine (Parameter parameter) throws PinnacleException {
		parameter.legs()
			.parallelStream()
			.map(Parameter::getValidator)
			.forEach(Validator::validateLegToGet);
		Validator validator = parameter.getValidator();
		validator.requiredKey("oddsFormat");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/line/parlay");
		return Request.newInstance(url).post(parameter);
	}
	
	static Request getBets (Parameter parameter) throws PinnacleException {
		Validator validator = parameter.getValidator();
		validator.validateBets();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/bets?" + parameter.get());
		return Request.newInstance(url).get();
	}
	
	static Request getInrunning () throws PinnacleException {
		URL url = getUrl(PinnacleAPI.BASE_URL + "/inrunning");
		return Request.newInstance(url).get();
	}
	
	static Request getTranslations (Parameter parameter) throws PinnacleException {
		Validator validator = parameter.getValidator();
		validator.requiredKey("cultureCodes");
		validator.requiredKey("baseTexts");
		validator.validateKeys();
		URL url = getUrl(PinnacleAPI.BASE_URL + "/translations?" + parameter.get());
		return Request.newInstance(url).get();
	}
}
