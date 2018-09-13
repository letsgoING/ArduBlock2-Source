package com.ardublock.translator.block.operators;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MapCommonBlock extends TranslatorBlock
{
	public MapCommonBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "map( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " , 0, 1023, 0, 255)";
		return codePrefix + ret + codeSuffix;
	}
	

}
