/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.hotel.builder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VAIO
 */
public class Builders {
    
    /**
     * Creates a builder instance with given type.
     * @param <T>
     * @param clazz
     * @return 
     */
    public static <T> T newBuilder(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            Logger.getLogger(Builders.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
