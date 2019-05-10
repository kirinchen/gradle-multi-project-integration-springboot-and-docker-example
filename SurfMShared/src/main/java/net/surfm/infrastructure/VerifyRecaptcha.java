package net.surfm.infrastructure;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;

import net.surfm.exception.BotcodeException;
import net.surfm.exception.SurfmRuntimeException;

public class VerifyRecaptcha {

	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6LeuuXsUAAAAANktyKhty1_dluo_fIBLqSvR_TSR";
	private final static String USER_AGENT = "Mozilla/5.0";

	public static void check(DebotCodeGot deGot) {
		if(deGot.skipble()) return ;
		if(verify(deGot)) return;
		throw new BotcodeException();
	}
	
	public static void check(String gRecaptchaResponse) {
		if(verify(gRecaptchaResponse)) return ;
		throw new BotcodeException();
	}
	
	public static boolean verify(DebotCodeGot deGot)  {
		if(deGot.skipble()) return true;
		return verify(deGot.getRecaptchaResponse());
	}
	
	public static boolean verify(String gRecaptchaResponse)  {
		if (StringUtils.isBlank(gRecaptchaResponse)) {
			throw new BotcodeException();
		}

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

			// parse JSON response and return 'success' value
			
			javax.json.JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			return  jsonObject.getBoolean("success") ;
		} catch (Exception e) {
			throw new SurfmRuntimeException(e);
		}
	}
}