package com.twgan.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomParseXML {
	public List<Object> ReadItemXML(InputStream inStream) throws Exception{
		List<Object> itemList = new ArrayList<Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inStream);  //
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("row");
		
		for(int i=0;i<nodes.getLength();i++){
			Element itemElement=(Element)nodes.item(i);
			Object obj = new Object();
			//obj.setId(itemElement.getAttribute("id"));
			//obj.setName(itemElement.getAttribute("name"));
			//obj.setPublishers(itemElement.getAttribute("publishers"));
			//obj.setPrice(itemElement.getAttribute("price"));
			
			itemList.add(obj);
		}
		
		return itemList;
	}

}
