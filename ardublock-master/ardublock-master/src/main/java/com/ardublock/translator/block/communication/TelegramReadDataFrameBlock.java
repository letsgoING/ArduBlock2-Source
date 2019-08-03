package com.ardublock.translator.block.communication;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.ConstantStringBlock;
import com.ardublock.translator.block.numbers.LocalVariableCharBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.StringBlock;
import com.ardublock.translator.block.numbers.VariableCharBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class TelegramReadDataFrameBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public TelegramReadDataFrameBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String readTeleFrameFunction = "//Frame: <12@4|data> <Sender,Empfänger,Befehl,Länge,Trennzeichen,Nutzdaten>\r\n" + 
														"void readTeleFrame(int sendAddress, int recAddress, char* command, char* payload, char* recBuffer) {\r\n" + 
														"\tint dataLength = 0;   //Variable für lokale Datenlänge\r\n" + 
														"\tpayload[0] = char(0); //setze Abschlusszeichen für string/print falls keine korrekten Daten erhalten werden\r\n" + 
														"\t*command = '0';       //setze Command auf null, falls kein gültiger Befehl oder Frame\r\n" + 
														"\t//Überprüfe ob Daten in Telegrammrahmen und an richrige Adresse gehen bzw. von richtiger Adresse kommen (0 => accept all)\r\n" + 
														"\tif (recBuffer[0] == '<' && (sendAddress == recBuffer[1] - 48 || sendAddress == 0) && (recAddress == recBuffer[2] - 48 || recAddress == 0) && recBuffer[5] == '|') {\r\n" + 
														"\t\tdataLength = recBuffer[4] - 48; //lese Länge der übertragenen Daten ein\r\n" + 
														"\t\tif (recBuffer[dataLength + 6] == '>') {\r\n" + 
														"\t\t\t*command = recBuffer[3];\r\n" + 
														"\t\t\tfor (int i = 0; i < dataLength; i++) {\r\n" + 
														"\t\t\t\tpayload[i] = recBuffer[6 + i]; //Übertrage Zeichen aus payload in sendBuffer\r\n" + 
														"\t\t\t}\r\n" + 
														"\t\t\tpayload[dataLength] = char(0);          //setze Abschlusszeichen für string/print\r\n" + 
														"\t\t}\r\n" + 
														"\t}\r\n" + 
														"};";

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String sendAddress;
		String recAddress;
		String command;
		String payload;
		String recBuffer;
		
		TranslatorBlock tbSendAddress = this.getRequiredTranslatorBlockAtSocket(0);
		sendAddress = tbSendAddress.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecAddress = this.getRequiredTranslatorBlockAtSocket(1);
		recAddress = tbRecAddress.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbCommand = this.getRequiredTranslatorBlockAtSocket(2);
		command = tbCommand.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbPayload = this.getRequiredTranslatorBlockAtSocket(3);
		payload = tbPayload.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecBuffer = this.getRequiredTranslatorBlockAtSocket(4);
		recBuffer = tbRecBuffer.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if (!(tbCommand instanceof VariableCharBlock) && !(tbCommand instanceof LocalVariableCharBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.char_var_slot"));
		}
		
		if (!(tbPayload instanceof VariableStringBlock) && !(tbPayload instanceof LocalVariableStringBlock)  && !(tbPayload instanceof StringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbRecBuffer instanceof VariableStringBlock) && !(tbRecBuffer instanceof LocalVariableStringBlock)  && !(tbRecBuffer instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addDefinitionCommand(readTeleFrameFunction);
		
		String ret = "readTeleFrame("+sendAddress+", "+recAddress+", &"+command+", "+payload+", "+recBuffer+");";
		

		return codePrefix + ret + codeSuffix;
	}
}