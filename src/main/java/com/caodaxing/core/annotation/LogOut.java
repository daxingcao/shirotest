package com.caodaxing.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOut {
	
	String value() default "";
	
	/**
	 * This method is used to set whether or not to save the log, 
	 * and when the value is set to true, the log is saved to the database, 
	 * which defaults to false
	 * @return
	 */
	boolean save() default false;

}
