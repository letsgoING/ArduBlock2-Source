package com.ardublock.translator.block.cast;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CastUpperIntCharBlock extends TranslatorBlock
{
	public CastUpperIntCharBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String intData = tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
	
		
		return "char("+ intData + " >> 8)";
	}

}
