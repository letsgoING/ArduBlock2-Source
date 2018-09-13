package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class GlueBlock extends TranslatorBlock
{
	public GlueBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{	
		String VarMarker = "><"; //split marker for SerialPrintBlock
		String ret = "";
		
		TranslatorBlock tB2 = this.getTranslatorBlockAtSocket(1);
		
		TranslatorBlock tB1 = this.getTranslatorBlockAtSocket(0);
		if(tB1 != null){
			ret = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + VarMarker;
		}
		
		return  ret + tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "") + " ";
	}
}

