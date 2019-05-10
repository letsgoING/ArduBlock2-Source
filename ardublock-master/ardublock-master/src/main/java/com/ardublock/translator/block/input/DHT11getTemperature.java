package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DHT11getTemperature extends TranslatorBlock
{
	public DHT11getTemperature(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}


	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		 // Header hinzuf�gen
		translator.addHeaderFile("DHT.h");
		String ret = "dht.readTemperature()";
		return codePrefix + ret + codeSuffix;
	}
}