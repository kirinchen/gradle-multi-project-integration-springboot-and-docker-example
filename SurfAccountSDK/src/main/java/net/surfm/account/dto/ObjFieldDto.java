package net.surfm.account.dto;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.JsonMap;
import net.surfm.infrastructure.ReflectionTool;


/**
 * @see net.surfm.account.model.ObjField
 * @author kirin
 *
 */
public class ObjFieldDto {

	

	@Target(FIELD)
	@Retention(RUNTIME)
	public @interface At {
		
		boolean numDesc() default false;
		
		boolean autoType() default false;
		
		boolean fold() default false;
		
		FieldType type() default FieldType.string;
		
		FieldBehavior behavior() default FieldBehavior.Transaction;
		
		String[] valueEnums() default StringUtils.EMPTY ;

	}	
	
	private String name;
	private FieldType type;
	private FieldBehavior behavior;
	private Set<String> valueEnums = new HashSet<>();
	private boolean numDesc;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNumDesc() {
		return numDesc;
	}

	public void setNumDesc(boolean numDesc) {
		this.numDesc = numDesc;
	}



	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public Set<String> getValueEnums() {
		return valueEnums;
	}

	public void setValueEnums(Set<String> valueEnums) {
		this.valueEnums = valueEnums;
	}
	
	
	
	public FieldBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(FieldBehavior behavior) {
		this.behavior = behavior;
	}



	public static final Set<String> EMPTY_SET = new HashSet<>();
	
	
	public static List<ObjFieldDto> gen(Object o){
		List<ObjFieldDto> ans =new ArrayList<>();
		try {
			genRecursively(StringUtils.EMPTY,o,ans);
		} catch (Exception e) {
			throw new SurfmRuntimeException("genRecursively fails", e);
		}
		CommUtils.pl("ans size="+ans.size());
		return ans;
	}
	
	public static void genRecursively(String _path,Object o,List<ObjFieldDto> ans) throws IllegalArgumentException, IllegalAccessException{
		List<String> fNames= ReflectionTool.getFieldNames(o.getClass());
		
		for(String fName : fNames) {
			String path = _path+fName;
			At ant =  ReflectionTool.getAnnotation(o.getClass(), At.class, fName);
			if(safeGet(ant,a-> a.fold() , false)) {
				genRecursively(path+JsonMap.PATH_DOT,ReflectionTool.getValue(o, fName),ans);
			}else {
				ObjFieldDto dto = new ObjFieldDto();
				dto.setName(path);
				dto.setBehavior(safeGet(ant,a->a.behavior(),FieldBehavior.Transaction));
				dto.setNumDesc(safeGet(ant,a->a.numDesc(),false));
				dto.setValueEnums(safeGet(ant, a->covertSet(a.valueEnums()), EMPTY_SET ));
				if(safeGet(ant,a->a.autoType(),true)) {
					dto.setType(FieldType.part(ReflectionTool.getValue(o, fName),dto.getValueEnums() ));
				}else {
					dto.setType(ant.type());
				}
				ans.add(dto);
			}

		}
	}
	
	
	
	private static Set<String> covertSet(String[] ss){
		return Arrays.stream(ss).collect(Collectors.toSet());
	}
	
	public static <T> T safeGet(At _at,Function<At,T> func ,T org ) {
		return _at == null ? org : func.apply(_at);
	}
	
	

}
