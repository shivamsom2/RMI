import java.io.FileReader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Properties;

/**
 * JNDI Server
 * 1.create a registry on port 1234
 * 2.bind JNDI
 * 3.wait for connection
 * 4.clean up and end
 */
public class Server {
    private static Registry registry;
    private static InitialContext ctx;

    public static void main(String[] args) throws NamingException, IOException,Exception {
        String propPath = "C:\\Users\\DELL\\Documents\\RMI\\src\\application.properties";
        Properties prop = loadProperties(propPath);
        String ip=prop.getProperty("server.address");
        int port = Integer.parseInt(prop.getProperty("server.port"));
        String jndiObjectName = prop.getProperty("jndi.objectName");
        System.out.println("SERVER ADDRESS=>"+ip);
        System.out.println("SERVER PORT=>"+port);
        System.out.println("JNDI OBJECT NAME=>"+jndiObjectName);
        initJNDI(ip,port);
//        NMessage msg = new NMessage("Just A Message");
        ResponseObject response = new ResponseObject();
        bindJNDI(jndiObjectName, response);
        System.out.println("Started Succesfully");
        System.in.read();
        unbindJNDI(jndiObjectName);
        unInitJNDI();
    }
    
     public static void initJNDI(String ip,int port) {
        try {
            registry = LocateRegistry.createRegistry(port);
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "rmi://"+ip+":"+port);
            ctx = new InitialContext(jndiProperties);
            System.out.println("Starting the server...");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void bindJNDI(String name, Object obj) throws NamingException {
        System.out.println("Binding object...");
        ctx.bind(name, obj);
    }

    public static void unbindJNDI(String name) throws NamingException {
        ctx.unbind(name);
    }

    public static void unInitJNDI() throws NamingException {
        System.out.println("Shutting down the server...");
        ctx.close();
    }

    private static Properties loadProperties(String propertyFilePath)throws Exception {
        Properties properties = new Properties();
        FileReader reader = new FileReader(propertyFilePath);
        properties.load(reader);
        return properties;
    }
    
    
}