package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialPrintBlock extends TranslatorBlock
{
	public SerialPrintBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{	
		String VarMarker = "><"; //split marker used in GlueBlock
		String ret = "";
		
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0); //Code
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1); //newLine?
		
		if(!translator.containsSetupCommand("Serial.begin")){
			translator.addSetupCommand("Serial.begin(9600);");
		}
		
		String stringInput = tB1.toCode();
		String[] stringParts = stringInput.split(VarMarker);
		
		for(int i = 0; i < stringParts.length; i += 1){
			if(stringParts[i].endsWith("\"")){
				stringParts[i] = stringParts[i].substring(0,stringParts[i].length() - 1) + " \"";// SPACE added at the end of every part
			}
			ret += "Serial.print(" + stringParts[i] +  ");\n";
		}

		String newLine = tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if(newLine.equals("true")||newLine.equals("HIGH")){
		    ret += "Serial.println();\n";	
		}

		return ret;
	}
}
