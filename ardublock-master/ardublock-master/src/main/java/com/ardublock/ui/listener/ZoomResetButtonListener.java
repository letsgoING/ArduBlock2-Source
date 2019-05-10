package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;

import edu.mit.blocks.workspace.PageChangeEventManager;
import edu.mit.blocks.workspace.Workspace;

public class ZoomResetButtonListener implements ActionListener
{
	private Workspace workspace;
	
	public ZoomResetButtonListener(Workspace workspace)
	{
		Context.getContext();
		
		this.workspace = workspace;
	}
	
	public void actionPerformed(ActionEvent e) {
		workspace.setWorkspaceZoomToDefault();
		PageChangeEventManager.notifyListeners();
	}

}
