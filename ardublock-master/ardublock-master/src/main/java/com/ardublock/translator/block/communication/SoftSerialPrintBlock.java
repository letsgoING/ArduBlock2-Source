package com.ardublock.translator.block.communication;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SoftSerialPrintBlock extends TranslatorBlock
{
	public SoftSerialPrintBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		/**
		 * DO NOT add tab in code any more, we'll use arduino to format code, or the code will duplicated. 
		 */
		String VarMarker = "><"; //split marker used in GlueBlock
		String ret = "";
		
		TranslatorBlock tB1 = this.getRequiredTranslatorBlockAtSocket(0);//Pin Rx
		TranslatorBlock tB2 = this.getRequiredTranslatorBlockAtSocket(1);//Pin Tx
		String SerialNumber = tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "") + tB2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tB3 = this.getRequiredTranslatorBlockAtSocket(2);//,"softSerial"+tB1.toCode().replaceAll("\\s*_.new\\b\\s*", "")+".print(",");\n");//Code
		TranslatorBlock tB4 = this.getRequiredTranslatorBlockAtSocket(3);//newLine?
		
		
		String stringInput = tB3.toCode();
		String[] stringParts = stringInput.split(VarMarker);
		
		for(int i = 0; i < stringParts.length; i += 1){
			stringParts[i] += " ";// SPACE added at the end of every part
			ret += "softSerial"+SerialNumber+".print(" + stringParts[i] +  ");\n"; 
		}

		String newLine = tB4.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if(newLine.equals("true")||newLine.equals("HIGH")){
		    ret += "softSerial"+SerialNumber+".println();\n";	
		}

		return ret;
	}
}
