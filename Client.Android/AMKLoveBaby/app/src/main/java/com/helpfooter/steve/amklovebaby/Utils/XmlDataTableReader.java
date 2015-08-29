package com.helpfooter.steve.amklovebaby.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlDataTableReader {
	ArrayList<HashMap<String,String>> lstRow=new ArrayList<HashMap<String,String>>();
	public XmlDataTableReader(InputStream is) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(is);
			Element elmtInfo = doc.getDocumentElement();

			NodeList StartNode = elmtInfo.getChildNodes();
			Node tableNode=StartNode.item(0);
			NodeList rowNodeList=tableNode.getChildNodes();
			for (int i = 0; i < rowNodeList.getLength(); i++){

				Node rowNode = rowNodeList.item(i);
				NodeList colList= rowNode.getChildNodes();
				HashMap<String,String> dictCol=getColDict(colList);

				lstRow.add(dictCol);
 			}
		}
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		//Log.d("nodecount", String.valueOf(dictRecord.size())+"\"+);
	}

	public ArrayList<HashMap<String,String>> getDataTableValue(){
		return lstRow;
	}

	private HashMap<String,String> getColDict(NodeList nl){
		HashMap<String,String> dictRowValue=new HashMap<String,String>();
		for (int i=0;i<nl.getLength();i++){
			dictRowValue.put(nl.item(i).getNodeName(),nl.item(i).getTextContent());
		}
		return  dictRowValue;
	}

}
