package net.surfm.account.dto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.CommUtils;
import net.surfm.infrastructure.JsonMap;

public enum FieldType {


	
	numericalInt(
				o -> Integer.parseInt(o.toString())+"",
				s->  Integer.parseInt(s)
				) ,
	
	numericalFloat(
				o -> Float.parseFloat(o.toString())+"",
				s -> Float.parseFloat(s)
				) ,
	
	bool(
			o -> ((Boolean) o)+"",
			s -> Boolean.parseBoolean(s)
		),
	
	enumed(
			o -> (String)o,
			s-> s
			) ,
	
	string(
			o -> (String)o,
			s->s
			) ,
	
	jsonObject(
			o -> CommUtils.toJsonSting(o),
			s -> CommUtils.toObjByJson(s,JsonMap.class)
			),
	
	@SuppressWarnings("rawtypes")
	jsonArray(
			o -> CommUtils.toJsonSting((Collection)o),
			s -> CommUtils.toObjByJson(s,Collection.class)
			)
	;
	
	
	private Function<Object,String> obj2StrFunc;
	private Function<String,Object> str2ObjFunc;
	
	
	private FieldType(Function<Object,String> _obj2StrFunc,Function<String,Object> _str2ObjFunc) {
		obj2StrFunc = _obj2StrFunc;
		str2ObjFunc = _str2ObjFunc;
	}
	

	
	
	public String convertAndValidateString(Object o) {
		try {
			return  obj2StrFunc.apply(o).toString();
		} catch (Exception e) {
			throw new SurfmRuntimeException("type="+this+" o="+o+ " this can not convert ", e);
		}
	}
	
	public Object parse(String s) {
		return str2ObjFunc.apply(s);
	}




	public static FieldType part(Object o,Set<String> evs) {
		if(o instanceof Integer) return numericalInt;
		if(o instanceof Float) return numericalFloat;
		if(o instanceof Boolean) return bool;
		if(o instanceof String)	return evs.contains(o) ? enumed : string;
		if( o instanceof Map)		return jsonObject;
		if( o instanceof Collection)		return jsonArray;
		throw new SurfmRuntimeException(new NullPointerException("not allow this type =" + o.getClass()+" o="+o));
	}	
	
}
