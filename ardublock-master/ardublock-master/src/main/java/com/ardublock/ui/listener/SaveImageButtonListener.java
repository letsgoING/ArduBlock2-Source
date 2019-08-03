package com.ardublock.ui.listener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import edu.mit.blocks.workspace.Workspace;

public class SaveImageButtonListener implements ActionListener
{
	private Workspace workspace;
	
	public SaveImageButtonListener(Workspace workspace)
	{
		this.workspace = workspace;
	}
	
	public void actionPerformed(ActionEvent e) {
		Dimension size = workspace.getCanvasSize();
		Color transparent = new Color(0, 0, 0, 0);

		//System.out.println("size: " + size);
		BufferedImage bi = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB); //letsgoING

		Graphics2D g = (Graphics2D)bi.createGraphics();
		g.setBackground(transparent);
		//double theScaleFactor = (300d/72d); 
		//g.scale(theScaleFactor,theScaleFactor);

		workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
		
		//CHANGE BACKGROUND COLOR FOR PRINT
		//*****************************************
		int maxPixelX = 0;
		int maxPixelY = 0;
		
	    for( int x = 0; x < bi.getWidth(); x++ ) {          // loop through the pixels
	        for( int y = 0; y < bi.getHeight(); y++ ) {
	            Color pixelColor = new Color(bi.getRGB(x, y));
	            if( pixelColor.getRed() == 128 && pixelColor.getGreen() == 128 && pixelColor.getBlue() == 128 ) {    
	            	bi.setRGB(x, y,transparent.getRGB());
	            }
	            else{ //GET SIZE OF BLOCKS ON IMAGE
	            	maxPixelX = Math.max(x,maxPixelX); 
					maxPixelY =  Math.max(y,maxPixelY);
	            }
	        }
	    }
	    
	    //System.out.println("Max X: " + maxPixelX +"  Max Y: " + maxPixelY +"Image W: " + bi.getWidth() +  "Image H: " +bi.getHeight());
	    
	    BufferedImage ci = bi.getSubimage(0, 0, Math.min(maxPixelX+20, bi.getWidth()), Math.min(maxPixelY+20, bi.getHeight())); 
		//*****************************************
	    
		try{
			final JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("ardublock.png"));
			int returnVal = fc.showSaveDialog(workspace.getBlockCanvas().getJComponent());
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
				ImageIO.write(ci,"png",file); //////
	        }
		} catch (Exception e1) {
			
		} finally {
			g.dispose();
		}
	}

}
