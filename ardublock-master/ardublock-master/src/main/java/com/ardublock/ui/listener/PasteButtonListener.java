package com.ardublock.ui.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import edu.mit.blocks.controller.WorkspaceController;

public class PasteButtonListener implements ActionListener
{

	private Context context;
	private ResourceBundle uiMessageBundle;
	private OpenblocksFrame openBlocksFrame;
	
	public PasteButtonListener(OpenblocksFrame openBlocksFrame, Context context, ResourceBundle uiMessageBundle, boolean workspaceExpertState)
	{
		this.context = context;
		this.uiMessageBundle = uiMessageBundle;
		this.openBlocksFrame = openBlocksFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		String pasteProject = "";
		String currentProject = "";
		String mergedProgram = "";
		Boolean pasteDone = false;
		int maxBlockId = 0;
		
		NodeList currentList = null;
		
		WorkspaceController workspaceController = context.getWorkspaceController();
		currentProject = workspaceController.getSaveString();
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    DataFlavor flavor = DataFlavor.stringFlavor;
	    if (clipboard.isDataFlavorAvailable(flavor)) {
	      try {
	    	pasteProject = (String) clipboard.getData(flavor);
	        //System.out.println(pasteProject);
	      } catch (UnsupportedFlavorException e1) {
	        System.out.println(e1);
	      } catch (IOException e2) {
	        System.out.println(e2);
	      }
	    }
	    
	    try{
	    	//CURRENT PROGRAM
	    	DocumentBuilderFactory currentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder currentBuilder = currentFactory.newDocumentBuilder();
	        InputSource currentSource = new InputSource(new StringReader(currentProject));
	        Document currentProgramXML = currentBuilder.parse(currentSource);
	        currentProgramXML.getDocumentElement().normalize();
	        
	        try {
				currentList = currentProgramXML.getElementsByTagName("Block");
				for (int temp = 0; temp < currentList.getLength(); temp++) {
		            Node nNode = currentList.item(temp);
		            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
			        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			              Element eElement = (Element) nNode;
			              //System.out.print(eElement.getAttribute("genus-name"));
			              maxBlockId = Math.max(maxBlockId, Integer.parseInt(eElement.getAttribute("id"))); //TOINT
			              //System.out.println("MAX:"+ maxBlockId);
			        }
		        }
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
	       			        
	        //PASTE PROGRAM
	        if(pasteProject.trim().startsWith("<") && pasteProject.trim().endsWith(">") && pasteProject.contains("?xml")){
		        DocumentBuilderFactory pasteFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder pasteBuilder = pasteFactory.newDocumentBuilder();
		        InputSource pasteSource = new InputSource(new StringReader(pasteProject));
		        Document pasteProgramXML = null;
		        
				try {
					pasteProgramXML = pasteBuilder.parse(pasteSource);
					pasteProgramXML.getDocumentElement().normalize();
				} catch (Exception e1) {
					// e1.printStackTrace();
				}
		        	        
		        //TODO: ADD catch error messages

		        //System.out.println("Root element :" + pasteProgramXML.getDocumentElement().getNodeName());
		        NodeList pasteList = pasteProgramXML.getElementsByTagName("Block");
		        //System.out.println("----------------------------");
		        
		        for (int temp = 0; temp < pasteList.getLength(); temp++) {
		            Node nNode = pasteList.item(temp);
		            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
			        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			              Element eElement = (Element) nNode;
			              //System.out.println("Old ID: "+ eElement.getAttribute("id"));
			              //SET NEW ID
			              eElement.setAttribute("id", Integer.toString(Integer.parseInt(eElement.getAttribute("id")) + maxBlockId));
			             
			              //SET NEW BEFORE_ID
			              Element beforeBlockID = (Element) eElement.getElementsByTagName("BeforeBlockId").item(0);
			              if(beforeBlockID != null){
			            	  beforeBlockID.setTextContent(Integer.toString(Integer.parseInt(beforeBlockID.getTextContent())+maxBlockId));
			            	  //System.out.println("New BeforeId: "+ beforeBlockID.getTextContent().toString());
			              }
			              //SET NEW AFTER_ID 
			              Element afterBlockID = (Element) eElement.getElementsByTagName("AfterBlockId").item(0);
			              if(afterBlockID != null){
			            	  afterBlockID.setTextContent(Integer.toString(Integer.parseInt(afterBlockID.getTextContent())+maxBlockId));
			            	  //System.out.println("New AfterId: "+ afterBlockID.getTextContent().toString());
			              }
			              
			              NodeList blockConnectorList = eElement.getElementsByTagName("BlockConnector");
			              for (int temp2 = 0; temp2 < blockConnectorList.getLength(); temp2++) {
			               	  Element blockConnector = (Element) blockConnectorList.item(temp2);
			            	  if(!blockConnector.getAttribute("con-block-id").isEmpty()){
			            		  Integer  newInt = Integer.parseInt(blockConnector.getAttribute("con-block-id")) + maxBlockId;
			            		  //System.out.println("NewID:"+newInt);
			            		  blockConnector.setAttribute("con-block-id", Integer.toString(newInt));
			            		  //System.out.println("New BlockCon Id: "+ blockConnector.getAttribute("con-block-id").toString());
			            	  }
			              }
			              //System.out.print(eElement.getAttribute("genus-name"));
			              //System.out.println("New ID: "+ eElement.getAttribute("id"));
			              //ADD ELEMENT TO CURRENTXML
			              
			              try {
							Node superNode = currentProgramXML.getElementsByTagName("PageBlocks").item(0);
							  Node pNode = currentProgramXML.importNode(nNode, true);
							  superNode.appendChild(pNode);
						} catch (Exception e1) {
							System.out.println(uiMessageBundle.getString("ardublock.error_msg.paste.notpossible"));
							//e1.printStackTrace();
						}
			              
			        }
		        }
		        //System.out.println("Merged program: "+currentProgramXML.toString());
		        try {
			        StringWriter sw = new StringWriter();
			        TransformerFactory tf = TransformerFactory.newInstance();
			        Transformer transformer = tf.newTransformer();
			        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			        transformer.transform(new DOMSource(currentProgramXML), new StreamResult(sw));
			        mergedProgram = sw.toString();
			        //System.out.println("Merged program: "+mergedProgram);
			        pasteDone = true;
			    } catch (Exception ex) {
			        //throw new RuntimeException("Error converting to String", ex);
			    }
	        }else{	
	        	System.out.println(uiMessageBundle.getString("ardublock.error_msg.paste.invalid"));
	        }
		        
		} catch (Exception e1) {
		 	//e1.printStackTrace();
		}
	   
		try {
			if(pasteDone){
		        if(openBlocksFrame.getModeState()){
		        	workspaceController.loadProject(mergedProgram, null , "custom");	
		        }
		        else{
		        	workspaceController.loadProject(mergedProgram, null , "default");
		        }
			}
	        
        } catch (Exception e1) {
            //e1.printStackTrace();
        }
		//System.out.println("paste is executed");
	}
	


}
