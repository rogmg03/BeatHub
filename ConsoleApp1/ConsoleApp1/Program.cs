using System;
using System.Net.Sockets;
using System.Text;
using System.Xml;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {

            // connect to server
            TcpClient client = new TcpClient("localhost", 1133);

            Console.Write("Enter name : ");
            String name = Console.ReadLine();

            // send name to server
            byte[] buf;
            // append newline as server expects a line to be read
            buf = Encoding.UTF8.GetBytes(name + "\n");

            NetworkStream stream = client.GetStream();
            stream.Write(buf, 0, name.Length + 1);

            // read xml from server

            buf = new byte[100];
            stream.Read(buf, 0, 100);
            string xml = Encoding.UTF8.GetString(buf);
            // take only upto first null char
            xml = xml.Substring(0, xml.IndexOf(char.ConvertFromUtf32(0)));

            XmlDocument doc = new XmlDocument();
            doc.LoadXml(xml);
            Console.WriteLine(doc.DocumentElement.Name);
            if (doc.DocumentElement.Name == "error")
                Console.WriteLine("Name not found!");
            else
            {
                Console.WriteLine("Mobile : {0}", doc.SelectSingleNode("//mobile").InnerText);
                Console.WriteLine("Email  : {0}", doc.SelectSingleNode("//email").InnerText);
                Console.ReadLine();
            }
            Console.ReadLine();
        }
    }
}