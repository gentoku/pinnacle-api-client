package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.TeaserGroups;
import pinnacle.api.enums.ODDS_FORMAT;

public class GetTeaserGroups {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		
		// response as plain json text
		String jsonText = api.getTeaserGroupsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		TeaserGroups teaserGroups = api.getTeaserGroupsAsObject(parameter);
		System.out.println(teaserGroups);
	}
}
