package com.ardublock.translator.block.output;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class AnalogOutputBlock extends TranslatorBlock
{
	public AnalogOutputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String portNum = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String value = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		/* NOTE: AnalogWrite never needs the pin to be set as an OUTPUT */
		String ret = "analogWrite(" + portNum + " , " + value + ");\n";
		return ret;
	}

}


