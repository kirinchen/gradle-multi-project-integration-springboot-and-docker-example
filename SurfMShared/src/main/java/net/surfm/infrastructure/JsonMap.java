package net.surfm.infrastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;

import net.surfm.exception.SurfmRuntimeException;

@SuppressWarnings("serial")
public class JsonMap extends HashMap<String, Object> {
	
	public static final String PATH_DOT = ">";
	
	public boolean isJsonMap(String k) {
		return get(k) instanceof Map;
	}
	
	public JsonMap getMap(String k) {
		Map child = (Map) get(k);
		if(child instanceof JsonMap) return (JsonMap)child;
		JsonMap ans = new JsonMap();
		for(Object ck : child.keySet()) {
			if(!(ck instanceof String)) throw new SurfmRuntimeException("this key must be string");
			ans.put(ck.toString(), child.get(ck));
		}
		return ans;
	}
	

	public void merge(Collection<MergePV> pvs ) {
		pvs.forEach(pv->{
			putByPath(pv.getPath(), pv.findVal());
		});
	}
	
	public Map<String,Object> parsePathValue(){
		Map<String,Object> ans = new HashMap<>();
		parsePathValueRecursively(StringUtils.EMPTY,this,ans);
		return ans;
	}	
	
	private void  parsePathValueRecursively(String _path,JsonMap _childMap,Map<String,Object> ans) {
		for(String k : _childMap.keySet()) {
			Object v = _childMap.get(k);
			String path = _path+k;
			if(_childMap.isJsonMap(k)) {
				parsePathValueRecursively(path+PATH_DOT,_childMap.getMap(k),ans);
			}else {
				ans.put(path, v);
			}
		}
	}	
	

	
	public Object getByPath(String path) {
		return findByPath(path, false,(jm,k)-> jm.get(k) );
	}
	
	@SuppressWarnings("unchecked")
	public void putByPath(String path,Object o) {
		findByPath(path,true,(jm,k)->{
			jm.put(k, o);
			return null;
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object findByPath(String path,boolean createPath,ChildAction<Map,String> ca) {
		String[] ps = path.split(PATH_DOT);
		Map jm = this;
		int i=0;
		while (i<ps.length-1) {
			if(createPath && !jm.containsKey(ps[i])) {
				jm.put(ps[i], new JsonMap());
			}
			jm = (Map)jm.get(ps[i]);
			i++;
		}
		return ca.accept(jm,ps[i]);
	}	
	
	@FunctionalInterface
	public interface ChildAction<M,K>{
		Object accept(M map , K key);
	}
	
	public interface MergePV{
		String getPath();
		Object findVal();
	}
	
	public static class MergePVO implements MergePV{

		private String path;
		private Object value;
	
		public MergePVO(String path, Object value) {
			this.path = path;
			this.value = value;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public Object findVal() {
			return value;
		}
		
	}
	
	public static JsonMap gen(Object o) {
		DozerBeanMapper dm = new DozerBeanMapper();
		return dm.map(o, JsonMap.class);
	}

}
