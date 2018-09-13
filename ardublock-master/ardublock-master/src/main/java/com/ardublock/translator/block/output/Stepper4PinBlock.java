
package com.ardublock.translator.block.output;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Stepper4PinBlock extends TranslatorBlock {
	
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

	public Stepper4PinBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tbNum = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock tbPin1 = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock tbPin2 = this.getRequiredTranslatorBlockAtSocket(2);
		TranslatorBlock tbPin3 = this.getRequiredTranslatorBlockAtSocket(3);
		TranslatorBlock tbPin4 = this.getRequiredTranslatorBlockAtSocket(4);
		TranslatorBlock tbStepsPerRev = this.getRequiredTranslatorBlockAtSocket(5);

		
		String num = tbNum.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String pin1 = tbPin1.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String pin2 = tbPin2.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String pin3 = tbPin3.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String pin4 = tbPin4.toCode().replaceAll("\\s*_.new\\b\\s*", "");	
		String stepsPR = tbStepsPerRev.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		String stepperName = "stepper" + num;
		String internalVariableName = translator.buildVariableName(stepperName);
		
		if (translator.getNumberVariable(internalVariableName) == null){
			translator.addNumberVariable(internalVariableName, internalVariableName);
			translator.addHeaderFile("Stepper.h");
			translator.addDefinitionCommand("Stepper "+internalVariableName +"("+stepsPR+", "+pin1+", "+pin2+", "+pin3 +", "+pin4+");\n");
		}
		else{
			throw new BlockException(this.blockId, uiMessageBundle.getString("ardublock.error_msg.stepper_duplicated")+": stepper"+num);
		}

		return "";
		
	}

}
