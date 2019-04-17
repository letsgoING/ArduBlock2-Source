package com.ardublock.translator.block.storage;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableFileBlock;
import com.ardublock.translator.block.numbers.VariableFileBlock;

public class SDOpenBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SDOpenBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String newMarker = "_.new";
		String regex = "\\s*"+newMarker+"\\b\\s*";
		String dataType = "File";//"String";
		
		TranslatorBlock tb_Name = this.getRequiredTranslatorBlockAtSocket(0);
		String variableName  = tb_Name.toCode();
				
		String fileName = getRequiredTranslatorBlockAtSocket(1).toCode().replaceAll(regex, "");
		String fileMode = getRequiredTranslatorBlockAtSocket(2).toCode().replaceAll(regex, "");
		
		if (!(tb_Name instanceof VariableFileBlock) && !(tb_Name instanceof LocalVariableFileBlock) ) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		//LOCAL VAR
		if ((tb_Name instanceof LocalVariableFileBlock) && variableName.contains(newMarker)) {
				
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				variableName = dataType + " " + variableName; // add local declaration
		}
		//GLOBAL VAR
		else if ((tb_Name instanceof VariableFileBlock) && variableName.contains(newMarker)){
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			translator.addDefinitionCommand(dataType + " " + variableName +";");
		}
		
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
		
		String ret= variableName +" = SD.open("+fileName+", "+fileMode+");\n";
		
		return ret;
	}
}
