package pinnacle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pinnacle.api.PinnacleException.NoNecessaryKeyException;
import pinnacle.api.Xml.Element;

public class Sport {

	/**
	 * Sport Id.
	 */
	private Integer id;
	public Integer id () {
		return this.id;
	} 

	/**
	 * Whether the sport currently has lines.
	 */
	private Boolean feedContents;
	public Boolean feedContents () {
		return this.feedContents;
	}

	/**
	 * Sport name.
	 */
	private String name;
	public String name () {
		return this.name;
	}
	
	private Sport (Element element) {
		this.id = element.attr("id")
				.map(Integer::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("Sport.@id"));
		this.name = element.value();
		this.feedContents = element.attr("feedContents")
				.map(Enums::boolean2)
				.orElseThrow(() -> NoNecessaryKeyException.of("Sport.@feedContents"));
	}
	
	static List<Sport> parse (String xml) throws PinnacleException {
		return xml.equals("") 
				? new ArrayList<>() 
				: Xml.of(xml)
					.streamOf("sport")
					.map(Sport::new)
					.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Sport [id=" + id + ", feedContents=" + feedContents + ", name="
				+ name + "]";
	}
}
