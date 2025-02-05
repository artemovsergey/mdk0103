using TestPorject.Console;
using System;

public static class Program{
    public static void Main(){

        var user = new User(1, "bob");
        var userRecord = new UserRecord(1, "bob");
        Console.WriteLine(userRecord.Name);
        System.Console.WriteLine(user.Name);
    }
}