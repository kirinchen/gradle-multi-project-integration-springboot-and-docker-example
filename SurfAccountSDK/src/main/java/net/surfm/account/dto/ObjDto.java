package net.surfm.account.dto;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import net.surfm.infrastructure.ConvertFieldSkip;
import net.surfm.infrastructure.JsonMap;

/**
 * @see ObjFieldDto
 * @author Kirin
 *
 */
public class ObjDto {
	
	@Documented
	@Target(TYPE)
	@Retention(RUNTIME)
	public @interface At {

		String name() ;
		
		boolean systemed();
		
		boolean combined();
	}	
	
	private String name;
	private JsonMap data;
	private boolean systemed;
	private boolean combined;
	
	
	@ConvertFieldSkip
	private List<ObjFieldDto> fields = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JsonMap getData() {
		return data;
	}
	public void setData(JsonMap data) {
		this.data = data;
	}
	public boolean isSystemed() {
		return systemed;
	}
	public void setSystemed(boolean systemed) {
		this.systemed = systemed;
	}
	public boolean isCombined() {
		return combined;
	}
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	public List<ObjFieldDto> getFields() {
		return fields;
	}
	public void setFields(List<ObjFieldDto> fields) {
		this.fields = fields;
	}
	
	
	public static ObjDto gen(Object obj) {
		ObjDto.At objat =obj.getClass().getAnnotation(ObjDto.At.class);
		
		ObjDto ans = new ObjDto();
		ans.setData(JsonMap.gen(obj));
		ans.setCombined(objat.combined());
		ans.setSystemed(objat.systemed());
		ans.setName(objat.name());
		ans.setFields(ObjFieldDto.gen(obj));
		return ans;
	}	
	
	
	
}
