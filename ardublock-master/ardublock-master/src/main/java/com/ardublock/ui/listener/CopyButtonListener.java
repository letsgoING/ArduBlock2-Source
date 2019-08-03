package com.ardublock.ui.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.ardublock.core.Context;
import edu.mit.blocks.controller.WorkspaceController;

public class CopyButtonListener implements ActionListener
{

	private Context context;
	
	public CopyButtonListener(Context context)
	{
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		WorkspaceController workspaceController = context.getWorkspaceController();
		StringSelection stringSelection = new StringSelection(workspaceController.getSaveString());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		//System.out.println("copy is executed");
	}
	


}
