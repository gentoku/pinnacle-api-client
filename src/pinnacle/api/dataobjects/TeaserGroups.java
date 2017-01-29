package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class TeaserGroups extends AbstractDataObject {

	private List<TeaserGroup> teaserGroups = new ArrayList<>();

	public List<TeaserGroup> teaserGroups() {
		return this.teaserGroups;
	}

	public Stream<TeaserGroup> teaserGroupsAsStream() {
		return this.teaserGroups.stream();
	}

	private TeaserGroups(Json json) {
		this.teaserGroups = json.getAsJsonStream("teaserGroups").map(TeaserGroup::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private TeaserGroups() {
		this.isEmpty = true;
	}

	public static TeaserGroups parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new TeaserGroups() : new TeaserGroups(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "TeaserGroups [teaserGroups=" + teaserGroups + "]";
	}

	public static class TeaserGroup extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private List<Teaser> teasers = new ArrayList<>();

		public List<Teaser> teasers() {
			return this.teasers;
		}

		public Stream<Teaser> teasersAsStream() {
			return this.teasers.stream();
		}

		private TeaserGroup(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.teasers = json.getAsJsonStream("teasers").map(Teaser::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.name == null;
		}

		@Override
		public String toString() {
			return "TeaserGroup [id=" + id + ", name=" + name + ", teasers=" + teasers + "]";
		}
	}

	public static class Teaser extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private String description;

		public String description() {
			return this.description;
		}

		private Integer sportId;

		public Integer sportId() {
			return this.sportId;
		}

		private Integer minLegs;

		public Integer minLegs() {
			return this.minLegs;
		}

		private Integer maxLegs;

		public Integer maxLegs() {
			return this.maxLegs;
		}

		private Boolean sameEventOnly;

		public Boolean sameEventOnly() {
			return this.sameEventOnly;
		}

		private List<Payout> payouts = new ArrayList<>();

		public List<Payout> payouts() {
			return this.payouts;
		}

		public Stream<Payout> payoutsAsStream() {
			return this.payouts.stream();
		}

		private List<League> league = new ArrayList<>();

		public List<League> league() {
			return this.league;
		}

		public Stream<League> leagueAsStream() {
			return this.league.stream();
		}

		private Teaser(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.description = json.getAsString("description").orElse(null);
			this.sportId = json.getAsInteger("sportId").orElse(null);
			this.minLegs = json.getAsInteger("minLegs").orElse(null);
			this.maxLegs = json.getAsInteger("maxLegs").orElse(null);
			this.sameEventOnly = json.getAsBoolean("sameEventOnly").orElse(null);
			this.payouts = json.getAsJsonStream("payouts").map(Payout::new).collect(Collectors.toList());
			this.league = json.getAsJsonStream("league").map(League::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.description == null || this.sportId == null || this.minLegs == null
					|| this.maxLegs == null || this.sameEventOnly == null;
		}

		@Override
		public String toString() {
			return "Teaser [id=" + id + ", description=" + description + ", sportId=" + sportId + ", minLegs=" + minLegs
					+ ", maxLegs=" + maxLegs + ", sameEventOnly=" + sameEventOnly + ", payouts=" + payouts + ", league="
					+ league + "]";
		}
	}

	public static class Payout extends AbstractDataObject {

		private Integer numberOfLegs;

		public Integer numberOfLegs() {
			return this.numberOfLegs;
		}

		private BigDecimal price;

		public BigDecimal price() {
			return this.price;
		}

		private Payout(Json json) {
			this.numberOfLegs = json.getAsInteger("numberOfLegs").orElse(null);
			this.price = json.getAsBigDecimal("price").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.numberOfLegs == null || this.price == null;
		}

		@Override
		public String toString() {
			return "Payout [numberOfLegs=" + numberOfLegs + ", price=" + price + "]";
		}
	}

	public static class League extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private Spread spread;

		public Spread spread() {
			return this.spread;
		}

		private Optional<Total> total;

		public Optional<Total> total() {
			return this.total;
		}

		private League(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.spread = json.getAsJson("spread").map(Spread::new).orElse(null);
			this.total = json.getAsJson("total").map(Total::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.spread == null;
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", spread=" + spread + ", total=" + total + "]";
		}
	}

	public static class Spread extends AbstractDataObject {

		private BigDecimal points;

		public BigDecimal points() {
			return this.points;
		}

		private Spread(Json json) {
			this.points = json.getAsBigDecimal("points").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.points == null;
		}

		@Override
		public String toString() {
			return "Spread [points=" + points + "]";
		}
	}

	public static class Total extends AbstractDataObject {

		private BigDecimal points;

		public BigDecimal points() {
			return this.points;
		}

		private Total(Json json) {
			this.points = json.getAsBigDecimal("points").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.points == null;
		}

		@Override
		public String toString() {
			return "Total [points=" + points + "]";
		}
	}
}
