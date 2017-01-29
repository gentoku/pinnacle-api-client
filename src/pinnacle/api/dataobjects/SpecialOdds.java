package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class SpecialOdds extends AbstractDataObject {

	private Integer sportId;

	public Integer sportId() {
		return this.sportId;
	}

	private Long last;

	public Long last() {
		return this.last;
	}

	private List<League> leagues = new ArrayList<>();

	public List<League> leagues() {
		return this.leagues;
	}

	public Stream<League> leaguesAsStream() {
		return this.leagues.stream();
	}

	private SpecialOdds(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private SpecialOdds() {
		this.isEmpty = true;
	}

	public static SpecialOdds parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new SpecialOdds() : new SpecialOdds(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "SpecialOdds [sportId=" + sportId + ", last=" + last + ", leagues=" + leagues + "]";
	}

	public static class League extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private List<Special> specials = new ArrayList<>();

		public List<Special> specials() {
			return this.specials;
		}

		public Stream<Special> specialsAsStream() {
			return this.specials.stream();
		}

		private League(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.specials = json.getAsJsonStream("specials").map(Special::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", specials=" + specials + "]";
		}
	}

	public static class Special extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private BigDecimal maxBet;

		public BigDecimal maxBet() {
			return this.maxBet;
		}

		private List<ContestantLine> contestantLines = new ArrayList<>();

		public List<ContestantLine> contestantLines() {
			return this.contestantLines;
		}

		public Stream<ContestantLine> contestantLinesAsStream() {
			return this.contestantLines.stream();
		}

		private Special(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.maxBet = json.getAsBigDecimal("maxBet").orElse(null);
			this.contestantLines = json.getAsJsonStream("contestantLines").map(ContestantLine::new)
					.collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.maxBet == null;
		}

		@Override
		public String toString() {
			return "Special [id=" + id + ", maxBet=" + maxBet + ", contestantLines=" + contestantLines + "]";
		}
	}

	public static class ContestantLine extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private Long lineId;

		public Long lineId() {
			return this.lineId;
		}

		private BigDecimal price;

		public BigDecimal price() {
			return this.price;
		}

		private BigDecimal handicap;

		public BigDecimal handicap() {
			return this.handicap;
		}

		private ContestantLine(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.lineId = json.getAsLong("lineId").orElse(null);
			this.price = json.getAsBigDecimal("price").orElse(null);
			this.handicap = json.getAsBigDecimal("handicap").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.lineId == null || this.price == null || this.handicap == null;
		}

		@Override
		public String toString() {
			return "ContestantLine [id=" + id + ", lineId=" + lineId + ", price=" + price + ", handicap=" + handicap
					+ "]";
		}
	}
}
