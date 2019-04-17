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

public class SetterNumberArrayBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SetterNumberArrayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb_Name = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock tb_Pos	= this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock tb_Value= this.getRequiredTranslatorBlockAtSocket(2);
		
		String variableName = tb_Name.toCode();
		String arrayPos     = tb_Pos.toCode();
		String value        = tb_Value.toCode();
		
		if (!(tb_Name instanceof VariableNumberBlock)&& !(tb_Name instanceof LocalVariableNumberBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }
		/*if (!(tb_Pos instanceof VariableNumberBlock) && !(tb_Pos instanceof LocalVariableNumberBlock) && !(tb_Pos instanceof NumberBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }
		if (!(tb_Value instanceof VariableNumberBlock) && !(tb_Value instanceof LocalVariableNumberBlock) && !(tb_Value instanceof NumberBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }*/
		
		String ret = variableName+"["+arrayPos+"]";
		ret = ret + " = " + value + ";\n";
		return ret;
	}

}
