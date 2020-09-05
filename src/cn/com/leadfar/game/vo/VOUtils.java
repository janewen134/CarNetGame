package cn.com.leadfar.game.vo;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

public class VOUtils {
	
	/**
	 * ��һ������ת��ΪJSON��ʽ�Ĵ�
	 * @param vo Ҫת����VO����
	 * @return ת������ַ���
	 */
	public static String convertVO2String(Object vo){
		
		try {
			return JSONMapper.toJSON(vo).render(false);
		} catch (MapperException e) {
			throw new RuntimeException("�Ѷ���"+vo+"��ת��Ϊ�ַ�����ʱ�����������",e);
		}
		
	}
	
	/**
	 * ��һ��JSON��ʽ���ַ���ת��ΪJava����
	 * @param message Ҫת����JSON��ʽ���ַ���
	 * @param destClass Ҫ�����JSON��ʽ���ַ���ת��Ϊʲô���͵Ķ���
	 * @return ת��֮���Java����
	 */
	public static Object convertString2VO(String message,Class destClass){
		
		try {
			//�Ƚ����ַ���Ϊһ��JSONValue
			JSONValue value = new JSONParser(new StringReader(message)).nextValue();
			return JSONMapper.toJava(value, destClass);
		} catch (Exception e) {
			throw new RuntimeException("�ڰ��ַ�����"+message+"��ת��Ϊ��"+destClass+"�����͵Ķ���ʱ�������쳣" +
					"������������ַ�����ʽ���ԣ���������",e);
		}
	}
}
