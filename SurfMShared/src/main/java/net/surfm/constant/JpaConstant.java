package net.surfm.constant;


/**
 * 
 * @author kirin
 *
 */
public class JpaConstant {
	
	
	
	public static final String COLUMN_DEFIN_ID = "int(12) unsigned";
	public static final String COLUMN_DEFIN_INT = "int(18) default 0";
	
	
	public static final int COLUMN_DEFIN_UID_SIZE = 32;
	public static final String COLUMN_DEFIN_UID = "varchar("+COLUMN_DEFIN_UID_SIZE+")";
	public static final String COLUMN_DEFIN_SMALL_TEXT = "varchar(50)";
	public static final String COLUMN_DEFIN_MEDIUM_TEXT = "varchar(255)";
	public static final String COLUMN_DEFIN_LARGE_TEXT = "varchar(1000)";
	public static final String COLUMN_DEFIN_XLARGE_TEXT = "text";
	public static final String COLUMN_DEFIN_BOOLEAN = "bit default 0";
	public static final String COLUMN_DEFIN_DOUBLE = "double(24,12) default 0.00";
	public static final String COLUMN_DEFIN_DATE = "datetime ";
	
}
