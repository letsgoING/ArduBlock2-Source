package com.ardublock.translator.block.code;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CommentHeadBlock extends TranslatorBlock
{
	public CommentHeadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String comment = translatorBlock.toCode().replaceAll("\"", "");
		String ret = "//"+ comment +"\n";
		translator.addDefinitionCommand(ret);
		return "";
	}
}
