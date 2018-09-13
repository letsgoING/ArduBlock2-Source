package com.ardublock.translator.block.numbers;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SetterVariableCharBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SetterVariableCharBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String suffix = "";
		String newMarker = "_.new";
		String regex = "\\s*"+newMarker+"\\b\\s*";
		String dataType = "char";
		String stdInitVal= "\"\"";
		
		TranslatorBlock tb_Name = this.getRequiredTranslatorBlockAtSocket(0);
		String variableName = tb_Name.toCode();
		TranslatorBlock tb_Value = this.getRequiredTranslatorBlockAtSocket(1);
		String value = tb_Value.toCode().replaceAll(regex, "");
		
		if (!(tb_Name instanceof VariableCharBlock) && !(tb_Name instanceof LocalVariableCharBlock)  && !(tb_Name instanceof ConstantCharBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		//LOCAL VAR
		if ((tb_Name instanceof LocalVariableCharBlock) && variableName.contains(newMarker)) {
				
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				variableName = dataType + " " + variableName; // add local declaration
		}
		//GLOBAL VAR
		else if ((tb_Name instanceof VariableCharBlock) && variableName.contains(newMarker)){
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			translator.addDefinitionCommand(dataType + " " + variableName + " = "+ value + suffix +";");
		}
		//CONSTANT
		else if ((tb_Name instanceof ConstantCharBlock) ){
			if(variableName.contains(newMarker)){
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				if(tb_Value instanceof CharBlock){
					translator.addDefinitionCommand(dataType + " " + variableName + " = "+ value + suffix +";");
				}else{
					translator.addDefinitionCommand(dataType + " " + variableName + " = " + stdInitVal + ";");
				}
			}
			else{
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_const_write"));
			}
			return ""; //Break without return value if CONST is used
		}
		
		return variableName + " = " + value + suffix + ";\n";
	}

}
