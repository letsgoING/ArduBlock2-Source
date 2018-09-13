package com.ardublock.translator.block.numbers;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.ConstBlock;

public class DigitalHighBlock extends ConstBlock
{

	public DigitalHighBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		this.setCode("HIGH");
	}
}
