package net.surfm.account.testdata;


import net.surfm.account.dto.ObjDto;
import net.surfm.account.dto.ObjFieldDto;

@ObjDto.At(combined = false, name = DreamonConstant.OBJ_NAME_PET, systemed = false)
public class PetObj {

	private String name = "PetName";
	@ObjFieldDto.At(fold = true)
	private PetAttr attr = new PetAttr();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PetAttr getAttr() {
		return attr;
	}

	public void setAttr(PetAttr attr) {
		this.attr = attr;
	}



}


