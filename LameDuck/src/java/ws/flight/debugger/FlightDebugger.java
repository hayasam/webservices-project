/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.flight.debugger;

import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 *
 * @author Oguz Demir
 */
public class FlightDebugger {
    
    public static void log(PrintStream out, String operationName, Object input) {
        try {
            out.println(operationName + " is called with following params:");
            for(Field field : input.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                out.println(makeEntry(field.getName(), field.get(input).toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private static String makeEntry(String fieldName, String fieldValue) {
        return fieldName + ": " + fieldValue;
    }
    
}
