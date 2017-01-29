package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.SpecialFixtures;

public class GetSpecialFixtures {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(29);
		//parameter.leagueIds(1980);
		parameter.category("Top Goalscorer");
		//parameter.eventId();
		//parameter.specialId();
		//parameter.since();
		
		// response as plain json text
		String jsonText = api.getSpecialFixturesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		SpecialFixtures specialFixtures = api.getSpecialFixturesAsObject(parameter);
		System.out.println(specialFixtures);
	}
}
