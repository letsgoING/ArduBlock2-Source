package com.ardublock.translator.block.control;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class InterruptBlock extends TranslatorBlock
{

	public InterruptBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String isrName = label.trim();
		
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		String irNumber = translatorBlock.toCode();
		String irMode   =  getRequiredTranslatorBlockAtSocket(1).toCode();
		
		switch (irMode) {
		case "0":
			irMode = "LOW";
			break;
		case "1":
			irMode = "HIGH";
			break;
		case "2":
			irMode = "RISING";
			break;
		case "3":
			irMode = "FALLING";
			break;
		case "4":
			irMode = "CHANGE";
			break;
		default:
			irMode = "CHANGE";
		}
		
		translator.addDefinitionCommand("void " + isrName + "();\n");
		translator.addSetupCommand("attachInterrupt(" +irNumber+ ", " +isrName + ", " +irMode+");\n");

		String ret = "void " + isrName + "()\n{\n";
		translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\n\n";
		return ret;
	}

}