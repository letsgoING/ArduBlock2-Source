package com.ardublock.translator.block.communication;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableDigitalBlock;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.VariableDigitalBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class TelegramReadFrameBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public TelegramReadFrameBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String readTeleFrameFunction = "//Frame: <12@4|data> <Sender,Empfänger,Befehl,Länge,Trennzeichen,Nutzdaten>\r\n" + 
														"void readSerialFrame(char nextChar, bool* dataComplete, int* numReceivedChars, char* recBuffer) {\r\n" + 
														"\tif (( nextChar == '<' )) {\r\n" + 
														"\t\trecBuffer[0] = nextChar;\r\n" + 
														"\t\t*numReceivedChars = 1;\r\n" + 
														"\t\t*dataComplete = false;\r\n" + 
														"\t}\r\n" + 
														"\tif (( ( *numReceivedChars > 0 ) && ( ( nextChar != '<' ) && ( nextChar != '>' ) ) )) {\r\n" + 
														"\t\trecBuffer[*numReceivedChars] = nextChar;\r\n" + 
														"\t\t*numReceivedChars = ( *numReceivedChars + 1 );\r\n" + 
														"\t\t*dataComplete = false;\r\n" + 
														"\t}\r\n" + 
														"\tif (( nextChar == '>' )) {\r\n" + 
														"\t\trecBuffer[*numReceivedChars] = nextChar;\r\n" + 
														"\t\t*numReceivedChars = 0;\r\n" + 
														"\t\t*dataComplete = true;\r\n" + 
														"\t}\r\n" + 
														"};";

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String nextChar;
		String dataComplete;
		String recChars;
		String recBuffer;
		
		TranslatorBlock tbNextChar = this.getRequiredTranslatorBlockAtSocket(0);
		nextChar = tbNextChar.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbDataComplete = this.getRequiredTranslatorBlockAtSocket(1);
		dataComplete = tbDataComplete.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecChars = this.getRequiredTranslatorBlockAtSocket(2);
		recChars = tbRecChars.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecBuffer = this.getRequiredTranslatorBlockAtSocket(3);
		recBuffer = tbRecBuffer.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if (!(tbNextChar instanceof SerialReadBlock) && !(tbNextChar instanceof SoftSerialReadBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.char_var_slot"));
		}
		
		if (!(tbDataComplete instanceof VariableDigitalBlock) && !(tbDataComplete instanceof LocalVariableDigitalBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.digital_var_slot"));
		}
		
		if (!(tbRecChars instanceof VariableNumberBlock) && !(tbRecChars instanceof LocalVariableNumberBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		if (!(tbRecBuffer instanceof VariableStringBlock) && !(tbRecBuffer instanceof LocalVariableStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addDefinitionCommand(readTeleFrameFunction);
		
		String ret = "readSerialFrame("+nextChar+", &"+dataComplete+", &"+recChars+", "+recBuffer+");";
		
		return codePrefix + ret + codeSuffix;
	}
}