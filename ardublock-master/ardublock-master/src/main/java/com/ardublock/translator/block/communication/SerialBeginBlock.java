package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialBeginBlock extends TranslatorBlock
{
	public SerialBeginBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//Baud
		translator.addSetupCommand("Serial.begin("+tB1.toCode()+");");
		
		return "";
	}
}
