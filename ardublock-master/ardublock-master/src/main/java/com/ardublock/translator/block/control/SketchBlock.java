package com.ardublock.translator.block.control;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.arrays.SetterCharArrayBlock;
import com.ardublock.translator.block.arrays.SetterNumberArrayBlock;
import com.ardublock.translator.block.code.CommentBlock;
import com.ardublock.translator.block.code.CodeBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


@SuppressWarnings("unused")
public class SketchBlock extends TranslatorBlock
{
	private List<String> setupCommand;
	private List<String> headerCommand;
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SketchBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
		setupCommand = new LinkedList<String>();
		headerCommand = new LinkedList<String>();
	}
	
	

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	    String ret="";
	    
	    TranslatorBlock headerBlocks = getTranslatorBlockAtSocket(0);
		while (headerBlocks != null)
		{
			ret = headerBlocks.toCode();
			headerBlocks = headerBlocks.nextTranslatorBlock();
			
			/*if(!ret.contentEquals("")){ //remove empty commands //TODO: select suitable Blocks  && (headerBlocks instanceof CodeLoopBlock || headerBlocks instanceof CodeCommentBlock || headerBlocks instanceof SetterNumberArrayBlock || headerBlocks instanceof SetterCharArrayBlock)
				this.headerCommand.add(ret);
			}else{
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.wrong_block_header"));
			}*/
		}
	    
		TranslatorBlock setupBlocks = getTranslatorBlockAtSocket(1);
		while (setupBlocks != null)
		{
			ret = setupBlocks.toCode();
			setupBlocks = setupBlocks.nextTranslatorBlock();
			if(!ret.contentEquals("")){ //remove empty commands e.g. definition commands
				this.setupCommand.add(ret);
			}
		}
		
		translator.registerBodyTranslateFinishCallback(this);

		ret="";
		ret = "void loop() {\n";
		TranslatorBlock loopBlocks = getTranslatorBlockAtSocket(2);
		while (loopBlocks != null)
		{
			ret = ret + loopBlocks.toCode();
			loopBlocks = loopBlocks.nextTranslatorBlock();
		}
	
		ret = ret + "}\n\n";
		return ret;
	}
	
	@Override
	public void onTranslateBodyFinished()
	{
		for (String command : headerCommand)
		{
			translator.addDefinitionCommand(command);
		}
		
		for (String command : setupCommand)
		{
			translator.addSetupCommandForced(command);
		}
	}
	
}
