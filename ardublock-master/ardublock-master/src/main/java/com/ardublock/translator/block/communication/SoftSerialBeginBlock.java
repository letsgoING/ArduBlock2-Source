package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SoftSerialBeginBlock extends TranslatorBlock
{
	public SoftSerialBeginBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		/**
		 * DO NOT add tab in code any more, we'll use arduino to format code, or the code will duplicated. 
		 */
		translator.addHeaderFile("SoftwareSerial.h");
		
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//Pin Rx
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1);//Pin Tx
		TranslatorBlock tB3 = this.getRequiredTranslatorBlockAtSocket(2);//Baud
		String SerialNumber = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		translator.addDefinitionCommand("SoftwareSerial softSerial"+SerialNumber+"(" + tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + ", "+ tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "") +");\n");
		translator.addSetupCommand("softSerial"+SerialNumber+".begin("+tB3.toCode().replaceAll("\\s*_.new\\b\\s*", "")+");");
		
		return "";
	}
}
