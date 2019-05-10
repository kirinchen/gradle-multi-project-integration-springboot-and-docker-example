package net.surfm.infrastructure;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import net.surfm.exception.SurfmRuntimeException;

/**
 * 
 * @author kirin
 *
 * @param <DTO>
 * @param <ENTITY>
 */
public class SkipbleConverter {

	public <I, R> R convert(I i, R r, String skipKey) {

		Field[] rkeys = i.getClass().getDeclaredFields();
		for (Field iF : rkeys) {

			setupOneValue(iF, i, r, skipKey);
		}
		return r;
	}

	private <I, R> void setupOneValue(Field iF, I i, R r, String skipKey) {
		try {
			if (isCopyed(iF, skipKey)) {
				iF.setAccessible(true);
				Class rc = r.getClass();
				if (!ReflectionTool.hasFieldKey(rc, iF.getName())) {
					return;
				}
				Object o = iF.get(i);
				ReflectionTool.putFieldValue(r, iF.getName(), o);
			}
		} catch (Exception e) {
			handleExceptionConvert(e, iF, i, r);
		}
	}

	protected <I, R> void handleExceptionConvert(Exception e, Field f, I i, R r) {
		throw new SurfmRuntimeException("fName="+f.getName()+" f=" + f + " i=" + i + " r=" + r, e);
	}

	private boolean isCopyed(Field f, String skipKey) {
		ConvertFieldSkip dfe = f.getAnnotation(ConvertFieldSkip.class);
		return dfe == null || dfe.skipKeys().length == 0
				|| !Arrays.stream(dfe.skipKeys()).anyMatch(s -> StringUtils.equals(skipKey, s));
	}

}
