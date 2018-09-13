package com.ardublock.translator.block.arrays;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ReadCharArrayBlock extends TranslatorBlock
{
	public ReadCharArrayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock name = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock position = this.getRequiredTranslatorBlockAtSocket(1);
		String ret = name.toCode().replaceAll("\\s*_.new\\b\\s*", "")+"["+position.toCode().replaceAll("\\s*_.new\\b\\s*", "")+"]";
		return codePrefix + ret + codeSuffix;
	}

}
