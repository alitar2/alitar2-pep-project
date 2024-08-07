package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.sql.ResultSet;

public class AccountDAO {
    public Account insertAccount(Account a){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username,password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,a.getUsername());
            ps.setString(2,a.getPassword());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, a.getUsername(),a.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account a = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return a;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Account a = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return a;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
