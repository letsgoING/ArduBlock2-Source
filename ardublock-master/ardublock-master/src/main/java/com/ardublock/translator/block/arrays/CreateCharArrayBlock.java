package com.ardublock.translator.block.arrays;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.ConstantNumberBlock;
import com.ardublock.translator.block.numbers.ConstantStringBlock;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.StringBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class CreateCharArrayBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public CreateCharArrayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String suffix = "";
		String newMarker = "_.new";
		String regex = "\\s*"+newMarker+"\\b\\s*";
		String dataType = "char";//"String";
		@SuppressWarnings("unused")
		String stdInitVal= "\"\"";
		
		String length = "0";
		String value  = "";
		
		TranslatorBlock tb_Name = this.getRequiredTranslatorBlockAtSocket(0);
		String variableName = tb_Name.toCode();
		TranslatorBlock tb_Length = this.getTranslatorBlockAtSocket(1);	
		TranslatorBlock tb_Value = this.getTranslatorBlockAtSocket(2);
		
		
		if((tb_Value instanceof VariableStringBlock) || (tb_Value instanceof LocalVariableStringBlock) || (tb_Value instanceof StringBlock) || (tb_Name instanceof ConstantStringBlock)){
			value = tb_Value.toCode().replaceAll(regex, "");
			length = ""; //remove length=0 if value is given
		}
		
		if((tb_Length instanceof NumberBlock) || (tb_Length instanceof VariableNumberBlock) || (tb_Length instanceof LocalVariableNumberBlock) || (tb_Length instanceof ConstantNumberBlock)){
			length = tb_Length.toCode().replaceAll(regex, "");
		}
		
		if (!(tb_Name instanceof VariableStringBlock) && !(tb_Name instanceof LocalVariableStringBlock)  && !(tb_Name instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		//LOCAL VAR
		if ((tb_Name instanceof LocalVariableStringBlock) && variableName.contains(newMarker)) {
				
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				if(tb_Value instanceof StringBlock){
					variableName = dataType + " " + variableName + "["+ length +"] = "+ value + suffix +";"; // add local declaration
				}else{
					variableName = dataType + " " + variableName + "["+ length +"];"; // add local declaration
				}
				
				return variableName + "\n";	
		}
		//GLOBAL VAR
		else if ((tb_Name instanceof VariableStringBlock) && variableName.contains(newMarker)){
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			if(tb_Value instanceof StringBlock){
				translator.addDefinitionCommand(dataType + " " + variableName + "["+ length +"] = "+ value + suffix +";");
			}else{
				translator.addDefinitionCommand(dataType + " " + variableName + "["+ length +"];");
			}
		}
		//CONSTANT
		else if ((tb_Name instanceof ConstantStringBlock) ){
			if(variableName.contains(newMarker)){
				variableName = variableName.replaceAll(regex, "");
				translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
				if(tb_Value instanceof StringBlock){
					translator.addDefinitionCommand(dataType + " " + variableName + "["+ length +"] = "+ value + suffix +";");
				}else{
					translator.addDefinitionCommand(dataType + " " + variableName + "["+ length +"];");
				}
			}
			else{
				throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_const_write"));
			}
			//return ""; //return without value if CONST is used
		}
		
		return "";//variableName + " = " + value + suffix + ";\n";
		
		
	}

}
