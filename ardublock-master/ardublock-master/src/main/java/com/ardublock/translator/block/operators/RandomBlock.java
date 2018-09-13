package com.ardublock.translator.block.operators;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RandomBlock extends TranslatorBlock
{

	public RandomBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "random( ";
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " )";
		//translator.addSetupCommand("\trandomSeed(analogRead(A0) + analogRead(A1) + analogRead(A2));");
		return ret;
	}

}
