package expert.ui;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SwingProxy {

    public interface TextComponent {
        void setText(String text);
        String getText();
    }

    public static TextComponent createEDTProxy(JLabel label) {
        return (TextComponent) Proxy.newProxyInstance(
                TextComponent.class.getClassLoader(),
                new Class<?>[]{TextComponent.class},
                new SwingInvocationHandler(label)
        );
    }

    private static class SwingInvocationHandler implements InvocationHandler {
        private final JLabel target;

        public SwingInvocationHandler(JLabel target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (SwingUtilities.isEventDispatchThread()) {
                return method.invoke(target, args);
            } else {
                final Object[] result = new Object[1];
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        result[0] = method.invoke(target, args);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                return result[0];
            }
        }
    }

    public static void main(String[] args) {
        JLabel label = new JLabel();
        TextComponent proxyLabel = SwingProxy.createEDTProxy(label);

        // This call will run on the EDT if not already on it
        proxyLabel.setText("This will run on EDT automatically");
        System.out.println(proxyLabel.getText());
    }
}
