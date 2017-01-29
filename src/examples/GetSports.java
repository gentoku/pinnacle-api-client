package examples;

import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Sports;

public class GetSports {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// response as plain json text
		String jsonText = api.getSportsAsJson();
		System.out.println(jsonText);
		
		// response as data object
		Sports sports = api.getSportsAsObject();
		System.out.println(sports);
	}
}
