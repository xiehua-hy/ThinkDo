package com.thinkdo.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

public class XmlInit {
    //	private String path1="CONFIG.xml";
//	private String path2="resource_3d.xml";
    private String path3 = "resourceString.xml";
    //	private static Document doc1,doc2;
    private static Document doc3;

    public XmlInit(Context context) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            try {
                doc3 = builder.parse(context.getAssets().open(path3));
            } catch (IOException e) {
                Log.d("TAG", "xml解析器创建失败");
            }
        } catch (ParserConfigurationException e) {
            Log.d("TAG", "xml解析器创建失败");
        } catch (SAXException e) {
            Log.d("TAG", "xml文件读写失败");
        }
    }

//	public static String getConfigNodeValue(String nodeKey){
//		NodeList nodeList = doc1.getElementsByTagName(nodeKey);
//		if(nodeList!=null&&nodeList.item(0).getFirstChild()!=null){
//			return nodeList.item(0).getFirstChild().getNodeValue();
//		}
//		return null;
//	}
//	
//	public static String getResource_3dNodeValue(String parentKey,String nodeKey){
//		NodeList parent = doc2.getElementsByTagName(parentKey);
//		if(parent!=null){
//			for(int i=0;i<parent.getLength();i++){
//				Node node = parent.item(0);
//				if(node.getNodeName()==nodeKey){
//					if(node.getFirstChild()!=null)return node.getFirstChild().getNodeValue();
//					break;
//				}
//			}
//		}
//		return null;
//	}

    public static String getResourceNodeValue(String nodeKey) {
        if (doc3 != null) {
            NodeList nodeList = doc3.getElementsByTagName(nodeKey);
            if (nodeList != null && nodeList.getLength() != 0 && nodeList.item(0).getFirstChild() != null) {
                return nodeList.item(0).getFirstChild().getNodeValue().trim();
            }
        }
        return null;
    }

}
