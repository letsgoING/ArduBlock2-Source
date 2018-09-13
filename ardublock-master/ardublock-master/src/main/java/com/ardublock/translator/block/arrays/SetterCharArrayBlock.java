package com.ardublock.translator.block.arrays;


import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.ConstantStringBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class SetterCharArrayBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SetterCharArrayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		
		if (!(tb_Name instanceof VariableStringBlock) && !(tb_Name instanceof LocalVariableStringBlock)  && !(tb_Name instanceof ConstantStringBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }
		/*	if (!(tb_Pos instanceof VariableNumberBlock) && !(tb_Pos instanceof LocalVariableNumberBlock) && !(tb_Pos instanceof NumberBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }
		if (!(tb_Value instanceof VariableStringBlock) && !(tb_Value instanceof LocalVariableStringBlock)  && !(tb_Value instanceof ConstantStringBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		    }*/
		
		String ret = variableName+"["+arrayPos+"]";
		ret = ret + " = " + value + ";\n";
		return ret;
	}

}
