using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace ConsoleApp1
{
    public class Class1
    {
        public Class1()
        {

        }

        public int opcode { get; set; }
        public string artista { get; set; }
        public string album { get; set; }
        public byte[] song { get; set; }

        public void Save(string fileName)
        {
            using (var stream = new FileStream(fileName, FileMode.Create))
            {
                var xml = new XmlSerializer(typeof(Class1));
                xml.Serialize(stream, this);
            }
        }

      
        public static Class1 LoadFile(String fileName)
        {
            using (var stream = new FileStream(fileName, FileMode.Open))
            {
                var xml = new XmlSerializer(typeof(Class1));
               return (Class1) xml.Deserialize(stream);
            }
        }
    }
}
