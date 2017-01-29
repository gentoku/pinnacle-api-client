package examples;

import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.ClientBalance;

public class GetClientBalance {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// response as plain json text
		String jsonText = api.getClientBalanceAsJson();
		System.out.println(jsonText);
		
		// response as data object
		ClientBalance clientBalance = api.getClientBalanceAsObject();
		System.out.println(clientBalance);
	}
}
