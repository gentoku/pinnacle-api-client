package pinnacle.api;

import java.util.Optional;

import pinnacle.api.Enums.PLACEBET_ERROR_CODE;
import pinnacle.api.Enums.PLACEBET_RESPONSE_STATUS;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class PlacedBet {

	/**
	 * Response status.
	 */
	private PLACEBET_RESPONSE_STATUS status;
	public PLACEBET_RESPONSE_STATUS status () { return this.status; }

	/**
	 * If Status is PROCESSED_WITH_ERROR, errorCode will be in the response. (Optional)
	 */
	private Optional<PLACEBET_ERROR_CODE> errorCode;
	public Optional<PLACEBET_ERROR_CODE> errorCode () { return this.errorCode; }

	/**
	 * The bet ID of the new bet. May be empty on failure. (Optional)
	 */
	private Optional<Integer> betId;
	public Optional<Integer> betId () { return this.betId; }

	/**
	 * Echo of the uniqueRequestId from the request.
	 */
	private String uniqueRequestId;
	public String uniqueRequestId () { return this.uniqueRequestId; }

	/**
	 * Whether or not the bet was accepted on the line that changed in favour of client.
	 * This can be true only if acceptBetterLine in the Place Bet request is set to TRUE.
	 * 
	 * Pinnaclesports.com set this as required but it is actually optional, 
	 * no value returned for this when a request processed with error. 
	 */
	private Optional<Boolean> betterLineWasAccepted;
	public Optional<Boolean> betterLineWasAccepted () { return this.betterLineWasAccepted; }
	
	private PlacedBet (Json json) {
		this.status = json.getAsString("status")
				.map(PLACEBET_RESPONSE_STATUS::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("PlacedBet.status"));
		this.errorCode = json.getAsString("errorCode")
				.map(PLACEBET_ERROR_CODE::valueOf); // optional
		this.betId = json.getAsInteger("betId"); // optional
		this.uniqueRequestId = json.getAsString("uniqueRequestId")
				.orElseThrow(() -> NoNecessaryKeyException.of("PlacedBet.uniqueRequestId"));
		this.betterLineWasAccepted = json.getAsBoolean("betterLineWasAccepted");
	}
	
	static PlacedBet parse (String text) throws PinnacleException {
		if (text.equals("")) throw new PinnacleException("Empty response for Place Bet.");
		return new PlacedBet(Json.of(text));
	}
	
	
	@Override
	public String toString() {
		return "PlacedBet [status=" + status + ", errorCode=" + errorCode
				+ ", betId=" + betId + ", uniqueRequestId=" + uniqueRequestId
				+ ", betterLineWasAccepted=" + betterLineWasAccepted + "]";
	}
}
