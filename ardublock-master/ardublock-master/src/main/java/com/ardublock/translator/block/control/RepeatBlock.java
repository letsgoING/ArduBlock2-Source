package com.ardublock.translator.block.control;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;
import com.ardublock.translator.block.numbers.NumberBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;

public class RepeatBlock extends TranslatorBlock
{

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public RepeatBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String suffix = "";
		String newMarker = "_.new";
		String regex = "\\s*"+newMarker+"\\b\\s*";
		String dataType = "int";
		String stdInitVal= "0";
		String ret = "for(";
		
		
		TranslatorBlock tb_Name = getRequiredTranslatorBlockAtSocket(0);
		String variableName = tb_Name.toCode();
		TranslatorBlock tb_Value = this.getRequiredTranslatorBlockAtSocket(1);
		String value = tb_Value.toCode().replaceAll(regex, "");
		
		if (!(tb_Name instanceof VariableNumberBlock) && !(tb_Name instanceof LocalVariableNumberBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		/*if (!(tb_Value instanceof NumberBlock) && !(tb_Value instanceof VariableNumberBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_slot"));
		}*/
		
		//LOCAL VAR
		if ((tb_Name instanceof LocalVariableNumberBlock)) {
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			ret += dataType + " " + variableName; // add local declaration
		}
		//GLOBAL VAR
		else if ((tb_Name instanceof VariableNumberBlock) && variableName.contains(newMarker)){
			variableName = variableName.replaceAll(regex, "");
			translator.addNumberVariable(variableName, variableName);  //remove the "new" Tag after declaration
			if(tb_Value instanceof NumberBlock){
				translator.addDefinitionCommand(dataType + " " + variableName + " = "+ stdInitVal + suffix +";");
			}else{
				translator.addDefinitionCommand(dataType + " " + variableName + " = " + stdInitVal +  suffix +";");
			}
			ret += variableName;
		}
		else{
			ret += variableName;
		}
		
		ret += " = 0; " + variableName +" < " + value + "; "+ variableName +"++) {\n"; 

		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		ret = ret + "}\n";
		return ret;
	}

}
