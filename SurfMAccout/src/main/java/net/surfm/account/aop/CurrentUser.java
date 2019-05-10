package net.surfm.account.aop;
import java.lang.annotation.*;

/**
 * 
 * @author kirin
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}