package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.TeaserOdds;

public class GetTeaserOdds {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.teaserId(50);
		
		// response as plain json text
		String jsonText = api.getTeaserOddsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		TeaserOdds teaserOdds = api.getTeaserOddsAsObject(parameter);
		System.out.println(teaserOdds);
	}
}
