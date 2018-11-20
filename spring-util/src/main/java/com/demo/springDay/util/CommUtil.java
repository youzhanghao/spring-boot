package com.demo.springUtil.util;

import com.alibaba.fastjson.JSONObject;
import com.demo.springUtil.xss.MyX509TrustManager;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import java.util.Base64;*/


/**
 * The Class CommUtil.
 */
public class CommUtil {
	protected static final Log logger = LogFactory.getLog(CommUtil.class);

	/**
	 * Instantiates a new comm util.
	 */
	private CommUtil() {
	}


	public static double add(Object a, Object b) {
		double ret = 0.0;
		BigDecimal e = new BigDecimal(CommUtil.null2Double(a));
		BigDecimal f = new BigDecimal(CommUtil.null2Double(b));
		ret = e.add(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Double.valueOf(df.format(ret));
	}

	/**
	 * Null2 int.
	 *
	 * @param s
	 *            the s
	 * @return the int
	 */
	public static int null2Int(Object s) {
		int v = 0;
		if (s != null) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * Parses the date.
	 *
	 * @param type
	 *            the type
	 * @param date
	 *            the date
	 * @return the int
	 */
	public static int parseDate(String type, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("y")) {
			return cal.get(Calendar.YEAR);
		}
		if (type.equals("M")) {
			return cal.get(Calendar.MONTH) + 1;
		}
		if (type.equals("d")) {
			return cal.get(Calendar.DAY_OF_MONTH);
		}
		if (type.equals("H")) {
			return cal.get(Calendar.HOUR_OF_DAY);
		}
		if (type.equals("m")) {
			return cal.get(Calendar.MINUTE);
		}
		if (type.equals("s")) {
			return cal.get(Calendar.SECOND);
		}
		return 0;
	}

	/**
	 * Format date.
	 *
	 * @param s
	 *            the s
	 * @param format
	 *            the format
	 * @return the java.util. date
	 */
	public static Date formatDate(String s, String format) {
		Date d = null;
		try {
			SimpleDateFormat dFormat = new SimpleDateFormat(format);
			d = dFormat.parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	/**
	 * Format time.
	 *
	 * @param format
	 *            the format
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String formatTime(String format, Object v) {
		if (v == null) {
			return null;
		}
		if (v.equals("")) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	/**
	 * Format long date.
	 *
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String formatLongDate(Object v) {
		if (v == null || v.equals("")) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(v);
	}

	/**
	 * Format short date.
	 *
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String formatShortDate(Object v) {
		if (v == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(v);
	}

	/**
	 * Null2 float.
	 *
	 * @param s
	 *            the s
	 * @return the float
	 */
	public static float null2Float(Object s) {
		float v = 0.0f;
		if (s != null) {
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * Null2 double.
	 *
	 * @param s
	 *            the s
	 * @return the double
	 */
	public static double null2Double(Object s) {
		double v = 0.0;
		if (s != null) {
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * Null2 boolean.
	 *
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null) {
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * Null2 string.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	/**
	 * Null2 long.
	 *
	 * @param s
	 *            the s
	 * @return the long
	 */
	public static Long null2Long(Object s) {
		Long v = -1L;
		if (s != null) {
			try {
				v = Long.parseLong(s.toString());
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * Null2 big decimal.
	 *
	 * @param s
	 *            the s
	 * @return the big decimal
	 */
	public static BigDecimal null2BigDecimal(Object s) {
		BigDecimal v = new BigDecimal("0");
		if (s != null) {
			try {
				v = new BigDecimal(null2String(s));
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * 是否是手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if(StringUtils.isNotBlank(mobiles) && mobiles.length() == 11){
			String mobile = "^(1[3-9][0-9])\\d{8}$";
			Pattern p = Pattern.compile(mobile);
			Matcher m = p.matcher(mobiles);
			return m.matches();
		}else{
			return false;
		}
	}
	
	/**
	 * 获取某个超链接中的参数值
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getUrlParam(String url,String name){
		String urlzz = "(^|\\?|&)"+ name +"=([^&]*)(&|$)";
		Pattern p = Pattern.compile(urlzz);
		Matcher m = p.matcher(url);
		while(m.find()){
			return m.group();
	    }
		return null;
	}
	
	public static String getOrderName(String tableAlias,String name){
    	if("userId".equalsIgnoreCase(name)){
    		name = "user_id";
    	}else if("addTime".equalsIgnoreCase(name)){
    		name = "add_time";
    	}
    	if(StringUtils.isNotBlank(tableAlias)){
    		tableAlias = (tableAlias + ".");
    		name = tableAlias + name;
    	}
    	
    	return name;
    }
	
	/**
     * 发送https请求，并返还json object
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject processHttpsRequestAndReturnJSONObject(String requestUrl, String requestMethod, String outputStr) {
        String response = httpsRequest(requestUrl, requestMethod, outputStr,null);
        JSONObject jsonObject = null;
        if (StringUtils.isNotBlank(response)) {
            jsonObject = JSONObject.parseObject(response);
        }
        return jsonObject;
    }
    /**
     * 发送https请求，并返还json object
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject processHttpsRequestAndReturnJSONObject_Colud(String requestUrl, String requestMethod, String outputStr, String token) {
        String response = httpsRequest(requestUrl, requestMethod, outputStr,token);
        JSONObject jsonObject = null;
        if (StringUtils.isNotBlank(response)) {
            jsonObject = JSONObject.parseObject(response);
        }
        return jsonObject;
    }
	/**
     * 发送https请求，并返还json object
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return String 请求返还结果
     */

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr,String token) {
        String response = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn;
            if(requestUrl.startsWith("https://")){
            	HttpsURLConnection conns = (HttpsURLConnection) url.openConnection();
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = {new MyX509TrustManager()};
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();
            	conns.setSSLSocketFactory(ssf);
            	conn = conns;	
            }else{
                conn = (HttpURLConnection) url.openConnection();
            }
            if(StringUtils.isNotBlank(token)){
                conn.setRequestProperty("token",token);
                if (StringUtils.isNotBlank(outputStr)) {
	        		if(JSONUtils.mayBeJSON(outputStr)){
	        			conn.setRequestProperty("Content-Type", "application/json");
	        		}
	        	}
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (StringUtils.isNotBlank(outputStr)) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            response = buffer.toString();
        } catch (ConnectException e) {
            logger.error("连接超时：{}", e);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return response;
    }
    
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    
    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }
    
    /**
     * 将map中的key全部转换为驼峰命名
     * @param map
     * @return
     */
    public static Map<String, Object> changeKeyNamesToCamel(Map<String, Object> map){
    	Map<String, Object> result = new HashMap<>();
    	for (String key : map.keySet()) {
    		Object value = map.get(key);
			key = underline2Camel(key, true);
			result.put(key, value);
		}
    	return result;
    }

	public static <T> T convertMapToEntity(Map<String,Object> map, Class<T> claz){
		map = CommUtil.changeKeyNamesToCamel(map);
		try {
			T obj = (T) claz.newInstance();
			List<String> keys = new ArrayList<>();
			for(String key:map.keySet()){
				if(map.get(key) == null){
					keys.add(key);
				}
			}
			for(String key: keys){
				map.remove(key);
			}
			BeanUtils.populate(obj, map);
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * map集合转实体类集合
	 */
	public static <T> List<T> mapToEntity(List<Map<String,Object>> list, Class<T> claz){
		List<T> dataList = new ArrayList<>();
		if(list != null && !list.isEmpty()){
			for (Map<String, Object> map : list) {
				map = CommUtil.changeKeyNamesToCamel(map);
				try {
					T obj = (T) claz.newInstance();
					List<String> keys = new ArrayList<>();
					for(String key:map.keySet()){
						if(map.get(key) == null){
							keys.add(key);
						}
					}
					for(String key: keys){
						map.remove(key);
					}
					BeanUtils.populate(obj, map);
					dataList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
}
