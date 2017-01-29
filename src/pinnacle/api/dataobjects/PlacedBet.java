package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.Optional;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.PLACEBET_ERROR_CODE;
import pinnacle.api.enums.PLACEBET_RESPONSE_STATUS;

public class PlacedBet extends AbstractDataObject {

	private PLACEBET_RESPONSE_STATUS status;

	public PLACEBET_RESPONSE_STATUS status() {
		return this.status;
	}

	private Optional<PLACEBET_ERROR_CODE> errorCode;

	public Optional<PLACEBET_ERROR_CODE> errorCode() {
		return this.errorCode;
	}

	private Optional<Long> betId;

	public Optional<Long> betId() {
		return this.betId;
	}

	private String uniqueRequestId;

	public String uniqueRequestId() {
		return this.uniqueRequestId;
	}

	private Boolean betterLineWasAccepted;

	public Boolean betterLineWasAccepted() {
		return this.betterLineWasAccepted;
	}

	private Optional<BigDecimal> price;

	public Optional<BigDecimal> price() {
		return this.price;
	}

	private PlacedBet(Json json) {
		this.status = json.getAsString("status").map(PLACEBET_RESPONSE_STATUS::fromAPI).orElse(null);
		this.errorCode = json.getAsString("errorCode").map(PLACEBET_ERROR_CODE::fromAPI);
		this.betId = json.getAsLong("betId");
		this.uniqueRequestId = json.getAsString("uniqueRequestId").orElse(null);
		this.betterLineWasAccepted = json.getAsBoolean("betterLineWasAccepted").orElse(null);
		this.price = json.getAsBigDecimal("price");
		this.checkRequiredKeys();
	}

	private PlacedBet() {
		this.isEmpty = true;
	}

	public static PlacedBet parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new PlacedBet() : new PlacedBet(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null || this.uniqueRequestId == null || this.betterLineWasAccepted == null;
	}

	@Override
	public String toString() {
		return "PlacedBet [status=" + status + ", errorCode=" + errorCode + ", betId=" + betId + ", uniqueRequestId="
				+ uniqueRequestId + ", betterLineWasAccepted=" + betterLineWasAccepted + ", price=" + price + "]";
	}
}
