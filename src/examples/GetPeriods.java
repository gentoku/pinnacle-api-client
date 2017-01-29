package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Periods;

public class GetPeriods {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(4);
		
		// response as plain json text
		String jsonText = api.getPeriodsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Periods periods = api.getPeriodsAsObject(parameter);
		System.out.println(periods);
	}
}
