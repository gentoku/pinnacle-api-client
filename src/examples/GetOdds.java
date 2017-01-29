package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Odds;
import pinnacle.api.enums.ODDS_FORMAT;

public class GetOdds {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueIds(1980, 1977);
		parameter.since(365915784);
		parameter.isLive(true);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		
		// response as plain json text
		String jsonText = api.getOddsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Odds odds = api.getOddsAsObject(parameter);
		System.out.println(odds);
	}
}
