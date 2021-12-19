
import java.io.FileWriter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * 1.init context
 * 2.lookup registry for the service
 * 3.use the service
 * 4.end
 */
public class Client {
    public static void main(String[] args) throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "rmi://localhost:1234");

        InitialContext ctx = new InitialContext(jndiProperties);
        //NMessage msg = (NMessage)ctx.lookup("/neohope/jndi/test01");
        ResponseObject response = (ResponseObject) ctx.lookup("/neohope/jndi/test01");
        System.out.println(response.getFile().getAbsolutePath());
        System.out.println(response.getFile().length());
        ctx.close();
    }
}