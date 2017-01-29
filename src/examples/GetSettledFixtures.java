package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.SettledFixtures;

public class GetSettledFixtures {

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
		
		// response as plain json text
		String jsonText = api.getSettledFixturesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		SettledFixtures settledFixtures = api.getSettledFixturesAsObject(parameter);
		System.out.println(settledFixtures);
	}
}
