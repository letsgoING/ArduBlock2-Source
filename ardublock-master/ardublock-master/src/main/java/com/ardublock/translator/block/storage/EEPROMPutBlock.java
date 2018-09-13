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


public class EEPROMPutBlock extends TranslatorBlock
{
//	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public EEPROMPutBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
			translator.addHeaderFile("EEPROM.h");

			String ret = "EEPROM.put(";
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
			ret += tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			
			tb = this.getRequiredTranslatorBlockAtSocket(1);
			ret = "\t"+ret + ", " + tb.toCode().replaceAll("\\s*_.new\\b\\s*", "") + " );\n";
			
		return codePrefix + ret + codeSuffix;
	}
}
