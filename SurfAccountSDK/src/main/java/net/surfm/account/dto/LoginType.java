package net.surfm.account.dto;

public enum LoginType {
	FB, GOOGLE, SLEF, STEAM , NONE;
	
	public String getMailPattern(){
		switch (this) {
		case STEAM:
			return "steam%s@steam.com";
		case FB :
			return "fb%s@fb.com";
		}
		throw new NullPointerException("not support ="+this);
		
	}
	
	public String getField(){
		switch (this) {
		case STEAM:
			return "steamId";
		case FB:
			return "fbId";
		}
		throw new NullPointerException("not support ="+this);
	}

	public Object toFieldType(String uid) {
		switch (this) {
		case STEAM:
			return  Integer.parseInt(uid);
		case FB:
			return uid;
		}
		throw new NullPointerException("not support ="+this);
	}
	 
	 
}