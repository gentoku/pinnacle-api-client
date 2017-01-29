package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Leagues;

public class GetLeagues {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(12);
		
		// response as plain json text
		String jsonText = api.getLeaguesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Leagues leagues = api.getLeaguesAsObject(parameter);
		System.out.println(leagues);
	}
}
