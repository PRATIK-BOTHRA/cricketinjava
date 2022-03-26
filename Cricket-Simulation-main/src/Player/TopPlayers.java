package Player;

import MISC.DatabaseErrors;
import Menu.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TopPlayers {
    public static String bestPlayer()
    {
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT ID FROM `Stats` WHERE 1 ORDER BY Stats.wins DESC LIMIT 1");
            if (rs.next())
            {
                int id= rs.getInt("ID");
                stmt= MySQLConnection.getConnection().createStatement();
                rs=stmt.executeQuery("SELECT Username FROM `Accounts` WHERE ID="+id);
                rs.next();
            }
            return rs.getString("Username");
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
            return "Error";
        }
    }
}
