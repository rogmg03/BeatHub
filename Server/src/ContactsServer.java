import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ContactsServer {
    private static ServerSocket serversocket;
	static ObjectMapper mapper = new ObjectMapper();
	static XmlMapper xmlma = new XmlMapper();
	
	public static void main(String[] args) throws Exception {
        serversocket = new ServerSocket(1133,10);
        System.out.println("Contacts server is ready ....");
        
        //XML desde el file 
        Message leido = xmlma.readValue(new File("c:\\Users\\danie\\a.xml"), Message.class);
       // System.out.println(leido.getSong());
      
        //Creacion del Json
        Song[] canciones = new Song[1]; 
        canciones[0] = new Song();
        canciones[0].setArtista(leido.getArtista());
        canciones[0].setNombre(leido.getOpcode());
        File file = new File("prueba.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, canciones);
        
        
        //Server
        while (true) {
        	Socket in = serversocket.accept();
        	InputStream entrada = in.getInputStream();
        	int a = entrada.read();
        	byte[] msjCliente = new byte[100000];
        	entrada.read(msjCliente,0,100000);
        	
        	//No se que hace la verdad 
        	System.out.println(msjCliente);
        	String recieved = new String(msjCliente);
        	String lol = recieved.trim();
        	String lel = lol.substring(3);
        	System.out.println(lel+"lol");
        	System.out.println(lel.length());
        	
        	//agarra el XML del cliente y lo descomprime (no funka)
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder builder;
        	builder = factory.newDocumentBuilder();
        	System.out.println("AK7");
        	Document doc = builder.parse(new InputSource(new StringReader(lel)));
        	System.out.println(doc.getNodeName());
        	//BufferedReader reader = new BufferedReader(new InputStreamReader(in.getInputStream()));
        	
        	//System.out.println(reader.readLine());
        	
           
        
		
            
         
            PrintWriter pw = new PrintWriter(in.getOutputStream(), true);

            
//            // find contact with the given name

          //  pw.println(leido);
            
//            boolean found = false;
//            for (Contact c : contacts) {
//            	 
//                if (c.getName().equals(name)) {
//                    found = true;
//                    // convert contact to XML
//                    String xml = "<contact><mobile>" + c.getMobile() + "</mobile><email>" + c.getEmail() + "</email></contact>";
//                 
//                    pw.println(xml);
//                }
//               
//            }
//            if(name.equals("Nombre")) {
//             	pw.print(p.getTextContent());
//             }
//            
//            if (!found) {
//                pw.println("<error>Name not found</error>");
//            }
            in.close();
        }
    }
}