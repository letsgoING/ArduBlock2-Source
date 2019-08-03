package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.mit.blocks.workspace.PageChangeEventManager;
import edu.mit.blocks.workspace.Workspace;

public class ZoomOutButtonListener implements ActionListener
{
	private Workspace workspace;
	
	public ZoomOutButtonListener(Workspace workspace)
	{
		
		this.workspace = workspace;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		double zoomLevel = workspace.getCurrentWorkspaceZoom();
		
		if(zoomLevel > 0.6){
			zoomLevel -= 0.1;
			workspace.setWorkspaceZoom(zoomLevel);
			PageChangeEventManager.notifyListeners();
		}
	}

}
