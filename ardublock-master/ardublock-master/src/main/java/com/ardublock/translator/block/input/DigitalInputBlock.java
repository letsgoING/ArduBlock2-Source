package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;

public class DigitalInputBlock extends TranslatorBlock
{
	public DigitalInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String number;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		number = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if(translatorBlock instanceof NumberBlock || translatorBlock instanceof VariableNumberBlock){
			//translator.addInputPin(number.trim());
			translator.addSetupCommand("pinMode("+number.trim()+", INPUT);");
		}
		
		
		String ret = "digitalRead(";
		ret = ret + number;
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	} 

}
