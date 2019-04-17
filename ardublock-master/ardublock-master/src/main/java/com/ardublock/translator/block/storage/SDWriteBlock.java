package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDWriteBlock extends TranslatorBlock
{
	public SDWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String VarMarker = "><"; //split marker used in GlueBlock
		
		String fileVar  = getRequiredTranslatorBlockAtSocket(0).toCode();
		fileVar = fileVar.replaceAll("\"", "");
		
		String fileData = getRequiredTranslatorBlockAtSocket(1).toCode();
		String newLine  = getRequiredTranslatorBlockAtSocket(2).toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String ret = "";
		
		String[] stringParts = fileData.split(VarMarker);
		
		for(int i = 0; i < stringParts.length; i += 1){
			if(stringParts[i].endsWith("\"")){
				stringParts[i] = stringParts[i].substring(0,stringParts[i].length() - 1) + " \"";// SPACE added at the end of every part
			}
			ret += fileVar+".print("+stringParts[i]+");\n"; 
		}
		
		if(newLine.equals("true")|| newLine.equals("HIGH")){
			
			ret = fileVar+".println();";
		}
        
		return  ret ;
	}
}
