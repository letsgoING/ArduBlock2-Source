package com.ardublock.translator.block.storage;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EEPROMReadBlock extends TranslatorBlock
{
	public EEPROMReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("EEPROM.h");
		
		String ret = "EEPROM.read(";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret += tb.toCode().replaceAll("\\s*_.new\\b\\s*", "") + ")";
		
	return codePrefix + ret + codeSuffix;
	}
}
