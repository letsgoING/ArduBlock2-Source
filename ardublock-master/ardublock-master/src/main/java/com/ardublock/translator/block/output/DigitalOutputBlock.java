package com.ardublock.translator.block.output;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;

public class DigitalOutputBlock extends TranslatorBlock
{
	public DigitalOutputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String number = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		//ToDo: 
		if(translatorBlock instanceof NumberBlock || translatorBlock instanceof VariableNumberBlock){
			//translator.addOutputPin(number.trim());
			translator.addSetupCommand("pinMode("+number.trim()+", OUTPUT);");
		}
		
		String ret = "digitalWrite( ";
		ret = ret + number;
		ret = ret + " , ";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		ret = ret + " );\n";
		
		return ret;
	}

}
