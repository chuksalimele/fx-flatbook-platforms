/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expert.ui.controls;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class EALabel extends JLabel implements InvocationHandler {

    private final Object target;

    public EALabel(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy invocation");
        if (SwingUtilities.isEventDispatchThread()) {
            return method.invoke(target, args);
        } else {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    method.invoke(target, args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return null;
        }
    }
    
    public static void main(String args[]){
        
        EALabel label = new EALabel("This is label");
        label.setText("chuks");
        
        System.out.println(label.getText());
        
    }
}
