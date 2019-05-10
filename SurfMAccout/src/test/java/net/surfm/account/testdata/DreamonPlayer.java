package net.surfm.account.testdata;

import net.surfm.account.dto.ObjDto;

@ObjDto.At(combined = false, name = "DreamonPlayer", systemed = false)
public class DreamonPlayer {
	
	private String name = "PlayerShowName";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
