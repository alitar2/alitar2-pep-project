package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MessageDAO {

    public Message insertMessage(Message m){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,m.getPosted_by());
            ps.setString(2,m.getMessage_text());
            ps.setLong(3,m.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id,m.getPosted_by(),m.getMessage_text(),m.getTime_posted_epoch());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Message m = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return m;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByUser(int postedBy){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,postedBy);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public void changeMessageById(int id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message_text);
            ps.setInt(2,id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
