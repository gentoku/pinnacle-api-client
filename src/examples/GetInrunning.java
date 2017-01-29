package examples;

import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Inrunning;

public class GetInrunning {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// response as plain json text
		String jsonText = api.getInrunningAsJson();
		System.out.println(jsonText);
		
		// response as data object
		Inrunning inrunning = api.getInrunningAsObject();
		System.out.println(inrunning);
	}
}
