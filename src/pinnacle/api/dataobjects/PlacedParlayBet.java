package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.PLACE_PARLAY_BET_ERROR_CODE;
import pinnacle.api.enums.PLACE_PARLAY_BET_RESPONSE_STATUS;
import pinnacle.api.enums.PLACE_PARLAY_LEG_ERROR_CODE;
import pinnacle.api.enums.PLACE_PARLAY_LEG_RESPONSE_STATUS;
import pinnacle.api.enums.ROUND_ROBIN_OPTIONS;

public class PlacedParlayBet extends AbstractDataObject {

	private PLACE_PARLAY_BET_RESPONSE_STATUS status;

	public PLACE_PARLAY_BET_RESPONSE_STATUS status() {
		return this.status;
	}

	private Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode;

	public Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode() {
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

	private List<ROUND_ROBIN_OPTIONS> roundRobinOptions = new ArrayList<>();

	public List<ROUND_ROBIN_OPTIONS> roundRobinOptions() {
		return this.roundRobinOptions;
	}

	public Stream<ROUND_ROBIN_OPTIONS> roundRobinOptionsAsStream() {
		return this.roundRobinOptions.stream();
	}

	private List<Leg> validLegs = new ArrayList<>();

	public List<Leg> validLegs() {
		return this.validLegs;
	}

	public Stream<Leg> validLegsAsStream() {
		return this.validLegs.stream();
	}

	private List<Leg> invalidLegs = new ArrayList<>();

	public List<Leg> invalidLegs() {
		return this.invalidLegs;
	}

	public Stream<Leg> invalidLegsAsStream() {
		return this.invalidLegs.stream();
	}

	private PlacedParlayBet(Json json) {
		this.status = json.getAsString("status").map(PLACE_PARLAY_BET_RESPONSE_STATUS::fromAPI).orElse(null);
		this.errorCode = json.getAsString("errorCode").map(PLACE_PARLAY_BET_ERROR_CODE::fromAPI);
		this.betId = json.getAsLong("betId");
		this.uniqueRequestId = json.getAsString("uniqueRequestId").orElse(null);
		this.roundRobinOptions = json.getAsStringStream("roundRobinOptions").map(ROUND_ROBIN_OPTIONS::fromAPI)
				.collect(Collectors.toList());
		this.validLegs = json.getAsJsonStream("validLegs").map(Leg::new).collect(Collectors.toList());
		this.invalidLegs = json.getAsJsonStream("invalidLegs").map(Leg::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private PlacedParlayBet() {
		this.isEmpty = true;
	}

	public static PlacedParlayBet parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new PlacedParlayBet() : new PlacedParlayBet(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null || this.uniqueRequestId == null;
	}

	@Override
	public String toString() {
		return "PlacedParlayBet [status=" + status + ", errorCode=" + errorCode + ", betId=" + betId
				+ ", uniqueRequestId=" + uniqueRequestId + ", roundRobinOptions=" + roundRobinOptions + ", validLegs="
				+ validLegs + ", invalidLegs=" + invalidLegs + "]";
	}

	public static class Leg extends AbstractDataObject {

		private PLACE_PARLAY_LEG_RESPONSE_STATUS status;

		public PLACE_PARLAY_LEG_RESPONSE_STATUS status() {
			return this.status;
		}

		private Optional<PLACE_PARLAY_LEG_ERROR_CODE> errorCode;

		public Optional<PLACE_PARLAY_LEG_ERROR_CODE> errorCode() {
			return this.errorCode;
		}

		private String legId;

		public String legId() {
			return this.legId;
		}

		private List<String> correlatedLegs = new ArrayList<>();

		public List<String> correlatedLegs() {
			return this.correlatedLegs;
		}

		public Stream<String> correlatedLegsAsStream() {
			return this.correlatedLegs.stream();
		}

		private Optional<Long> lineId;

		public Optional<Long> lineId() {
			return this.lineId;
		}

		private Optional<Long> altLineId;

		public Optional<Long> altLineId() {
			return this.altLineId;
		}

		private Optional<BigDecimal> price;

		public Optional<BigDecimal> price() {
			return this.price;
		}

		private Leg(Json json) {
			this.status = json.getAsString("status").map(PLACE_PARLAY_LEG_RESPONSE_STATUS::fromAPI).orElse(null);
			this.errorCode = json.getAsString("errorCode").map(PLACE_PARLAY_LEG_ERROR_CODE::fromAPI);
			this.legId = json.getAsString("legId").orElse(null);
			this.correlatedLegs = json.getAsStringStream("correlatedLegs").collect(Collectors.toList());
			this.lineId = json.getAsLong("lineId");
			this.altLineId = json.getAsLong("altLineId");
			this.price = json.getAsBigDecimal("price");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.legId == null;
		}

		@Override
		public String toString() {
			return "Leg [status=" + status + ", errorCode=" + errorCode + ", legId=" + legId + ", correlatedLegs="
					+ correlatedLegs + ", lineId=" + lineId + ", altLineId=" + altLineId + ", price=" + price + "]";
		}
	}
}
