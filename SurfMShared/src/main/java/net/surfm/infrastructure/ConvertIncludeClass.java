package net.surfm.infrastructure;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author kirin
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ConvertIncludeClass {
	
	Class<?>[] include();
	

}
