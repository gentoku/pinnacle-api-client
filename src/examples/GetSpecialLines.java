package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.SpecialLines;
import pinnacle.api.enums.ODDS_FORMAT;

public class GetSpecialLines {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.specialId(686671592);
		parameter.contestantId(686671598);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		
		// response as plain json text
		String jsonText = api.getSpecialLinesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		SpecialLines specialLines = api.getSpecialLinesAsObject(parameter);
		System.out.println(specialLines);
	}
}
