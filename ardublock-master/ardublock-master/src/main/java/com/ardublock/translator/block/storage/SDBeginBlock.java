package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDBeginBlock extends TranslatorBlock
{
	public SDBeginBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("SD.h");
		
		String cs_pin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		cs_pin = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
	    translator.addSetupCommand("SD.begin("+cs_pin+");\t//chip_select Pin"+cs_pin+"\n"); 
	    
	    return "";
	}
}
