package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;

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
		
		if(translatorBlock instanceof NumberBlock || translatorBlock instanceof VariableNumberBlock){
			//translator.addSetupCommand("pinMode( " + number + " , INPUT_PULLUP);");
			translator.addSetupCommand("pinMode("+number.trim()+", INPUT_PULLUP);");
		}
		
		String ret = "digitalRead(";
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + ");\n";
		return codePrefix + ret + codeSuffix;
	} 

}
