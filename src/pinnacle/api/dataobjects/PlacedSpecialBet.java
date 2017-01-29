package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.PLACE_SPECIAL_BET_ERROR_CODE;
import pinnacle.api.enums.PLACE_SPECIAL_BET_RESPONSE_STATUS;

public class PlacedSpecialBet extends AbstractDataObject {

	private List<Bet> bets = new ArrayList<>();

	public List<Bet> bets() {
		return this.bets;
	}

	public Stream<Bet> betsAsStream() {
		return this.bets.stream();
	}

	private PlacedSpecialBet(Json json) {
		this.bets = json.getAsJsonStream("bets").map(Bet::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private PlacedSpecialBet() {
		this.isEmpty = true;
	}

	public static PlacedSpecialBet parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new PlacedSpecialBet() : new PlacedSpecialBet(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "PlacedSpecialBet [bets=" + bets + "]";
	}

	public static class Bet extends AbstractDataObject {

		private PLACE_SPECIAL_BET_RESPONSE_STATUS status;

		public PLACE_SPECIAL_BET_RESPONSE_STATUS status() {
			return this.status;
		}

		private Optional<PLACE_SPECIAL_BET_ERROR_CODE> errorCode;

		public Optional<PLACE_SPECIAL_BET_ERROR_CODE> errorCode() {
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

		private Optional<Boolean> betterLineWasAccepted;

		public Optional<Boolean> betterLineWasAccepted() {
			return this.betterLineWasAccepted;
		}

		private Bet(Json json) {
			this.status = json.getAsString("status").map(PLACE_SPECIAL_BET_RESPONSE_STATUS::fromAPI).orElse(null);
			this.errorCode = json.getAsString("errorCode").map(PLACE_SPECIAL_BET_ERROR_CODE::fromAPI);
			this.betId = json.getAsLong("betId");
			this.uniqueRequestId = json.getAsString("uniqueRequestId").orElse(null);
			this.betterLineWasAccepted = json.getAsBoolean("betterLineWasAccepted");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.uniqueRequestId == null;
		}

		@Override
		public String toString() {
			return "Bet [status=" + status + ", errorCode=" + errorCode + ", betId=" + betId + ", uniqueRequestId="
					+ uniqueRequestId + ", betterLineWasAccepted=" + betterLineWasAccepted + "]";
		}
	}
}
