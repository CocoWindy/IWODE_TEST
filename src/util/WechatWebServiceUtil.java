package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

import com.xiao.Socket.WebClient.KeyValuePair;

/**
 * 
 * @author Cody.yao
 *
 */
public class WechatWebServiceUtil {
	
	/**
	 * 微信公众号接口主机名
	 */
	private static final String HOST_URL = "https://api.weixin.qq.com";
	
	/**
	 * Get请求
	 * 
	 * @param String pathUrl
	 * 				路径名
	 * 
	 * @param KeyValuePaor[] pairs
	 * 				Get参数，键值对数组
	 * 
	 * @return
	 */
	public static String doGet(String pathUrl,KeyValuePair[] pairs){
		
		/**
		 * 拼接url
		 */
		String getUrl = HOST_URL + pathUrl + "?" + turnPairsToString(pairs);
		URL httpsURL = null;
		
		try {
			httpsURL = new URL(getUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**
		 * 建立https连接及参数设置
		 */
		HttpsURLConnection httpsConn = null;
		try {
			httpsConn = (HttpsURLConnection)httpsURL.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		httpsConn.setDoInput(true);
		httpsConn.setDoOutput(true);
		try {
			httpsConn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			httpsConn.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * 读取返回的数据
		 */
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder returnJson = new StringBuilder();
		String box;
		
		try {
			while((box = reader.readLine()) != null){
				returnJson.append(box);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpsConn.disconnect();
		
		return returnJson.toString();
	}
	
	public static String doPost(String pathUrl,String data){
		
		/**
		 * 拼接url
		 */
		String postUrl = HOST_URL + pathUrl;
		URL httpsURL = null;
		
		try {
			httpsURL = new URL(postUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**
		 * 建立https连接及参数设置
		 */
		HttpsURLConnection httpsConn = null;
		try {
			httpsConn = (HttpsURLConnection)httpsURL.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		httpsConn.setDoInput(true);
		httpsConn.setDoOutput(true);
		try {
			httpsConn.setRequestMethod("POST");
			
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DataOutputStream out = new DataOutputStream(httpsConn.getOutputStream());
			if(data != null){
				out.writeBytes(data);
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			httpsConn.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/**
		 * 读取返回的数据
		 */
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder returnJson = new StringBuilder();
		String box;
		
		try {
			while((box = reader.readLine()) != null){
				returnJson.append(box);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpsConn.disconnect();
		
		return returnJson.toString();
		
	}
	
	/**
	 * 将键值对转换成Get方式拼接字符串
	 * 
	 * @param KeyValuePair[] pairs
	 * 				要转换的键值对数组
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String turnPairsToString(KeyValuePair[] pairs){
		StringBuilder re = new StringBuilder();
		
		for(int i = 0; i < pairs.length; ++i){
			re.append(URLEncoder.encode(pairs[i].key));
			re.append("=");
			re.append(URLEncoder.encode(pairs[i].value));
			if(i != (pairs.length - 1)){
				re.append("&");
			}
		}
		
		return re.toString();
	}
}

