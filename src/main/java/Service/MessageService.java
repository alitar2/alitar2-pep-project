package Service;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyCollection;

import java.util.ArrayList;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message message){
        if (message.getMessage_text()!="" && message.getMessage_text().length()<255 && accountDAO.getAccountById(message.getPosted_by())!=null){
            return this.messageDAO.insertMessage(message);
        }
        else{
            return null;
        }
    }

    public List<Message> retreiveAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    public Message retreiveMessageById(int id){
        return this.messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        if (this.messageDAO.getMessageById(id)!=null){
            Message deleted = this.messageDAO.getMessageById(id);
            this.messageDAO.removeMessageById(id);
            return deleted;
        }
        else{
            return null;
        }
    }

    public Message updateMessageById(int id, String newMessage){
        if (this.messageDAO.getMessageById(id)!=null && newMessage.length()<255 && newMessage!=""){
            this.messageDAO.changeMessageById(id,newMessage);
            return this.messageDAO.getMessageById(id);
        }
        else{
            return null;
        }
    }

    public List<Message> retreiveMessagesByUser(int postedBy){
        return this.messageDAO.getMessagesByUser(postedBy);
    }
    
}
