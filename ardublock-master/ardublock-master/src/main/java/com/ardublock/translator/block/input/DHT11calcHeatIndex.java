package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DHT11calcHeatIndex extends TranslatorBlock
{
	public DHT11calcHeatIndex(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		/**
		 * DO NOT add tab in code any more, we'll use arduino to format code, or the code will duplicated. 
		 */		
		String temp, humid;
		
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//temperature
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1);//humidity
		temp = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		humid = tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
				
		String ret = "dht.computeHeatIndex("+temp+", "+humid+", false)";
		
		return codePrefix+ret+codeSuffix;
	}
}
