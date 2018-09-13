package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDOpenBlock extends TranslatorBlock
{
	public SDOpenBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String fileVar  = getRequiredTranslatorBlockAtSocket(0).toCode();
		fileVar = fileVar.replaceAll("\"", "");
		
		String fileName = getRequiredTranslatorBlockAtSocket(1).toCode();
		String fileMode = getRequiredTranslatorBlockAtSocket(2).toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		switch (fileMode) {
		case "false":
		case "LOW":
			fileMode = "FILE_WRITE";
			break;
		case "true":
		case "HIGH":
			fileMode = "FILE_READ";
			break;
		default:
			fileMode = "FILE_WRITE";
		}
		
		String ret="File "+fileVar+" = SD.open("+fileName+", "+fileMode+");\n";
		
		return ret;
	}
}
