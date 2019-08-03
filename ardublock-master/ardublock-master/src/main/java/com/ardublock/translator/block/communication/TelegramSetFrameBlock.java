package com.ardublock.translator.block.communication;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class TelegramSetFrameBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public TelegramSetFrameBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String setTeleFrameFunction = "//Frame: <12@4|data> <Sender,Empfänger,Befehl,Länge,Trennzeichen,Nutzdaten>\r\n" + 
														"void setTeleFrame(int sendAddress, int recAddress, char command, int dataLength, char* payload, char* sendBuffer) {\r\n" + 
														"\tsendBuffer[0] = '<';              //setze Startzeichen\r\n" + 
														"\tsendBuffer[5] = '|';              // setze Trennzeichen\r\n" + 
														"\tsendBuffer[dataLength + 6] = '>'; // setze Stoppzeichen\r\n" + 
														"\tsendBuffer[dataLength + 7] = char(0);          // setze Abschlusszeichen für string/print\r\n" + 
														"\tsendBuffer[1] = sendAddress + 48;\r\n" + 
														"\tsendBuffer[2] = recAddress  + 48;\r\n" + 
														"\tsendBuffer[3] = command;\r\n" + 
														"\tsendBuffer[4] = dataLength  + 48;\r\n" + 
														"\tfor (int i = 0; i < dataLength; i++) {\r\n" + 
														"\t\tsendBuffer[6 + i] = payload[i]; //Übertrage Zeichen auss payload in sendBuffer\r\n" + 
														"\t}\r\n" + 
														"};";
		
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String sendAddress;
		String recAddress;
		String dataLength;
		String command;
		String payload;
		String sendBuffer;
			
		TranslatorBlock tbSendAddress = this.getRequiredTranslatorBlockAtSocket(0);
		sendAddress = tbSendAddress.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecAddress = this.getRequiredTranslatorBlockAtSocket(1);
		recAddress = tbRecAddress.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbLength = this.getRequiredTranslatorBlockAtSocket(2);
		dataLength = tbLength.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbCommand = this.getRequiredTranslatorBlockAtSocket(3);
		command = tbCommand.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbPayload = this.getRequiredTranslatorBlockAtSocket(4);
		payload = tbPayload.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbSendBuffer = this.getRequiredTranslatorBlockAtSocket(5);
		sendBuffer = tbSendBuffer.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if (!(tbPayload instanceof VariableStringBlock) && !(tbPayload instanceof LocalVariableStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbSendBuffer instanceof VariableStringBlock) && !(tbSendBuffer instanceof LocalVariableStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addDefinitionCommand(setTeleFrameFunction);
		
		String ret = "setTeleFrame("+sendAddress+", "+recAddress+", "+dataLength+", "+command+ ", "+payload+", "+sendBuffer+");";
		

		return codePrefix + ret + codeSuffix;
	}
}