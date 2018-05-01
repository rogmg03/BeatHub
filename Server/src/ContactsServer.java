import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsServer {
    private static ServerSocket serversocket;
	private static Scanner scanner;

	public static void main(String[] args) throws Exception {
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Steve", "9090909090", "steve@gmail.com"));
        contacts.add(new Contact("Bill", "9988776655", "bill@hotmail.com"));
        contacts.add(new Contact("Jack", "9876543210", "jack@yaho.com"));
        contacts.add(new Contact("Larry", "8877996655", "larry@gmail.com"));
        
        serversocket = new ServerSocket(1133,10);
        System.out.println("Contacts server is ready ....");

        while (true) {
            Socket client = serversocket.accept();
            scanner = new Scanner(client.getInputStream());
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            // find contact with the given name
            String name = scanner.nextLine();
            System.out.println(name);
            boolean found = false;
            for (Contact c : contacts) {
                if (c.getName().equals(name)) {
                    found = true;
                    // convert contact to XML
                    String xml = "<contact><mobile>" + c.getMobile() + "</mobile><email>" + c.getEmail() + "</email></contact>";
                    pw.println(xml);
                }
            }
            if (!found) {
                pw.println("<error>Name not found</error>");
            }
            client.close();
        }
    }
}