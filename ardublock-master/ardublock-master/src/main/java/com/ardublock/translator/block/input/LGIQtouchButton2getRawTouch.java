package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LGIQtouchButton2getRawTouch extends TranslatorBlock
{
	public LGIQtouchButton2getRawTouch(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}


	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		 // Header hinzuf�gen
		translator.addHeaderFile("LGI_QTouch.h");
		String ret = "Button2.getRawTouch()";
		return codePrefix + ret + codeSuffix;
	}
}