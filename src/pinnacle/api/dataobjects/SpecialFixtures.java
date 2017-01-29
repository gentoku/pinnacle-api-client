package pinnacle.api.dataobjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.EVENT_STATUS;
import pinnacle.api.enums.SPECIAL_BET_TYPE;

public class SpecialFixtures extends AbstractDataObject {

	private Integer sportId;

	public Integer sportId() {
		return this.sportId;
	}

	private Long last;

	public Long last() {
		return this.last;
	}

	private List<SpecialLeague> leagues = new ArrayList<>();

	public List<SpecialLeague> leagues() {
		return this.leagues;
	}

	public Stream<SpecialLeague> leaguesAsStream() {
		return this.leagues.stream();
	}

	private SpecialFixtures(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(SpecialLeague::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private SpecialFixtures() {
		this.isEmpty = true;
	}

	public static SpecialFixtures parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new SpecialFixtures() : new SpecialFixtures(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "SpecialFixtures [sportId=" + sportId + ", last=" + last + ", leagues=" + leagues + "]";
	}

	public static class SpecialLeague extends AbstractDataObject {

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

		private SpecialLeague(Json json) {
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
			return "SpecialLeague [id=" + id + ", specials=" + specials + "]";
		}
	}

	public static class Special extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private SPECIAL_BET_TYPE betType;

		public SPECIAL_BET_TYPE betType() {
			return this.betType;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private Instant date;

		public Instant date() {
			return this.date;
		}

		private Instant cutoff;

		public Instant cutoff() {
			return this.cutoff;
		}

		private String category;

		public String category() {
			return this.category;
		}

		private Optional<String> units;

		public Optional<String> units() {
			return this.units;
		}

		private EVENT_STATUS status;

		public EVENT_STATUS status() {
			return this.status;
		}

		private List<Contestant> contestants = new ArrayList<>();

		public List<Contestant> contestants() {
			return this.contestants;
		}

		public Stream<Contestant> contestantsAsStream() {
			return this.contestants.stream();
		}

		private Special(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.betType = json.getAsString("betType").map(SPECIAL_BET_TYPE::fromAPI).orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.date = json.getAsString("date").map(Instant::parse).orElse(null);
			this.cutoff = json.getAsString("cutoff").map(Instant::parse).orElse(null);
			this.category = json.getAsString("category").orElse(null);
			this.units = json.getAsString("units");
			this.status = json.getAsString("status").map(EVENT_STATUS::fromAPI).orElse(null);
			this.contestants = json.getAsJsonStream("contestants").map(Contestant::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.betType == null || this.name == null || this.date == null
					|| this.cutoff == null || this.category == null || this.status == null;
		}

		@Override
		public String toString() {
			return "Special [id=" + id + ", betType=" + betType + ", name=" + name + ", date=" + date + ", cutoff="
					+ cutoff + ", category=" + category + ", units=" + units + ", status=" + status + ", contestants="
					+ contestants + "]";
		}
	}

	public static class Contestant extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private Integer rotNum;

		public Integer rotNum() {
			return this.rotNum;
		}

		private Contestant(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.rotNum = json.getAsInteger("rotNum").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.name == null || this.rotNum == null;
		}

		@Override
		public String toString() {
			return "Contestant [id=" + id + ", name=" + name + ", rotNum=" + rotNum + "]";
		}
	}
}
