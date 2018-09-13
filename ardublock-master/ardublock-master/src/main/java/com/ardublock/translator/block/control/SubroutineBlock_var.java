package com.ardublock.translator.block.control;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;


public class SubroutineBlock_var extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public SubroutineBlock_var(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String subroutineName = label.trim();
		String var;
		String ret;
		
		translator.addDefinitionCommand("int " + subroutineName + "(int);\n");
		
		TranslatorBlock translatorBlock_var = getTranslatorBlockAtSocket(0);
		var = translatorBlock_var.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(1);
		TranslatorBlock translatorBlock_return = getTranslatorBlockAtSocket(2);
		
		if (!(translatorBlock_var instanceof LocalVariableNumberBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_local_var_slot"));
		}
		
		ret = "int " + subroutineName + "(int "+var+")\n{\n";
		
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "return "+ translatorBlock_return.toCode().replaceAll("\\s*_.new\\b\\s*", "") +";\n }\n";
		return ret;
	}
}
