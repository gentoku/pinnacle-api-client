package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Fixtures;

public class GetFixtures {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueIds(1980, 1977);
		//parameter.since();
		parameter.isLive(true);
		
		// response as plain json text
		String jsonText = api.getFixturesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Fixtures fixtures = api.getFixturesAsObject(parameter);
		System.out.println(fixtures);
	}
}
