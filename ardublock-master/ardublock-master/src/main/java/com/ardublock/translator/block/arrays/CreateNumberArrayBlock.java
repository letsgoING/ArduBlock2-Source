package com.ardublock.translator.block.arrays;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;

public class CreateNumberArrayBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public CreateNumberArrayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		//String suffix = "";
		String newMarker = "_.new";
		String regex = "\\s*"+newMarker+"\\b\\s*";
		String dataType = "int";
		String stdInitVal= "0";
		
		TranslatorBlock tb_Name = this.getRequiredTranslatorBlockAtSocket(0);
		String variableName = tb_Name.toCode();
		TranslatorBlock tb_Value = this.getRequiredTranslatorBlockAtSocket(1);
		String value = tb_Value.toCode();
		
		if (!(tb_Name instanceof VariableNumberBlock) && !(tb_Name instanceof LocalVariableNumberBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		if (!(tb_Value instanceof NumberBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.array_size_slot"));
		}
		
		//LOCAL VAR
		if ((tb_Name instanceof LocalVariableNumberBlock) && variableName.contains(newMarker)) {
				
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				variableName = dataType + " " + variableName + "["+ value +"]" + " = {"+stdInitVal+"};"; // add local declaration
		}
		//GLOBAL VAR
		else if ((tb_Name instanceof VariableNumberBlock) && variableName.contains(newMarker)){
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			if(tb_Value instanceof NumberBlock){
				translator.addDefinitionCommand(dataType + " " + variableName + "["+ value +"]" + " = {"+stdInitVal+"};");
				variableName = "";
			}else{
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.array_size_slot"));
			}
		}
		return variableName;
	}

}
