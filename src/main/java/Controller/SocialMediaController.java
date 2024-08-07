package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login",this::postLoginHandler);
        app.post("/messages",this::postMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}",this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages",this::getMessagesByAccountHandler);
        return app;
    }

    /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account addedAccount = this.accountService.addAccount(account);
        if (addedAccount==null){
            context.status(400);
        }
        else{
            context.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account retreivedAccount = this.accountService.retreiveAccountByUsername(account.getUsername());
        if (retreivedAccount!=null && retreivedAccount.getPassword().equals(account.getPassword())){
            context.json(mapper.writeValueAsString(retreivedAccount));
        }
        else{
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);
        Message addedMessage = this.messageService.addMessage(message);
        if (addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context){
        context.json(this.messageService.retreiveAllMessages());
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message m = this.messageService.retreiveMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (m==null){
            context.status(200);
        }
        else{
            context.json(mapper.writeValueAsString(m));
        }
        
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message deletedMessage = this.messageService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (deletedMessage!=null){
            context.json(mapper.writeValueAsString(deletedMessage));
        }
        else{
            context.result("");
        }
    }

    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message m = mapper.readValue(context.body(),Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message changedMessage = this.messageService.updateMessageById(id, m.getMessage_text());
        if (changedMessage!=null){
            context.json(mapper.writeValueAsString(changedMessage));
        }
        else{
            context.status(400);
        }
    }

    private void getMessagesByAccountHandler(Context context){
        context.json(this.messageService.retreiveMessagesByUser(Integer.parseInt(context.pathParam("account_id"))));
    }









}