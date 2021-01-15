package com.ardublock.translator.block.output;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NeopixelInitBlock  extends TranslatorBlock {

	public NeopixelInitBlock (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			//String Pin ;
			String NbLed;
			//String NEO_KHZ;
			//String NEO_MODE;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			//Pin = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			//translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			NbLed = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
			//translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			//NEO_KHZ = translatorBlock.toCode().replaceAll("\"", "");
			//translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			//NEO_MODE = translatorBlock.toCode().replaceAll("\"", "");
			
			translator.addHeaderFile("Adafruit_NeoPixel.h");
			translator.addDefinitionCommand("Adafruit_NeoPixel strip= Adafruit_NeoPixel("+NbLed+", 0, NEO_RGB + NEO_KHZ800);");//,"+ Pin +", " +NEO_MODE+ " + "+NEO_KHZ +");");
			translator.addSetupCommand("strip.begin();\n" +
			"strip.show();");
			
			
			return "" ;	
		}
}
