package com.ardublock.translator.block.storage;

//import java.util.ResourceBundle;
//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
//import com.ardublock.translator.block.NumberBlock;
//import com.ardublock.translator.block.VariableNumberBlock;
//import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class EEPROMLengthBlock extends TranslatorBlock
{
//	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public EEPROMLengthBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		translator.addHeaderFile("EEPROM.h");
		
		String ret = "EEPROM.length()";
		return codePrefix + ret + codeSuffix;
	}
}
