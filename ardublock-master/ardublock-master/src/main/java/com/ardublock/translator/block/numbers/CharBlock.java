package com.ardublock.translator.block.numbers;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class CharBlock extends TranslatorBlock
{
	public CharBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		/*
		//trim char to one character
		int length = label.toString().length();
		if(length > 1){
			label = label.toString().substring(0,1);//length-1,length);
		}
		*/
	    return codePrefix + "\'"+label+ "\'" + codeSuffix;
	}

}
