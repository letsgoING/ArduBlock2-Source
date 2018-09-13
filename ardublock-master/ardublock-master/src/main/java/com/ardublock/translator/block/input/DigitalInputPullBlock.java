package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalInputPullBlock extends TranslatorBlock
{
	public DigitalInputPullBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String number = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String setupCode = "pinMode( " + number + " , INPUT_PULLUP);";
		translator.addSetupCommand(setupCode);
		
		String ret = "digitalRead(";
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + ");\n";
		return codePrefix + ret + codeSuffix;
	} 

}
