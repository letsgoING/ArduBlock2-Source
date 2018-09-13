package com.ardublock.translator.block.input;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LGIUltrasonicBlock extends TranslatorBlock
{
	public LGIUltrasonicBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String ultraSonicFunction = "\nint UltraschallSensor(int SensorPin){\n long duration, cm; \n pinMode(SensorPin, OUTPUT); \n digitalWrite(SensorPin, LOW);  \n delayMicroseconds(2); \n digitalWrite(SensorPin, HIGH); \n delayMicroseconds(5); \n digitalWrite(SensorPin, LOW); \n pinMode(SensorPin, INPUT); \n duration = pulseIn(SensorPin, HIGH); \n cm =  duration / 29 / 2; \n return cm;\n }\n";
	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String SensorPin;
	//	String echoPin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		SensorPin = translatorBlock.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		//translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		//echoPin = translatorBlock.toCode();
		
		translator.addSetupCommand("\tdigitalWrite( " + SensorPin + " , LOW );\n");
		
		translator.addDefinitionCommand(ultraSonicFunction);
		
		String ret = "\tUltraschallSensor( " + SensorPin + " )";
		

		return codePrefix + ret + codeSuffix;
	}
}