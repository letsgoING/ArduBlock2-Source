package com.ardublock.translator.block.code;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CommentProgramBlock extends TranslatorBlock
{
	public CommentProgramBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String programName = "";
		String programAuthor = "";
		String programDescription1 = "", programDescription2 = "", programDescription3 = "";
		String programDate ="";
		String programVersion ="";
		
		String ret = "/**\n";
		
		TranslatorBlock tb_programName = this.getRequiredTranslatorBlockAtSocket(0);
		programName = tb_programName.toCode().replaceAll("\"", "");
		ret += "* file:\t" + programName +"\n";
		
		TranslatorBlock tb_programAuthor = this.getRequiredTranslatorBlockAtSocket(1);
		programAuthor = tb_programAuthor.toCode().replaceAll("\"", "");
		ret += "* author:\t"     + programAuthor +"\n* \n";
		
		TranslatorBlock tb_programDescription1 = this.getRequiredTranslatorBlockAtSocket(2);
		programDescription1 = tb_programDescription1.toCode().replaceAll("\"", "");
		ret += "* description:\n* " + programDescription1 +"\n";
		
		TranslatorBlock tb_programDescription2 = this.getTranslatorBlockAtSocket(3);
		if(tb_programDescription2 != null){
			programDescription2 = tb_programDescription2.toCode().replaceAll("\"", "");
			ret += "* " + programDescription2 +"\n";
		}
		
		TranslatorBlock tb_programDescription3 = this.getTranslatorBlockAtSocket(4);
		if(tb_programDescription3 != null){
			programDescription3 = tb_programDescription3.toCode().replaceAll("\"", "");
			ret += "* " + programDescription3 +"\n";
		}
		
		TranslatorBlock tb_programDate = this.getRequiredTranslatorBlockAtSocket(5);
		programDate = tb_programDate.toCode().replaceAll("\"", "");
		ret += "* \n* date:\t"    + programDate +"\n";
		
		TranslatorBlock tb_programVersion = this.getRequiredTranslatorBlockAtSocket(6);
		programVersion = tb_programVersion.toCode().replaceAll("\"", "");
		ret += "* version:\t" + programVersion +"\n*/\n";
		
		translator.addDefinitionCommand(ret);
		return "";
	}
}

/*String ret = "/**\n"
+"* file:\t" + programName +"\n"
+"* author:\t"     + programAuthor +"\n"
+"* \n"
+"* description:\n"  
+"* " + programDescription1 +"\n"
+"* " + programDescription2 +"\n"
+"* " + programDescription3 +"\n"
+"* \n"
+"* date:\t"    + programDate +"\n"
+"* version:\t" + programVersion +"\n"
+"*\n";
*/