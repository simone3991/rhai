package it.rhai.routines;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
/**
 * This annotation marks a class as an executable 
 * one, and identify them with a short id
 * 
 * @author simone
 *
 */
public @interface Executable {
	public String id();
}
