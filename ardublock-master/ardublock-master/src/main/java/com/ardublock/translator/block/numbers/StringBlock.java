package com.ardublock.translator.block.numbers;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class StringBlock extends TranslatorBlock
{
	public StringBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
	/*	//TODO take out special character
		String ret;
		ret = label.replaceAll("\\\\", "\\\\\\\\");
		ret = ret.replaceAll("\"", "\\\\\"");
		//A way to have 'space' at start or end of message
		ret = ret.replaceAll("<&space>", " ");
		//A way to have other block settings applied but no message sent
		ret = ret.replaceAll("<&nothing>", "");
		// A way to add \t to messages
		ret = ret.replaceAll("<&tab>", "\\\\t");
		ret = codePrefix + "\"" + ret + "\"" + codeSuffix;
		
		return ret;
		*/
		return codePrefix + "\"" + label + "\"" + codeSuffix;
		
	}
}