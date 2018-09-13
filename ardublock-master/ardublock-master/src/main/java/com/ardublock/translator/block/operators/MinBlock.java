package com.ardublock.translator.block.operators;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MinBlock extends TranslatorBlock
{

	public MinBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "\tmin( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + " , ";
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " )";
		return codePrefix + ret + codeSuffix;
	}
	
}
