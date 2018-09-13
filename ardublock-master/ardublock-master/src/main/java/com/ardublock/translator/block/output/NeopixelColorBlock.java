package com.ardublock.translator.block.output;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NeopixelColorBlock  extends TranslatorBlock {

	public NeopixelColorBlock (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin ;
			String Pixel_Nb;
			String Red;
			String Blue;
			String Green;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Pixel_Nb = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Red = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Green = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Blue = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			
			
			String ret = "strip_pin"+Pin+".setPixelColor("+Pixel_Nb+","+Red+" ,"+Green+" ,"+Blue+" );\n";
			
			return codePrefix + ret + codeSuffix;
				
		}
}
