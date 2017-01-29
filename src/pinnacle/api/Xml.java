package pinnacle.api;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Xml {

	/**
	 * DOM parsed from XML text.
	 */
	private Document dom;

	private Xml () {}
	
	/**
	 * Constructor parses XML text and verify if status is ok. 
	 * @param text
	 * @throws IOException
	 */
	private Xml (String text) throws PinnacleException {
		DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder bldr = fctr.newDocumentBuilder();
			InputSource insrc = new InputSource(new StringReader(text));
			this.dom = bldr.parse(insrc);
		} catch (ParserConfigurationException e) {
			throw new PinnacleException("ParserConfigurationException: " + e.getMessage());
		} catch (SAXException e) {
			throw new PinnacleException("SAXException: " + e.getMessage());
		} catch (IOException e) {
			throw new PinnacleException("Couldn't parse XML: " + e.getMessage());
		} 
	}
	
	/**
	 * New instance is created from XML text.
	 * @param text
	 * @return
	 * @throws IOException
	 */
	static Xml of (String text) throws PinnacleException {
		return text.equals("") ? new Xml() : new Xml(text).verify();
	}

	/**
	 * Tries to parse error message in JSON. Returns null if it is not error. 
	 * @param text
	 * @return
	 */
	static String parseErrorMessage (String text) {
		try {
			Xml xml = new Xml(text);
			return xml.getErrorMessage();
		} catch (NoNecessaryKeyException | PinnacleException e) { // failed to parse XML
			return null;
		}
	}
	
	/**
	 * Returns error message if the XML represents error.
	 * Or null if not. 
	 * @return
	 * @throws PinnacleException 
	 * @throws NoNecessaryKeyException 
	 */
	private String getErrorMessage () {
		String status = this.getStatus()
				.orElseThrow(() -> NoNecessaryKeyException.of("GenericException2.rsp.@status"));
		if (status.equals("fail")) {
			return this.getError();
		} else if (!status.equals("ok")) {
			return "Unknown status: " + status;
		}
		return null; // status ok
	}
	
	
	/**
	 * Verifies if the XML represents error. 
	 * @throws IOException
	 */
	private Xml verify () throws PinnacleException {
		String errorMessage = this.getErrorMessage();
		if (errorMessage != null) throw new PinnacleException(errorMessage);
		return this;
	}
	
	/**
	 * Returns status value the XML should have.
	 * @return
	 * @throws IOException
	 */
	private Optional<String> getStatus () {
		return this.streamOf("rsp")
				.findFirst()
				.map(status -> status.attr("status"))
				.orElseThrow(() -> NoNecessaryKeyException.of("GenericException2.rsp"));
	}
	
	/**
	 * Returns error code and message as combined string.
	 * @return
	 * @throws IOException
	 */
	private String getError () {
		return this.streamOf("err")
				.findFirst()
				.map(this::errorMessage)
				.orElseThrow(() -> NoNecessaryKeyException.of("GenericException2.err"));
	}

	/**
	 * Returns error messages. There are two type of error XML depending on 
	 * HTTP response code, 200 or 400+.   
	 * @param error
	 * @return
	 */
	private String errorMessage (Element error) {
		String code = error.attr("code").orElse(null);
		String description;
		if (code == null) { // 400+ <err></err><code>description</code> 
			description = this.streamOf("code")
					.findFirst()
					.map(Element::value)
					.orElse("No description.");
			code = error.value();
		} else { // 200 <err code="n">description</err>
			description = error.value();
		}
		return String.format("[Error Code: %s] %s", code, description);
	}
	
	/**
	 * Returns nodes of the tag name as Stream of <code>Element</code>.
	 * @param tagname
	 * @return
	 */
	Stream<Element> streamOf (String tagname) {
		if (this.dom == null) return Stream.empty();
		NodeList nodeList = this.dom.getElementsByTagName(tagname);
		return IntStream.range(0, nodeList.getLength())
				.mapToObj(nodeList::item)
				.map(Element::new)
				.parallel();
	}
	
	/**
	 * Class to get value or attribute easily from Node. 
	 * @author pckobo
	 */
	static class Element {
		
		private Node node;
		
		Element (Node node) {
			this.node = node;
		}
		
		/**
		 * Returns value of the attribute. 
		 * @param name
		 * @return optional
		 */
		Optional<String> attr (String name) {
			Node target = this.node.getAttributes().getNamedItem(name);
			return target == null ? Optional.empty() : Optional.of(target.getNodeValue().trim());
		}
		
		/**
		 * Returns text value of the node.
		 * 
		 * This doesn't have to be Optional because validated virtually in streamOf.
		 * If the node doesn't have any child, the stream will be empty.
		 * @return
		 */
		String value () {
			return this.node.getFirstChild().getTextContent().trim();
		}
	}
	
	// test
	public static void main(String[] args) throws PinnacleException {
		String text = "<rsp status=\"fail\"><err code=\"1\">Invalid sport ID</err></rsp>";
		//System.out.println(parseErrorMessage(text));
		Xml.of(text);
	}
}

