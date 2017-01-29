package examples;

import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.CancellationReasons;

public class GetCancellationReasons {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// response as plain json text
		String jsonText = api.getCancellationReasonsAsJson();
		System.out.println(jsonText);
		
		// response as data object
		CancellationReasons cancellationReasons = api.getCancellationReasonsAsObject();
		System.out.println(cancellationReasons);
	}
}
