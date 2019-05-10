package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DHT11getHumidity extends TranslatorBlock
{
	public DHT11getHumidity(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}


	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		 // Header hinzuf�gen
		translator.addHeaderFile("DHT.h");
		String ret = "dht.readHumidity()";
		return codePrefix + ret + codeSuffix;
	}
}