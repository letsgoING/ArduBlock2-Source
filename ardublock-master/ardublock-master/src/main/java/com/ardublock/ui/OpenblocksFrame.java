package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ardublock.core.Context;
import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.CopyButtonListener;
import com.ardublock.ui.listener.NewButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import com.ardublock.ui.listener.PasteButtonListener;
import com.ardublock.ui.listener.SaveAsButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;
import com.ardublock.ui.listener.SaveImageButtonListener;
//import com.ardublock.ui.listener.ZoomInButtonListener;
//import com.ardublock.ui.listener.ZoomOutButtonListener;
//import com.ardublock.ui.listener.ZoomResetButtonListener;


import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;
import edu.mit.blocks.workspace.ZoomSlider;


public class OpenblocksFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841155965906223806L;

	private Context context;
	private JFileChooser fileChooser;
	private FileFilter ffilter;
	
	private boolean workspaceModeState = false;
	
	private ResourceBundle uiMessageBundle;
	
	public void addListener(OpenblocksFrameListener ofl)
	{
		context.registerOpenblocksFrameListener(ofl);
	}
	
	public String makeFrameTitle()
	{
		String title = Context.APP_NAME + " " + context.getSaveFileName();
		if (context.isWorkspaceChanged())
		{
			title = title + " *";
		}
		return title;
	}

	public OpenblocksFrame()
	{
		context = Context.getContext();
		this.setTitle(makeFrameTitle());
		this.setSize(new Dimension(1024, 760));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
		
		fileChooser = new JFileChooser();
		ffilter = new FileNameExtensionFilter(uiMessageBundle.getString("ardublock.file.suffix"), "abp");
		fileChooser.setFileFilter(ffilter);
		fileChooser.addChoosableFileFilter(ffilter);
		
		initOpenBlocks();
		
	}
	
	private void initOpenBlocks()
	{
		final Context context = Context.getContext();
		
		/*
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspaceComponent = workspaceController.getWorkspacePanel();
		*/
		
		final Workspace workspace = context.getWorkspace();
		
		// WTF I can't add workspacelistener by workspace controller
		workspace.addWorkspaceListener(new ArdublockWorkspaceListener(this));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		JButton newButton = new JButton(uiMessageBundle.getString("ardublock.ui.new"));
		ActionListener newButtonListener = new NewButtonListener(this);	
		newButton.addActionListener(newButtonListener);		
		KeyStroke ctrlnKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		newButton.registerKeyboardAction(newButtonListener, ctrlnKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		newButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.new.tooltip"));
		
		JButton saveButton = new JButton(uiMessageBundle.getString("ardublock.ui.save"));
		ActionListener saveButtonListener = new SaveButtonListener(this);
		saveButton.addActionListener(saveButtonListener);
		KeyStroke ctrlsKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		saveButton.registerKeyboardAction(saveButtonListener, ctrlsKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		saveButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.save.tooltip"));
		
		JButton saveAsButton = new JButton(uiMessageBundle.getString("ardublock.ui.saveAs"));
		ActionListener saveAsButtonListener = new SaveAsButtonListener(this);	
		saveAsButton.addActionListener(saveAsButtonListener);
		//KeyStroke ctrlshiftsKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK);
		//saveAsButton.registerKeyboardAction(saveAsButtonListener, ctrlshiftsKeyStroke, JComponent.WHEN_FOCUSED);
		saveAsButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.saveAs.tooltip"));
		
		JButton openButton = new JButton(uiMessageBundle.getString("ardublock.ui.load"));
		ActionListener openButtonListener = new OpenButtonListener(this);
		openButton.addActionListener(openButtonListener);
		KeyStroke ctrloKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		openButton.registerKeyboardAction(openButtonListener, ctrloKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		openButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.load.tooltip"));
		
		JButton generateButton = new JButton(uiMessageBundle.getString("ardublock.ui.upload"));
		ActionListener generateButtonListener = new GenerateCodeButtonListener(this, context);
		generateButton.addActionListener(generateButtonListener);
		KeyStroke ctrluKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK);
		generateButton.registerKeyboardAction(generateButtonListener, ctrluKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		generateButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.upload.tooltip"));
		
		JButton serialMonitorButton = new JButton(uiMessageBundle.getString("ardublock.ui.serialMonitor"));
		ActionListener serialButtonListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				context.getEditor().handleSerial();
			}
		};
		serialMonitorButton.addActionListener(serialButtonListener);
		KeyStroke ctrlmKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK);
		serialMonitorButton.registerKeyboardAction(serialButtonListener, ctrlmKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		serialMonitorButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.serialMonitor.tooltip"));
		
		topPanel.add(newButton);
		topPanel.add(saveButton);
		topPanel.add(saveAsButton);
		topPanel.add(openButton);
		topPanel.add(generateButton);
		topPanel.add(serialMonitorButton);
		
		//SAVE IMAGE BUTTON
		//*****************************************
		JPanel bottomPanel = new JPanel();
		JButton saveImageButton = new JButton(uiMessageBundle.getString("ardublock.ui.saveImage"));
		ActionListener saveImageButtonListener = new SaveImageButtonListener(workspace);
		saveImageButton.addActionListener(saveImageButtonListener);
		KeyStroke ctrlpKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		saveImageButton.registerKeyboardAction(saveImageButtonListener, ctrlpKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		saveImageButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.saveImage.tooltip"));
		//*****************************************
		
		//LMS SITE BUTTON
		//*****************************************
		JButton lmssiteButton = new JButton(uiMessageBundle.getString("ardublock.ui.lmssite"));
		lmssiteButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			        	url = new URL(uiMessageBundle.getString("ardublock.ui.lmssite.domain"));
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            //e1.printStackTrace();
			        }
			    }
			}
		});
		lmssiteButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.lmssite.tooltip"));
		//*****************************************
		
		//BLOCK REFERENCE BUTTON
		//*****************************************
		JButton blockreferenceButton = new JButton(uiMessageBundle.getString("ardublock.ui.blockReference"));
		ActionListener blockreferenceButtonListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			        	url = new URL(uiMessageBundle.getString("ardublock.ui.blockReference.domain"));
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            //e1.printStackTrace();
			        }
			    }
			}
		};
		blockreferenceButton.addActionListener(blockreferenceButtonListener);
		blockreferenceButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.blockReference.tooltip"));
		//*****************************************
		
		// VERSION LABEL
		//*****************************************
		JLabel versionLabel = new JLabel(uiMessageBundle.getString("ardublock.ui.version"));
		//*****************************************
		
		//ADD ZOOM 
		//*****************************************
		ZoomSlider zoomSlider = new ZoomSlider(workspace);
		zoomSlider.reset();
		/*
		JButton zoomOutButton = new JButton(uiMessageBundle.getString("ardublock.ui.zoomOut"));
		ActionListener zoomOutButtonListener = new ZoomOutButtonListener(workspace);
		zoomOutButton.addActionListener(zoomOutButtonListener);
		KeyStroke ctrlMinusKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK);
		zoomOutButton.registerKeyboardAction(zoomOutButtonListener, ctrlMinusKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		zoomOutButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.zoomOut.tooltip"));
		
		JButton zoomInButton = new JButton(uiMessageBundle.getString("ardublock.ui.zoomIn"));
		ActionListener zoomInButtonListener = new ZoomInButtonListener(workspace);
		zoomInButton.addActionListener(zoomInButtonListener);
		KeyStroke ctrlPlusKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK);
		zoomInButton.registerKeyboardAction(zoomInButtonListener, ctrlPlusKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		zoomInButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.zoomIn.tooltip"));
		
		JButton zoomResetButton = new JButton(uiMessageBundle.getString("ardublock.ui.zoomReset"));
		ActionListener zoomResetButtonListener = new ZoomResetButtonListener(workspace);
		zoomResetButton.addActionListener(zoomResetButtonListener);
		KeyStroke ctrl0KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK);
		zoomResetButton.registerKeyboardAction(zoomResetButtonListener, ctrl0KeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		zoomResetButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.zoomReset.tooltip"));
		*/
		//*****************************************	
	
		//SWITCH BLOCK MENU EXPERT/STANDARD 
		//*****************************************
		JButton modeButton = new JButton(uiMessageBundle.getString("ardublock.ui.modeButton.mode.expert"));
		ActionListener modeButtonListener = new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			        	WorkspaceController workspaceController = context.getWorkspaceController();
				        if(!workspaceModeState){
				        	workspaceController.loadProject(getArduBlockString(), null , "custom");	
				        	modeButton.setText(uiMessageBundle.getString("ardublock.ui.modeButton.mode.standard"));
				        }
				        else{
				        	workspaceController.loadProject(getArduBlockString(), null , "default");
				        	modeButton.setText(uiMessageBundle.getString("ardublock.ui.modeButton.mode.expert"));
				        }
				        zoomSlider.reset();
				        workspaceModeState=!workspaceModeState;
				        
			        } catch (Exception e1) {
			            //e1.printStackTrace();
			        }
			    }
			}
		};
		modeButton.addActionListener(modeButtonListener);
		KeyStroke ctrleKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
		modeButton.registerKeyboardAction(modeButtonListener, ctrleKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		modeButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.modeButton.mode.tooltip"));
		//*****************************************
		
		//COPY/PASTE BUTTONS
		//*****************************************
		JButton copyButton = new JButton(uiMessageBundle.getString("ardublock.ui.copy"));
		ActionListener copyButtonListener = new CopyButtonListener(context);
		copyButton.addActionListener(copyButtonListener);
		KeyStroke ctrlcKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
		copyButton.registerKeyboardAction(copyButtonListener, ctrlcKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		copyButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.copy.tooltip"));
		
		
		JButton pasteButton = new JButton(uiMessageBundle.getString("ardublock.ui.paste"));
		ActionListener pasteButtonListener = new PasteButtonListener(this, context, uiMessageBundle, workspaceModeState);
		pasteButton.addActionListener(pasteButtonListener);
		KeyStroke ctrlvKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
		pasteButton.registerKeyboardAction(pasteButtonListener, ctrlvKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		pasteButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.paste.tooltip"));
		//*****************************************
		
		//WEBSITE BUTTON
		//*****************************************
		JButton websiteButton = new JButton(uiMessageBundle.getString("ardublock.ui.website"));
		websiteButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    URL url;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			        	url = new URL(uiMessageBundle.getString("ardublock.ui.website.domain"));
			            desktop.browse(url.toURI());
			        } catch (Exception e1) {
			            //e1.printStackTrace();
			        }
			    }
			}
		});
		websiteButton.setToolTipText(uiMessageBundle.getString("ardublock.ui.website.tooltip"));
		//*****************************************
		
		bottomPanel.add(websiteButton);
		//bottomPanel.add(blockreferenceButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(15, 0))); //std value 30,0
		bottomPanel.add(saveImageButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(15, 0))); //std value 30,0
		bottomPanel.add(zoomSlider);
		//bottomPanel.add(zoomInButton);
		//bottomPanel.add(zoomResetButton);
		//bottomPanel.add(zoomOutButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(15, 0))); //std value 30,0
		bottomPanel.add(versionLabel);
		bottomPanel.add(Box.createRigidArea(new Dimension(15, 0))); //std value 30,0
		bottomPanel.add(copyButton);
		bottomPanel.add(pasteButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(15, 0))); //std value 30,0
		bottomPanel.add(modeButton);
		//bottomPanel.add(lmssiteButton);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.add(workspace, BorderLayout.CENTER);
	}
	
	public void doOpenArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.open_unsaved"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			if (optionValue == JOptionPane.YES_OPTION)
			{
				doSaveArduBlockFile();
				this.loadFile();
			}
			else
			{
				if (optionValue == JOptionPane.NO_OPTION)
				{
					this.loadFile();
				}
			}
		}
		else
		{
			this.loadFile();
		}
		this.setTitle(makeFrameTitle());
	}
	
	private void loadFile()
	{
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File savedFile = fileChooser.getSelectedFile();
			if (!savedFile.exists())
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				return ;
			}
			
			try
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				context.loadArduBlockFile(savedFile);	
				context.setWorkspaceChanged(false);
			}
			catch (IOException e)
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				//e.printStackTrace();
			}
			finally
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		//set Menu Mode letsgoING
		try {
	        if(workspaceModeState){
	        	context.getWorkspaceController().loadProject(getArduBlockString(), null , "custom");	
	        }
        } catch (Exception e1) {
            //e1.printStackTrace();
        }
		
	}
	
	public void doSaveArduBlockFile()
	{
		if (!context.isWorkspaceChanged())
		{
			return ;
		}
		
		String saveString = getArduBlockString();
		
		if (context.getSaveFilePath() == null)
		{
			chooseFileAndSave(saveString);
		}
		else
		{
			File saveFile = new File(context.getSaveFilePath());
			writeFileAndUpdateFrame(saveString, saveFile);
		}
	}
	
	public void doSaveAsArduBlockFile()
	{
		if (context.isWorkspaceEmpty())
		{
			return ;
		}
		
		String saveString = getArduBlockString();
		
		chooseFileAndSave(saveString);
		
	}
	
	private void chooseFileAndSave(String ardublockString)
	{
		File saveFile = letUserChooseSaveFile();
		saveFile = checkFileSuffix(saveFile);
		if (saveFile == null)
		{
			return ;
		}
		
		if (saveFile.exists() && !askUserOverwriteExistedFile())
		{
			return ;
		}
		
		writeFileAndUpdateFrame(ardublockString, saveFile);
	}
	
	private String getArduBlockString()
	{
		WorkspaceController workspaceController = context.getWorkspaceController();
		return workspaceController.getSaveString();
	}
	
	private void writeFileAndUpdateFrame(String ardublockString, File saveFile) 
	{
		try
		{
			saveArduBlockToFile(ardublockString, saveFile);
			context.setWorkspaceChanged(false);
			this.setTitle(this.makeFrameTitle());
		}
		catch (IOException e)
		{
			//e.printStackTrace();
		}
		
	}
	
	private File letUserChooseSaveFile()
	{
		int chooseResult;
		chooseResult = fileChooser.showSaveDialog(this);
		if (chooseResult == JFileChooser.APPROVE_OPTION)
		{
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	private boolean askUserOverwriteExistedFile()
	{
		int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
		return (optionValue == JOptionPane.YES_OPTION);
	}
	
	private void saveArduBlockToFile(String ardublockString, File saveFile) throws IOException
	{
		context.saveArduBlockFile(saveFile, ardublockString);
		context.setSaveFileName(saveFile.getName());
		context.setSaveFilePath(saveFile.getAbsolutePath());
	}
	
	public void doNewArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.newfile_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			if (optionValue != JOptionPane.YES_OPTION)
			{
				return ;
			}
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		context.resetWorkspace();
		context.setWorkspaceChanged(false);
		this.setTitle(this.makeFrameTitle());
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		//set Menu Mode letsgoING
		try {
		  if(workspaceModeState){
			  context.getWorkspaceController().loadProject(getArduBlockString(), null , "custom");	 
		  }
		} catch (Exception e1) {
			//e1.printStackTrace();
		}

	}
	
	private File checkFileSuffix(File saveFile)
	{
		String filePath = saveFile.getAbsolutePath();
		if (filePath.endsWith(".abp"))
		{
			return saveFile;
		}
		else
		{
			return new File(filePath + ".abp");
		}
	}

	@SuppressWarnings("unused")
	private void resetModeState(){ //letsgoING
		workspaceModeState = false; 
	}
	
	public boolean getModeState(){ //letsgoING
		return workspaceModeState; 
	}

}
