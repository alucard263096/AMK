package com.helpfooter.steve.amklovebaby.Utils;

import java.util.Dictionary;
import java.util.Hashtable;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class XmlReader {
	Dictionary<String,Node> dictRecord=new Hashtable<String,Node>();
	Dictionary<String,String> dictAttribute=new Hashtable<String,String>();
	public XmlReader(NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++)
        {
            Node result = nodes.item(i);
            if (result.getNodeType() == Node.ELEMENT_NODE )
            {
                //Log.d("nodename", result.getNodeName());
            	dictRecord.put(result.getNodeName(), result);
            }
        }
		//Log.d("nodecount", String.valueOf(dictRecord.size())+"\"+);
	}
	
	public static void ShowLog(NodeList nodes){
		for (int i = 0; i < nodes.getLength(); i++)
        {
            Node result = nodes.item(i);
            if (result.getNodeType() == Node.ELEMENT_NODE )
            {
                Log.d("nodename", result.getNodeName()+"|"+result.getTextContent());
            }
        }
	}
	
	public Node getNode(String key){
		return dictRecord.get(key);
	}
	public String getText(String key){
		//Log.d("getText", key);
		String ret=getNode(key).getTextContent();
		//Log.d("getTextvalue", ret);
		return ret;
	}
	public String getAttribute(String key,String type){
		NamedNodeMap attributes= getNode(key).getAttributes();
		return GetAttribute(getNode(key),type);
	}
	public static String GetAttribute(Node node,String type){
		NamedNodeMap attributes= node.getAttributes();
		for(int i=0;i<attributes.getLength();i++){  
			   Node attribute=attributes.item(i);  
			   if(attribute.getNodeName()==type){
				   return attribute.getNodeValue();
			   }
			  }
		return "";
	}
}
