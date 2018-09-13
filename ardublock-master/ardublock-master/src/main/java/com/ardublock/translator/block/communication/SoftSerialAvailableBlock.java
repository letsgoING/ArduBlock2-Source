package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SoftSerialAvailableBlock extends TranslatorBlock
{
	public SoftSerialAvailableBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//Pin Rx
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1);//Pin Tx
		String SerialNumber = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
	
		return "softSerial"+SerialNumber+".available()";
	}
}
