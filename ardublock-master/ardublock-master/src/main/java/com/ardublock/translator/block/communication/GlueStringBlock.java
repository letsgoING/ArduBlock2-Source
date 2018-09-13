package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class GlueStringBlock extends TranslatorBlock
{
	public GlueStringBlock (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String stringOne = tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String stringTwo = tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		String VarMarker = "><";
		String ret = "";
		
	/*	
		if(stringOne.contains("\"") && stringTwo.contains("\"")){
			stringOne = stringOne.replaceAll("\"", "");
			stringTwo = stringTwo.replaceAll("\"", "");
			
			ret = "\"" + stringOne +" "+ stringTwo + "\"";
		}
		else{
			ret = VarMarker + stringOne + VarMarker +  stringTwo +  VarMarker ;
		}
		*/
		
		ret =  stringOne + VarMarker +  stringTwo;
		
		return ret;
	}
}
