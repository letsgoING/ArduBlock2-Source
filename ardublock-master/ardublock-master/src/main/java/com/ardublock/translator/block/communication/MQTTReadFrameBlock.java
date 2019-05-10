package com.ardublock.translator.block.communication;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.translator.block.numbers.ConstantStringBlock;
import com.ardublock.translator.block.numbers.LocalVariableNumberBlock;
import com.ardublock.translator.block.numbers.LocalVariableStringBlock;
import com.ardublock.translator.block.numbers.VariableNumberBlock;
import com.ardublock.translator.block.numbers.VariableStringBlock;

public class MQTTReadFrameBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public MQTTReadFrameBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private final static String readMQTTFrameFunction = "void readMQTTFrame(int* topicLength, char* topic, int* payloadLength, char* payload, char* recBuffer) {\r\n" + 
														"  char charBuffer = '-';\r\n" + 
														"  int  charCounter = 0;\r\n" + 
														"  bool topicRead = false;\r\n" + 
														"  bool payloadRead = false;\r\n" + 
														"  \r\n" + 
														"  topic[0] = char(0); //setze Abschlusszeichen für string/print falls keine korrekten Daten erhalten werden\r\n" + 
														"  payload[0] = char(0); //setze Abschlusszeichen für string/print falls keine korrekten Daten erhalten werden\r\n" + 
														"  \r\n" + 
														"  while (!(topicRead  && payloadRead)) {\r\n" + 
														"    charBuffer = recBuffer[charCounter];\r\n" + 
														"    if (charBuffer == '<') {\r\n" + 
														"      charCounter = 1;\r\n" + 
														"      }\r\n" + 
														"    else if (charBuffer == '|') {\r\n" + 
														"      topicRead = true;\r\n" + 
														"      *topicLength = charCounter - 1;\r\n" + 
														"      topic[*topicLength] = char(0); //setze Abschlusszeichen für string/print\r\n" + 
														"      charCounter++;\r\n" + 
														"    }\r\n" + 
														"    else if (charBuffer == '>') {\r\n" + 
														"      payloadRead = true;\r\n" + 
														"      *payloadLength = charCounter - *topicLength - 2;\r\n" + 
														"      payload[*payloadLength] = char(0); //setze Abschlusszeichen für string/print\r\n" + 
														"    }\r\n" + 
														"    else if(charCounter > 100){\r\n" + 
														"      *topicLength = 0;\r\n" + 
														"      topic[0] = char(0); //setze Abschlusszeichen für string/print da keine korrekten Daten erhalten werden\r\n" + 
														"      *payloadLength = 0;\r\n" + 
														"      payload[0] = char(0); //setze Abschlusszeichen für string/print da keine korrekten Daten erhalten werden\r\n" + 
														"      break;\r\n" + 
														"    }\r\n" + 
														"    else {\r\n" + 
														"      if (!topicRead) {\r\n" + 
														"        topic[charCounter - 1] = charBuffer;\r\n" + 
														"      } else {\r\n" + 
														"        payload[charCounter - *topicLength - 2] = charBuffer;\r\n" + 
														"      }\r\n" + 
														"      charCounter++;\r\n" + 
														"    }\r\n" + 
														"  }\r\n" + 
														"};";
	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String topicLength = null;
		String topic = null;
		String payloadLength = null;
		String payload = null;
		String recBuffer = null;
			
		TranslatorBlock tbTopicLength = this.getRequiredTranslatorBlockAtSocket(0);
		topicLength = tbTopicLength.toCode().replaceAll("\\s*_.new\\b\\s*", "");

		
		TranslatorBlock tbTopic = this.getRequiredTranslatorBlockAtSocket(1);
		topic = tbTopic.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbPayloadLength = this.getRequiredTranslatorBlockAtSocket(2);
		payloadLength = tbPayloadLength.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		
		TranslatorBlock tbPayload = this.getRequiredTranslatorBlockAtSocket(3);
		payload = tbPayload.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		TranslatorBlock tbRecBuffer = this.getRequiredTranslatorBlockAtSocket(4);
		recBuffer = tbRecBuffer.toCode().replaceAll("\\s*_.new\\b\\s*", "");
		
		if (!(tbTopicLength instanceof VariableNumberBlock) && !(tbTopicLength instanceof LocalVariableNumberBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		if (!(tbTopic instanceof VariableStringBlock) && !(tbTopic instanceof LocalVariableStringBlock)  && !(tbTopic instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbPayloadLength instanceof VariableNumberBlock) && !(tbPayloadLength instanceof LocalVariableNumberBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
		
		if (!(tbPayload instanceof VariableStringBlock) && !(tbPayload instanceof LocalVariableStringBlock)  && !(tbPayload instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		if (!(tbRecBuffer instanceof VariableStringBlock) && !(tbRecBuffer instanceof LocalVariableStringBlock)  && !(tbRecBuffer instanceof ConstantStringBlock)) {
		    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addDefinitionCommand(readMQTTFrameFunction);
		
		String ret = "readMQTTFrame(&"+topicLength+", "+topic+", &"+payloadLength+", "+payload+", "+recBuffer+");";
		

		return codePrefix + ret + codeSuffix;
	}
}