package net.surfm.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.surfm.exception.SurfmRuntimeException;

public abstract class MapConvert<T,R> implements Function<T, R> {

	@Override
	public R apply(T t) {
		R r = newResultInstance();
		try {
			reflection(t, r);
		} catch (Exception e) {
			throw new SurfmRuntimeException(e);
		}
		convertCustom(t,r);
		return r;
	}
	
	protected void convertCustom(T t, R r) {
		
	}

	private void reflection(T t,R r) throws IllegalArgumentException, IllegalAccessException{
		List<String> names= listNames(r);
		for(String n : names){
			if(ReflectionTool.hasFieldKey(t.getClass(), n)){
				Object o = ReflectionTool.getValue(t, n);
				if(o!=null){
					ReflectionTool.putFieldValue(r, n, o);
				}
			}
		}
	}
	
	private List<String> listNames(R r){
		ConvertIncludeClass cic =r.getClass().getAnnotation(ConvertIncludeClass.class);
		if(cic==null){
			return ReflectionTool.getFieldNamesNegative(r.getClass(), ConvertFieldSkip.class);
		}else{
			List<String> ans = new ArrayList<>();
			for(Class cs : cic.include()){
				ans.addAll(ReflectionTool.getFieldNamesNegative(cs, ConvertFieldSkip.class));
			}
			return ans;
		}
		
	}

	protected abstract R newResultInstance() ;

}
