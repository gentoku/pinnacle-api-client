package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.SpecialOdds;
import pinnacle.api.enums.ODDS_FORMAT;

public class GetSpecialOdds {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueIds(1980);
		//parameter.since();
		//parameter.specialId();
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		
		// response as plain json text
		String jsonText = api.getSpecialOddsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		SpecialOdds specialOdds = api.getSpecialOddsAsObject(parameter);
		System.out.println(specialOdds);
	}
}
