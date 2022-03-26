package Player;

import MISC.DatabaseErrors;
import Menu.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public boolean checkUser(String username)
    {
        try {
            Statement stmt=MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT username FROM Accounts WHERE username='"+username+"'");
            if(rs.next())
                return true;
            else return false;
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            return false;
        }
    }

    public boolean getUser(String username, String password)
    {
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT username FROM Accounts WHERE username='"+username+"' AND password='"+password+"'");
            if(rs.next())
                return true;
            else return false;
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public int getUserID(String username)
    {
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT ID FROM Accounts WHERE username='"+username+"' LIMIT 1");
            if(rs.next())
                return rs.getInt(1);
            else return -1;
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean signUp(String username,String email, String password)
    {
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            stmt.executeUpdate(String.format("INSERT INTO Accounts (Username,Password,Email,RegisteredOn) VALUES ('%s','%s','%s',NOW())",username,password,email));
            stmt= MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT ID FROM Accounts WHERE username='"+username+"' LIMIT 1");
            rs.next();
            int ID= rs.getInt(1);
            stmt= MySQLConnection.getConnection().createStatement();
            int countUpdated=stmt.executeUpdate(String.format("INSERT INTO Stats (ID) VALUE ('%d')",ID));
            if(countUpdated > 0)
                return true;
            else return false;
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
            return false;
        }
    }
}
