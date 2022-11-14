import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SignUpModel {
    
    Connection conn = null;

    public SignUpModel(){
        this.conn = dbConnection.getConnection();
        if(this.conn == null){
            System.exit(1);
        }

    }

    public boolean isSignUp(String username, String password){

        if(isRegistered(username)){
            return false;
        }
        
        String query = "INSERT INTO login_tbl (username, password) VALUES (?,?)";
        PreparedStatement statement = null;

        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRegistered(String username){
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM login_tbl WHERE username = ?";

        try {
            statement = this.conn.prepareStatement(query);
            statement.setString(1, username);
            
            resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}