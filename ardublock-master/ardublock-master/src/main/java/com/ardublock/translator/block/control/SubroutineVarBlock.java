package com.ardublock.translator.block.control;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableCharBlock;
import com.ardublock.translator.block.numbers.LocalVariableDigitalBlock;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;


public class SubroutineVarBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public SubroutineVarBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String subroutineName = label.trim();
		String var;
		String ret = "";
		
		TranslatorBlock tb_commentBlock = getRequiredTranslatorBlockAtSocket(0);
		ret += "//"+tb_commentBlock.toCode().replaceAll("\"", "")+"\n";
		
		TranslatorBlock translatorBlock_var = getRequiredTranslatorBlockAtSocket(1);
		var = translatorBlock_var.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(2);
				
		if (translatorBlock_var instanceof LocalVariableNumberBlock) {
			translator.addDefinitionCommand("int " + subroutineName + "(int);\n");
			ret += "int " + subroutineName + "(int "+var+")\n{\n";
		}
		else if(translatorBlock_var instanceof LocalVariableDigitalBlock){
			translator.addDefinitionCommand("bool " + subroutineName + "(bool);\n");
			ret += "bool " + subroutineName + "(bool "+var+")\n{\n";
		}
		else if(translatorBlock_var instanceof LocalVariableCharBlock){
			translator.addDefinitionCommand("char " + subroutineName + "(char);\n");
			ret += "char " + subroutineName + "(char "+var+")\n{\n";
			
		}else{
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.local_var_slot"));
		}
		
		
		while (translatorBlock != null)
		{
			ret += translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret += "}\n\n";
		return ret;
	}
}
