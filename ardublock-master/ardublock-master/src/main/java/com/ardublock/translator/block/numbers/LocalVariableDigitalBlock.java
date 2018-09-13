package com.ardublock.translator.block.numbers;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;


public class LocalVariableDigitalBlock extends TranslatorBlock
{
	public LocalVariableDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{	
		String internalVariableName = translator.getBooleanVariable(translator.buildVariableName(label));
		String newInternalName = internalVariableName;
		
		if (internalVariableName == null )
		{
			internalVariableName = translator.buildVariableName(label);		
			newInternalName = internalVariableName+"_new"; //add the "new" Tag for varDeclaration
			translator.addBooleanVariable(internalVariableName, (newInternalName)); 
		}
		return codePrefix + newInternalName + codeSuffix;
	}
}
