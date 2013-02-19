package net.sf.orcc.tom.transform;

import net.sf.orcc.df.*;
import net.sf.orcc.ir.*;
import net.sf.orcc.util.OrccLogger;

/**
* This simple class print the list of procedures named "untagged_0" in an actor
*/
public class SimpleProcedureExample {

	private static boolean tom_equal_term_char(char t1, char t2) {return  t1==t2 ;}private static boolean tom_is_sort_char(char t) {return  true ;} private static boolean tom_equal_term_String(String t1, String t2) {return  t1.equals(t2) ;}private static boolean tom_is_sort_String(String t) {return  t instanceof String ;} private static boolean tom_equal_term_boolean(boolean t1, boolean t2) {return  t1==t2 ;}private static boolean tom_is_sort_boolean(boolean t) {return  true ;} private static boolean tom_equal_term_Proc(Object l1, Object l2) {return  l1.equals(l2) || l1 == l2;}private static boolean tom_is_sort_Proc(Object t) {return  t instanceof Procedure ;}private static boolean tom_is_fun_sym_proc( Procedure  t) {return t instanceof Procedure;}private static  String  tom_get_slot_proc_name( Procedure  t) {return ((Procedure)t).getName();}private static  boolean  tom_get_slot_proc_nat( Procedure  t) {return  ((Procedure)t).isNative();}  

	public void exec(Actor actor) {

		OrccLogger.traceln(actor.getName());
		
		for(Procedure procedure : actor.getProcs()) {
			{{if (tom_is_sort_Proc(procedure)) {if (tom_is_fun_sym_proc((( Procedure )procedure))) {if (tom_equal_term_String("untagged_0", tom_get_slot_proc_name((( Procedure )procedure)))) {

					OrccLogger.traceln(procedure.getName());
				}}}}}

		}
	}
}
