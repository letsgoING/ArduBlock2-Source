package com.ardublock.translator.block.cast;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CastCharIntBlock extends TranslatorBlock
{
	public CastCharIntBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb1 = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock tb2 = this.getRequiredTranslatorBlockAtSocket(1);
		String HighByte = tb1.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String LowByte  = tb2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
	
		return "int("+ HighByte + " << 8) + int("+ LowByte + ")";
	}

}
