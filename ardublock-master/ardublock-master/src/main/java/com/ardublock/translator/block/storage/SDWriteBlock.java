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
		String fileVar  = getRequiredTranslatorBlockAtSocket(0).toCode();
		fileVar = fileVar.replaceAll("\"", "");
		
		String fileData = getRequiredTranslatorBlockAtSocket(1).toCode();
		String newLine  = getRequiredTranslatorBlockAtSocket(2).toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		String ret;
		
		if(newLine.equals("true")|| newLine.equals("HIGH")){
			
			ret = fileVar+".println("+fileData+");";
		}
		else{
			ret = fileVar+".print("+fileData+");";
		}
        
		return  ret ;
	}
}
