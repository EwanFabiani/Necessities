package bot.necessities.sql;

import bot.necessities.command.CommandManager;
import bot.necessities.util.RANK;
import net.dv8tion.jda.api.entities.User;

import java.sql.*;

public class JDBC {

    public static String name = "";
    public static String password = "";
    public static String url = "jdbc:mysql://localhost:3306/necessities";

    public static void startUp(String namemethod, String passwordmethod) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        name = namemethod;
        password = passwordmethod;

        checkForEntry(12389721L);

        try {
            Connection con = DriverManager.getConnection(url, name, password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public static RANK getRank(Long ID) {

        try {
            String query = "select `rank` FROM users WHERE id=" + ID.toString() + ";";
            Connection con = DriverManager.getConnection(url, name, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                return RANK.valueOf(result.getString(1));
            }else {
                return null;
            }
        }catch (Exception e) {
            return null;
        }

    }

    public static Boolean checkForEntry(Long ID) {

        try {
            String query = "select * FROM users WHERE id=" + ID + ";";
            Connection con = DriverManager.getConnection(url, name, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e) {
            return null;
        }
    }

    public static Boolean checkForEntryAndCreate(Long ID) {

        try {
            String query = "select * FROM users WHERE id=" + ID + ";";
            Connection con = DriverManager.getConnection(url, name, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            if (result.next()) {
                return true;
            }else {
                if (addToDatabase(ID)) {
                    return false;
                }else {
                    return null;
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean addToDatabase(Long ID) {
        try {
            Connection con = DriverManager.getConnection(url, name, password);
            String createquery = "INSERT INTO users VALUES('" + ID + "', 'USER');";

            Statement createstatement = con.createStatement();
            createstatement.execute(createquery);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public static Boolean changeRank(Long id, RANK r) {
        try {

            //No checks for correct rank/user!
            String query = "UPDATE users SET `rank`='" + r.toString() + "' WHERE id=" + id + ";";
            Connection con = DriverManager.getConnection(url, name, password);
            Statement createstatement = con.createStatement();
            createstatement.execute(query);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void deleteUser(Long id) {

        if (checkForEntry(id)) {
            try {
                String query = "DELETE FROM users WHERE id=" + id + ";";
                Connection con = DriverManager.getConnection(url, name, password);
                Statement createstatement = con.createStatement();
                createstatement.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {

        }

    }

}
