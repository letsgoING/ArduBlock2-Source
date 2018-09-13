package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SoftSerialReadBlock extends TranslatorBlock
{
	public SoftSerialReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		/**
		 * DO NOT add tab in code any more, we'll use arduino to format code, or the code will duplicated. 
		 */		
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//Pin Rx
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1);//Pin Tx
		String SerialNumber = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		String ret = "softSerial"+SerialNumber+".read()";
		
		return codePrefix+ret+codeSuffix;
	}
}
