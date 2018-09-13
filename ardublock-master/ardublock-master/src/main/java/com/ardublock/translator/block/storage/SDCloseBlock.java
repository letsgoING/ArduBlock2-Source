package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDCloseBlock extends TranslatorBlock
{
	public SDCloseBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String fileVar  = getRequiredTranslatorBlockAtSocket(0).toCode();
		fileVar = fileVar.replaceAll("\"", "");
		String ret = fileVar+".close();";
		
		return  ret ;
	}
}
