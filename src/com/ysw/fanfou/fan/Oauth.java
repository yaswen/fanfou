package com.ysw.fanfou.fan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.ysw.fanfou.fan.*;

import sun.misc.BASE64Encoder;

public class Oauth {
	public static void main(String args[]) {

		Oauth oauth = new Oauth();
		String timestamp=oauth.oauth_timestamp;
		String nonce = oauth.oauth_nonce;
		System.out.println("随机串："+nonce);// 输出随机字符串
		System.out.println("时间戳："+timestamp);// 输出时间戳
		try {
//			String ceshijiami = hmacsha1("abc", "123");
//			ceshijiami=URLEncoder.encode(ceshijiami, "utf-8");
//			System.out.println("测试加密算法：" + ceshijiami);
			String method="GET";
			String url=oauth.requst_token_url;
			String basestring = oauth.BaseString(method,url,oauth.dict());
			System.out.println("basestring:"+basestring);
			String oauth_signature = hmacsha1(basestring, Fanfou.oauth_consumer_secret+"&");
			System.out.println(oauth_signature);
			oauth_signature=URLEncoder.encode(oauth_signature, "utf-8");
			System.out.println(oauth_signature);
			String params = "oauth_consumer_key=" + oauth.oauth_consumer_key + 
					"&oauth_nonce=" + nonce + 
					"&oauth_signature=" + oauth_signature+
					"&oauth_signature_method=HMAC-SHA1" + 
					"&oauth_timestamp=" + timestamp;
			// params = URLEncoder.encode(params, "utf-8");
			System.out.println("下面正式进行请求");
			String o = sendGet(oauth.requst_token_url,params);
			System.out.println(o);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
	}
	String oauth_request_method = "GET";//获取request_token采用GET方式
	String requst_token_url =Fanfou.requst_token_url;// 获取request_token的url
	String oauth_callback =Fanfou.oauth_callback;//第三步获取access_token的时候的callback服务
	String oauth_signature_method = "HMAC-SHA1";//签名方法，饭否只支持HMAC-SHA1
	String oauth_consumer_key =Fanfou.oauth_consumer_key;// 打篮球应用的key
	String oauth_consumer_secret =Fanfou.oauth_consumer_secret;// 打篮球应用的secret
	String oauth_nonce = set_nonce();//随机生成的随机字符串
	String oauth_timestamp = set_timestamp();//生成的时间戳
	
	/*
	 * 签名的基本伪代码过程如 base_string = httpMethod + '&' + url_encode(base_url) + '&' +
	 * sorted(querysting.items()).join('&') sig = signature(base_string) #得到签名
	 */
	/*public String set_basestring() throws UnsupportedEncodingException {
		String bss;// base String1
		bss = oauth_request_method + "&" + URLEncoder.encode(requst_token_url, "utf-8") + "&";
		String bsss = "oauth_callback=" + URLEncoder.encode(oauth_callback, "utf-8") + "&oauth_consumer_key="
				+ oauth_consumer_key + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method="
				+ oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=" + oauth_version;
		bsss = URLEncoder.encode(bsss, "utf-8");
		return bss + bsss;
	}*/
/*
	public String set_basestring_f() throws UnsupportedEncodingException {
		String bss;// base String1 第一段
		bss = oauth_request_method + "&" + URLEncoder.encode(requst_token_url, "utf-8")+"&";
		String bsss = // base String2 第二段
				"oauth_consumer_key=" + oauth_consumer_key + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method="
						+ oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp;

		bsss = URLEncoder.encode(bsss, "utf-8");// 对bsss进行encode处理，饭否的签名需要这一步（借鉴可可
		return bss + bsss;
	}
	*/
	/**
	 * 生成baseString
	 * @param method 本次请求的方式（GET或者POST）
	 * @param url 本次请求的url地址（本方法会对其进行encode处理）
	 * @param dict 本次请求的参数列表，按照k1=v1&k2=v2形式连接，并且经过按照参数名排序的。（本方法会对其进行encode处理）
	 * @return 返回连接好的baseString
	 * @throws UnsupportedEncodingException
	 */
	public String BaseString(String method,String url,String dict) throws UnsupportedEncodingException {
		String baseString;// base String 
		baseString = method + "&" + URLEncoder.encode(url, "utf-8")+"&"+URLEncoder.encode(dict,"utf-8");
		// 对参数列表进行encode处理，饭否的签名需要这一步（借鉴可可和跑跑）
		return baseString;
	}
	
	/**
	 * 第一次签名的参数列表
	 * 获取request_token时用
	 * @return 格式修改为要求的参数列表字符串
	 */
	public String dict() {
		String bsss = 
			"oauth_consumer_key"+"=" + oauth_consumer_key + 
			"&"+"oauth_nonce"+"=" + this.oauth_nonce +
			"&"+"oauth_signature_method"+"=" + oauth_signature_method + 
			"&"+"oauth_timestamp"+"=" + set_timestamp();
		return bsss;
	}
	/**
	 * 第二次签名的参数列表
	 * 获取access_token时用
	 * @return 格式修改为要求的参数列表字符串
	 */
	public String dict(String ockey,String method,String otoken,String nonce,String timestamp) {
		String bsss = 
			"oauth_consumer_key"+"=" + ockey + 
			"&"+"oauth_nonce"+"=" + nonce +
			"&"+"oauth_signature_method"+"=" + method + 
			"&"+"oauth_timestamp"+"=" + timestamp+
			"&"+"oauth_token"+"="+otoken;
		return bsss;
	}
	/**
	 * 发消息用的参数列表
	 * @param ockey 应用的consumer_key
	 * @param method 签名方法HMAC-SHA1
	 * @param otoken 发消息要传access_token
	 * @param nonce 随机字符串
	 * @param timestamp 时间戳
	 * @param status 要发的消息
	 * @return
	 */
	/**
	 * 将参数排序并且连接成key1=value1&key2=value2的形式
	 * @param tmp 包含所有参数键值对的TreeMap
	 * @return 返回按key排序并且写成key1=value1&key2=value2格式的字符串
	 */
	public static String getDict(TreeMap<String,String> tmp) {

		System.out.println(tmp);
		String t="";
		Iterator titer=tmp.entrySet().iterator();  
        while(titer.hasNext()){  
            Map.Entry ent=(Map.Entry )titer.next();  
            String keyt=ent.getKey().toString();  
            String valuet=ent.getValue().toString();  
            System.out.println(keyt+"="+valuet);  
            t+=keyt+"="+valuet+"&";
        }
        return(t.substring(0, t.length()-1));
	}

	/**
	 * 时间戳生成方法
	 * @return 返回10位精确到秒的时间戳
	 */
	public static String set_timestamp() {
		Date date = new Date();
		long time = date.getTime();
		return (time + "").substring(0, 10);// 返回时间戳前十位。
	}
	/**
	 * nonce生成方法
	 * @return 返回一串随机字符串
	 */
	public static String set_nonce() {
		//String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		String base = "0123456789";//使用数字
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	/**
	 * HMAC-SHA1加密算法
	 * @param data	要加密的内容
	 * @param key	加密的key
	 * @return		返回加密后的字符串
	 */
	public static String hmacsha1(String data, String key) {
		byte[] byteHMAC = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ignore) {
		}
		String oauth = new BASE64Encoder().encode(byteHMAC);
		return oauth;
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			//connection.setRequestProperty("accept", "*/*");
			//connection.setRequestProperty("connection", "Keep-Alive");
			//connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			/*// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("accept", "*/*");
            //conn.setRequestProperty("connection", "Keep-Alive");
            //conn.setRequestProperty("user-agent",
                    //"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}

