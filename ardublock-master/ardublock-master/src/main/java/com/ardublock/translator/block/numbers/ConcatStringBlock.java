package com.ardublock.translator.block.numbers;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ConcatStringBlock extends TranslatorBlock
{
	public ConcatStringBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String stringOne = tb.toCode();
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String stringTwo = tb.toCode();
		
		return "String("+ stringOne + ") + String("+ stringTwo + ")";
	}

}
