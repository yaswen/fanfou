package com.ysw.fanfou.demo;

import java.io.UnsupportedEncodingException;

public class UTFDemo {
	public static void main(String args[]) {
		String a=gbk(0xD7F9);
		System.out.println(a);
	}

	public static void getutf() {
		String str = null;
		try {
			str = new String("千与千寻大家".getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cnToUnicode("锟斤拷"));

	}

	public static String gbk(int i) {
		byte[] bytes = inToBytes(i);
		try {
			String str=new String(bytes, "GBK");
			return str;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		/*try {
			System.out.println("啊".getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public static byte[] inToBytes(int i) {
		byte[] b = new byte[2];
		b[0] = (byte) (i >>> 8);
		b[1] = (byte) i;
		return b;
	}

	private static String cnToUnicode(String cn) {
		char[] chars = cn.toCharArray();
		String returnStr = "";
		for (int i = 0; i < chars.length; i++) {
			returnStr += "\\u" + Integer.toString(chars[i], 16);
		}
		return returnStr;
	}

	private static String unicodeToCn(String unicode) {
		/** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格 */
		String[] strs = unicode.split("\\\\u");
		String returnStr = "";// 汉字范围 \u4e00-\u9fa5 (中文)
		// 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
		for (int i = 1; i < strs.length; i++) {
			returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
		}
		return returnStr;
	}
}
