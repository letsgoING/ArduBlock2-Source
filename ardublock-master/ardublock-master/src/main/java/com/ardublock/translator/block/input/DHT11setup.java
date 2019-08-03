package com.ardublock.translator.block.input;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DHT11setup extends TranslatorBlock
{
	  public DHT11setup (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	  {
	    super(blockId, translator, codePrefix, codeSuffix, label);
	  }

	  public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	  {
	    String pin, type, ret;
	        
	    // Wert von dem ersten Blockeingang auslesen
	    TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
	    pin = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
	    
	    translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
	    type = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
	    
	           
	    // Header hinzufuegen
	    translator.addHeaderFile("DHT.h");
	            
	    // Deklarationen hinzufuegen
		translator.addDefinitionCommand("DHT dht(" + pin + "," + type +");\n");
		
		
		// setup hinzufuegen
		 translator.addSetupCommand("dht.begin();");

		ret = "";
		return codePrefix + ret + codeSuffix;
	  }
	}
