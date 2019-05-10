package com.ardublock.translator.block.communication;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.ConstantStringBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class MQTTSetFrameBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public MQTTSetFrameBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String setMQTTFrameFunction =    "void setMQTTFrame(int topicLength, char* topic, int payloadLength, char* payload, char* sendBuffer) {"
														+ "	  sendBuffer[0] = '<';              //setze Startzeichen"
														+ "	  sendBuffer[topicLength + 1] = '|';              // setze Trennzeichen"
														+ "	  sendBuffer[topicLength + payloadLength + 2] = '>'; // setze Stoppzeichen"
														+ "	  sendBuffer[topicLength + payloadLength + 3] = char(0);          // setze Abschlusszeichen für string/print"
														+ "	  for (int i = 0; i < topicLength; i++) {"
														+ "	    sendBuffer[1 + i] = topic[i]; //Übertrage Zeichen aus topic in sendBuffer"
														+ "	  }"
														+ "	  for (int i = 0; i < payloadLength; i++) {"
														+ "	    sendBuffer[topicLength + 2 + i] = payload[i]; //Übertrage Zeichen aus payload in sendBuffer"
														+ "	  }"
														+ "};";
	
	
	
	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String topicLength;
		String topic;
		String payloadLength;
		String payload;
		String sendBuffer;
			
		TranslatorBlock tbTopicLength = this.getRequiredTranslatorBlockAtSocket(0);
		topicLength = tbTopicLength.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbTopic = this.getRequiredTranslatorBlockAtSocket(1);
		topic = tbTopic.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbPayloadLength = this.getRequiredTranslatorBlockAtSocket(2);
		payloadLength = tbPayloadLength.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbPayload = this.getRequiredTranslatorBlockAtSocket(3);
		payload = tbPayload.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbSendBuffer = this.getRequiredTranslatorBlockAtSocket(4);
		sendBuffer = tbSendBuffer.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if (!(tbTopic instanceof VariableStringBlock) && !(tbTopic instanceof LocalVariableStringBlock)  && !(tbTopic instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbPayload instanceof VariableStringBlock) && !(tbPayload instanceof LocalVariableStringBlock)  && !(tbPayload instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbSendBuffer instanceof VariableStringBlock) && !(tbSendBuffer instanceof LocalVariableStringBlock)  && !(tbSendBuffer instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addDefinitionCommand(setMQTTFrameFunction);
		
		String ret = "setMQTTFrame("+topicLength+", "+topic+", "+payloadLength+", "+payload+", "+sendBuffer+");";
		

		return codePrefix + ret + codeSuffix;
	}
}