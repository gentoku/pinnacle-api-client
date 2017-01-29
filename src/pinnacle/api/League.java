package pinnacle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pinnacle.api.Enums.TEAM_TYPE;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;
import pinnacle.api.Xml.Element;

public class League {
	
	/**
	 * League id.
	 */
	private Integer id;
	public Integer id() { return id; }
	
	/**
	 * Name of the league.
	 */
	private String name;
	public String name () { return this.name; }
	
	/**
	 * Whether the sport currently has lines.
	 */
	private Boolean feedContents;
	public Boolean feedContents () { return this.feedContents; }
	
	/**
	 * Specifies whether the home team is team1 or team2. 
	 * You need this information to place a bet.
	 */
	private TEAM_TYPE homeTeamType;
	public TEAM_TYPE homeTeamType () { return this.homeTeamType; }; 
	
	/**
	 * Specifies whether you can place parlay round robins on events in this league.
	 */
	private Boolean allowRoundRobins;
	public Boolean allowRoundRobins () { return this.allowRoundRobins; }
	
	private League (Element element) {
		this.id = element.attr("id")
				.map(Integer::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("League.@id"));
		this.name = element.value();
		this.feedContents = element.attr("feedContents")
				.map(Enums::boolean2)
				.orElseThrow(() -> NoNecessaryKeyException.of("League.@feedContents"));
		this.homeTeamType = element.attr("homeTeamType")
				.map(TEAM_TYPE::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("League.@homeTeamType"));
		this.allowRoundRobins = element.attr("allowRoundRobins")
				.map(Enums::boolean2)
				.orElseThrow(() -> NoNecessaryKeyException.of("League.@allowRoundRobins"));
	}
	
	static List<League> parse (String xml) throws PinnacleException {
		return xml.equals("") 
				? new ArrayList<>() 
				: Xml.of(xml)
					.streamOf("league")
					.map(League::new)
					.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + ", feedContents="
				+ feedContents + ", homeTeamType=" + homeTeamType
				+ ", allowRoundRobins=" + allowRoundRobins + "]";
	} 
	
}
