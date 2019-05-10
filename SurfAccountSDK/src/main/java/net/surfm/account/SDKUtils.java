package net.surfm.account;

import net.surfm.constant.JpaConstant;
import net.surfm.infrastructure.CommUtils;

public class SDKUtils {


	public static final String SYS_APP_UID = "@SYSTEM";
	

	public static String getUserObjFieldUid(String userItemUid,String fieldUid) {
		String hashText = userItemUid+"@"+fieldUid;
		return getUid(hashText);		
	}
	
	public static String getSysObjUid(String name) {
		return getObjUid(SYS_APP_UID,name);
	}
	
	public static String getObjUid(String appOid, String name) {
		String hashText = appOid+"@"+name;
		return getUid(hashText);
	}
	
	public static String getAppUid(String text) {
		return getUid(text);
	}
	
	public static String getUid(String text) {
		String sha1 = CommUtils.sha1(text);
		return sha1.substring(0, JpaConstant.COLUMN_DEFIN_UID_SIZE);
	}
	
	public static String getObjFieldUid(String objUid,String fieldName) {
		String hashText = objUid+"@"+fieldName;
		return getUid(hashText);
	}
	

	
	
	
	



}
