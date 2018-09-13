package com.ardublock.translator.block.operators;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ConstrainBlock extends TranslatorBlock
{
	public ConstrainBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "constrain( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " , ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " ) ";
		return codePrefix + ret + codeSuffix;
	}
	
}
