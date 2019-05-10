package com.ardublock.translator.block.control;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SubroutineBlock_legacy extends TranslatorBlock
{

	public SubroutineBlock_legacy(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		String subroutineName = label.trim();
		translator.addDefinitionCommand("void " + subroutineName + "();\n");
		String ret = "";
		
		ret += "void " + subroutineName + "()\n{\n";
		TranslatorBlock tb_comandsBlock = getTranslatorBlockAtSocket(0);
		while (tb_comandsBlock != null)
		{
			ret += tb_comandsBlock.toCode();
			tb_comandsBlock = tb_comandsBlock.nextTranslatorBlock();
		}
		ret += "}\n\n";
		return ret;
	}
}
