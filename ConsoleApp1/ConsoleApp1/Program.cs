using System;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace ConsoleApp1
{
    class Program
    {

        static void Main(string[] args)
        {
            //Creacion del XML
            String file = "c:\\Users\\danie\\a.xml";
            byte[] audiobyte = File.ReadAllBytes("C:\\Users\\danie\\Downloads\\Gorillaz - Clint Eastwood.mp3");
            String str = Encoding.UTF8.GetString(audiobyte);
            Class1 data = new Class1();
            data.opcode = 00;
            data.artista = "Tolebrio";
            data.album = "El sarpe";
            data.song = audiobyte;
            data.Save(file);
            Class1 class1 = Class1.LoadFile(file);

            XmlDocument doc = new XmlDocument();
            doc.Load(file);

            //Para mandar el XML al server
            MemoryStream xmlStream = new MemoryStream();
            //  doc.Save(xmlStream);
            xmlStream.ToArray();

           // connect to server
            TcpClient client = new TcpClient("localhost", 1133);

            // send name to server
           // byte[] buf;

            // append newline as server expects a line to be read
            byte[] buf = Convert.FromBase64String(doc.ToString());
            
            Stream stream = client.GetStream(); 
            stream.Write(buf, 0, 10);

            // read xml from server
            XmlDocument docServer = new XmlDocument();
            buf = new byte[100];
            stream.Read(buf, 0, 100);
            string xml = Encoding.UTF8.GetString(buf);

            // take only upto first null char
            xml = xml.Substring(0, xml.IndexOf(char.ConvertFromUtf32(0)));

           //Leer XML del server

            /*
            Console.WriteLine(doc.DocumentElement.Name);
            if (doc.DocumentElement.Name == "error")
                Console.WriteLine("Name not found!");
            else
            {
                Console.WriteLine("Mobile : {0}", doc.SelectSingleNode("//mobile").InnerText);
                Console.WriteLine("Email  : {0}", doc.SelectSingleNode("//email").InnerText);
                Console.ReadLine();
            }*/

            Console.ReadLine();
        }
    }
}