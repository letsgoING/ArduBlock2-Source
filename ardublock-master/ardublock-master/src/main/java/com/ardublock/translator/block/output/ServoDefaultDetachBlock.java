
package com.ardublock.translator.block.output;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.NumberBlock;

public class ServoDefaultDetachBlock extends TranslatorBlock {

	public ServoDefaultDetachBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		//String servoSpecs = "";

		if (!( tb instanceof NumberBlock ) )
		{
			throw new BlockException(this.blockId, "the Pin# of Servo must be a number");
		}
		
		String pinNumber = tb.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		String servoName = "servo_pin_" + pinNumber;

	//	tb = this.getRequiredTranslatorBlockAtSocket(1);

		String ret = servoName + ".detach();\n";
		translator.addHeaderFile("Servo.h");
		translator.addDefinitionCommand("Servo " + servoName + ";");
		//translator.addSetupCommand(servoName + ".detach(" + pinNumber + servoSpecs + ");");
		return ret;
	}

}
