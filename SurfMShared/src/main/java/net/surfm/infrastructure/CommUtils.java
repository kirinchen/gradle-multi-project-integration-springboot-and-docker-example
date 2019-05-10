package net.surfm.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import net.surfm.constant.RandomConstant;
import net.surfm.exception.SurfmRuntimeException;

@Component
public class CommUtils {

	@Inject
	private ApplicationContext context;

	private static SimpleDateFormat ISO_DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	private static  ObjectMapper JSON_MAPPER = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.setDateFormat(new StdDateFormat());

	public <T> T findBean(Class<T> cls, String name, T de) {
		return context.containsBean(name) ? context.getBean(name, cls) : de;
	}

	public static String genDateISOText(Date date) {
		return ISO_DATE_FORMATE.format(date);
	}

	public static Date parseByISOText(String iso) {
		try {
			return ISO_DATE_FORMATE.parse(iso);
		} catch (ParseException e) {
			throw new SurfmRuntimeException("this not right text=" + iso);
		}
	}
	
	public static String toJsonSting(Object o){
		try {
			return JSON_MAPPER.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new SurfmRuntimeException(e);
		}
	}
	
	public static <T> T toObjByJson(String o,Class<T> clz){
		try {
			return JSON_MAPPER.readValue(o, clz);
		} catch (Exception e) {
			throw new SurfmRuntimeException(e);
		}
	}	

	public static String sha1(String input) {
		return DigestUtils.sha1Hex(input);
	}

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			// System.out.println("encrypted string: " +
			// Base64.encodeBase64String(encrypted));

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String encodeBase64(String s) {
		try {
			return Base64.encodeBase64String(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new SurfmRuntimeException(e);
		}
	}

	public static String decodeBase64(String s) {
		try {
			return new String(Base64.decodeBase64(s), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new SurfmRuntimeException(e);
		}
	}

	@SafeVarargs
	public static <T> T[] typeArray(T... ts) {
		return ts;
	}

	public static void pl(Object o) {
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		System.out.println(String.format("%s.%s (%s) : %s", className, methodName, lineNumber, o));
	}

	public static void plAll(Object o) {
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		String allMsg = Arrays.stream(Thread.currentThread().getStackTrace()).skip(0).map(StackTraceElement::toString)
				.reduce((s1, s2) -> s1 + "\n	at " + s2).get();

		System.out.println(String.format("%s.%s (%s) : %s \n %s", className, methodName, lineNumber, o, allMsg));
	}

	public static String randomUid(int size) {
		String s = RandomStringUtils.random(size, RandomConstant.ABC123_STRING);
		return s;
	}

	public static int clampMath(int value, int start, int end) {
		int length = (end - start);
		int d = value % length;
		return d >= 0 ? start + d : end + d;
	}

	public static void delay(float d) {
		try {
			Thread.sleep((long) (d * 1000l));
		} catch (InterruptedException ie) {
			throw new SurfmRuntimeException(ie);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SurfmRuntimeException(e);
		}
	}

	public static Properties loadProperties(String configFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configFile));
			return properties;
		} catch (IOException e) {
			throw new SurfmRuntimeException(e);
		}
	}

	public static String replaceJSHolder(String s, String... args) {
		// String s = "Hey {0}, you are {1}.";
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i = 0; i < args.length; i++) {
			hm.put(i, args[i]);
		}
		Pattern p = Pattern.compile("(\\{\\d+\\})");
		Matcher m = p.matcher(s);
		while (m.find()) {
			//System.out.println(m.group());
			String val1 = m.group().replace("{", "").replace("}", "");
			// System.out.println(val1);
			s = (s.replace(m.group(), hm.get(Integer.parseInt(val1))));
			// System.out.println(s);
		}
		return s;
	}
	
	
	

	/*public static void main(String[] args) {
		String s = replaceJSHolder("Hey {0}, you are {1}.", "QQ", "CC");
		System.out.println(s);

	}*/

}
