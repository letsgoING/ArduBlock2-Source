
package com.ardublock.translator.block.output;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class StepperSpeedBlock extends TranslatorBlock {
	
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public StepperSpeedBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tbNum = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock tbSpeed = this.getRequiredTranslatorBlockAtSocket(1);
		
		String num = tbNum.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String speed = tbSpeed.toCode().replaceAll("\\s*_.new\\b\\s*", "");

		String stepperName = "stepper" + num;
		
		String internalVariableName = translator.buildVariableName(stepperName);
		if (translator.getNumberVariable(internalVariableName) == null){
			throw new BlockException(this.blockId, uiMessageBundle.getString("ardublock.error_msg.stepper_not_existing")+": stepper"+num);
		}


		
		return internalVariableName + ".setSpeed("+speed+");\n";
	}

}
