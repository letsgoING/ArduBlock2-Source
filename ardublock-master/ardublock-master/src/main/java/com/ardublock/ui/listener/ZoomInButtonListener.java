package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;

import edu.mit.blocks.workspace.PageChangeEventManager;
import edu.mit.blocks.workspace.Workspace;

public class ZoomInButtonListener implements ActionListener
{
	private Workspace workspace;
	
	public ZoomInButtonListener(Workspace workspace)
	{
		Context.getContext();
		
		this.workspace = workspace;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		double zoomLevel = workspace.getCurrentWorkspaceZoom();
		
		if(zoomLevel < 2.5){
			zoomLevel += 0.1;
			workspace.setWorkspaceZoom(zoomLevel);
			PageChangeEventManager.notifyListeners();
		}
	}

}
