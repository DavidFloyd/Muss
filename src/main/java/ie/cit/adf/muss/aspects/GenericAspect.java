package ie.cit.adf.muss.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class GenericAspect {

//TODO Implementar un Log para X o Y acciones. Al menos usa la funcionalidad... Por inutil que sea.
	
	@Before("execution(* follow*(..))")
	public void beforeFriends() {
//		System.out.println("beforeMethod IN");
//		System.out.println("beforeMethod OUT");
	}
	
	@After("execution(* follow*(..))")
	public void afterFriends() {
//		System.out.println("afterMethod IN");
//		System.out.println("afterMethod OUT");
	}
	
}
