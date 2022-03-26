package Player;

import MISC.DatabaseErrors;
import Menu.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsSaveLoad {

    private Player player;

    public StatsSaveLoad(Player player) {
        this.player = player;
    }

    public void loadStats()
    {
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM Stats WHERE ID="+player.getID()+" LIMIT 1");
            while (rs.next())
            {
                int i=1;
                player.setPlayed(rs.getInt(++i));
                player.setWins(rs.getInt(++i));
                player.setLost(rs.getInt(++i));
                player.setDraws(rs.getInt(++i));

                player.setLowest(rs.getInt(++i));
                player.setHighest(rs.getInt(++i));
                player.setScore(rs.getInt(++i));
                player.setWickets(rs.getInt(++i));
                player.setSixes(rs.getInt(++i));
                player.setFours(rs.getInt(++i));
                player.setBalls(rs.getInt(++i));
            }
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
            return;
        }
    }

    public void saveStats()
    {
        if(player == null) return;
        try {
            Statement stmt= MySQLConnection.getConnection().createStatement();
            stmt.executeUpdate("UPDATE `Stats` SET " +
                    "`played`="+player.getPlayed() +
                    ",`wins`="+player.getWins() +
                    ",`lost`="+player.getLost() +
                    ",`draws`="+player.getDraws() +
                    " WHERE ID="+player.getID());
            stmt= MySQLConnection.getConnection().createStatement();
            stmt.executeUpdate("UPDATE `Stats` SET " +
                    "`lowest`="+player.getLowest() +
                    ",`highest`="+player.getHighest() +
                    ",`score`="+player.getScore() +
                    ",`wickets`="+player.getWickets() +
                    ",`sixes`="+player.getSixes() +
                    ",`fours`="+player.getFours() +
                    ",`balls`="+player.getBalls() +
                    " WHERE ID="+player.getID());
            stmt= MySQLConnection.getConnection().createStatement();
            stmt.executeUpdate("UPDATE `Accounts` SET " +
                    "`LastSeen`=NOW()"+
                    " WHERE ID="+player.getID());
        }
        catch(SQLException e)
        {
            new DatabaseErrors(null,e.toString());
            e.printStackTrace();
        }
    }
}
