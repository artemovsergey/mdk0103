using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;

namespace TestPorject.Console;

public class User(int id, string name)
{
    public int Id {get; set;} = id;
    public string Name {get; set;} = name;

    // public User() : this(1,"bob"){
    //     Id = id;
    // }
};

public record UserRecord(int Id, string Name);





