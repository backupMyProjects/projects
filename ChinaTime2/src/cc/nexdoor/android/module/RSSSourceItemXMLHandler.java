package cc.nexdoor.android.module;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSSourceItemXMLHandler extends DefaultHandler {
	
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "CTHome:RSSSourceItemXMLHandler";
	LogD log = new LogD(TAG, false);
//	private StringBuffer buf;
	
	private boolean in_channeltag = false;
	private boolean in_itemtag = false;
	private boolean in_titletag = false;
	private boolean in_linktag = false;
	
	public ArrayList channelItemAttributesList = new ArrayList(); 
	
	private String idName;
	private String key;
//	public HashMap<String, String> item = new HashMap();
	public ArrayList channelItemContentsList = new ArrayList(); 


	
	// ===========================================================
	// Override Methods
	// ===========================================================
	
	@Override
	public void startDocument() throws SAXException {
//		this.buf = new StringBuffer();
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) {
		log.d("Forward Mapping : " + prefix + " Start!" + "  URI : " + uri);
	}

	@Override
	public void endPrefixMapping(String prefix) {
		log.d("Forward Mapping : " + prefix + " end!");
	}
	

	// ===========================================================
	// Calling Methods
	// ===========================================================
	public void readRSSSourceXML(String input){
        try {
            // init reader
        	SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            // Create DefaultHandler Instance
//            RSSSourceXMLHandler xmlHandler = new RSSSourceXMLHandler();
            reader.setContentHandler(this);
            reader.setErrorHandler(this);
            // Start Analyze
            URL url = new URL(input);
        	InputSource is = new InputSource(url.openStream());
            reader.parse(is);
            
			// get info
			channelItemAttributesList = this.channelItemAttributesList;
//			for (int i = 0; i < channelAttributesList.size(); i++) {
//				HashMap<String, String> hm = (HashMap) channelAttributesList.get(i);
//				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
//				}
//			}

			// get item
			channelItemContentsList = this.channelItemContentsList;
			
//			log.d("channelItemContentsList : "+channelItemContentsList.size());
			
//			for (int i = 0; i < channelContentsList.size(); i++) {
//				HashMap<String, String> hm = (HashMap) channelContentsList.get(i);
//				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
//				}
//			}
            
            
        } catch ( IOException e ) {
            log.d("Error on Read : " + e.getMessage());
        } catch ( SAXException e ) {
            log.d("Error on Analyze : " + e.getMessage());
        } catch (ParserConfigurationException e) {
            log.d("Error on Factory : " + e.getMessage());
		} 
    }
	
	
	/* ToDo : prepare to modify 20100813 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String fullName, Attributes attributes) throws SAXException {
		// print element start
//		log.d("Element : " + "[" + localName + "]" + " Start Analyze!");
		
		
		if (localName.equals("channel")) {
			this.in_channeltag = true;
			idName = attributes.getValue("id");
		}else if (localName.equals("item")) {
			this.in_itemtag = true;
		}else if (localName.equals("title")) {
			this.in_titletag = true;
		}else if (localName.equals("link")) {
			this.in_linktag = true;
		}
		
		
		
		
		// print attribute info
//		for (int i = 0; i < attributes.getLength(); i++) {
//			log.d("Attribute : " + attributes.getLocalName(i) + " : "
//					+ attributes.getValue(i));
//		}
		
	}
	
	
	
	
	
	@Override
	public void endElement(String namespaceURI, String localName,
			String fullName) throws SAXException {


		if (localName.equals("channel")) {
			this.in_channeltag = false;
		}else if (localName.equals("item")) {
			this.in_itemtag = false;
		}else if (localName.equals("title")) {
			if(in_itemtag){
				key = idName + "_" + buf.toString().trim();
				buf.setLength(0);
				this.in_titletag = false;
			}
		}else if (localName.equals("link")) {
			if(in_itemtag){
				content = buf.toString().trim();
				buf.setLength(0);
				log.d(key + " : " + content );
				HashMap<String, String> item = new HashMap();
				item.put(key, content);
				channelItemContentsList.add(item);
				this.in_linktag = false;
			}
		} else {
			buf.setLength(0);
		}
		
		
		
		// print element end
//		log.d("Element : " + "[" + localName + "]" + " END!");
	}
	
	
	


	StringBuffer buf = new StringBuffer();
	String content;
	int flag = 0;
	@Override
	public void characters(char[] chars, int start, int length)
			throws SAXException {
		if (this.in_itemtag) {
			/* 將char[]加入StringBuffer */
			buf.append(chars, start, length);
		}
		
//		String content = "";
//		if (!buf.toString().trim().equals("")) {
//			content = buf.toString().trim();
//		}
//		buf.setLength(0);
		
//		String temp = (new String(chars, start, length));
//		
//		if (this.in_titletag) {
//			content = temp;
////			log.d("title : "+ content );
//			key = idName + "_" + content;
//		}else if(this.in_linktag){
//			
//			
////			if ( flag == 0 &&  !"&".equals(temp) ) {
////				content += temp;
////			}else if ( "&".equals(temp) ) {
////				content += temp;
////				flag = 1;
////			}else if ( flag == 1 &&  !content.equals(temp) ) {
////				content += temp;
////				flag = 0;
////			}
//			
//			if ( flag == 0 &&  !"&".equals(temp) ) {
//				content = temp;
//				flag = 1;
//			}else if ( flag == 1 && "&".equals(temp) ) {
//				content += temp;
//				flag = 2;
//			}else if ( flag == 1 && !"&".equals(temp) ) {
//				flag = 0;
//			}else if ( flag == 2 &&  !content.equals(temp) ) {
//				content += temp;
//				flag = 0;
//			}
//			
//			
//			
//			if ( flag == 0 ) {
//				log.d("key : "+ key );
//				log.d("value : "+ content );
//				item.put(key, content);
//				channelContentsList.add(item);
//			}
//		}
		
	}

	
	
	
	
	
	@Override
	public void warning(SAXParseException exception) {
		log.d("*******WARNING******");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("********************");
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		log.d("******* ERROR ******");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("********************");
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		log.d("******** FATAL ERROR ********");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("*****************************");
	}
}