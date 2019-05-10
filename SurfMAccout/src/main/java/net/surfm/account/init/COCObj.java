package net.surfm.account.init;

import net.surfm.account.SDKContant;
import net.surfm.account.dto.ObjDto;



@ObjDto.At(name=SDKContant.COC_OBJ_NAME , combined = true , systemed = true)
public class COCObj {

	
	private String fullName = "Crypto Object Coin";
	private String shortName = "COC";

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
