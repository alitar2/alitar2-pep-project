package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if (this.accountDAO.getAccountByUsername(account.getUsername())==null && account.getUsername()!="" && account.getPassword().length()>=4){
            return this.accountDAO.insertAccount(account);
        }
        else{
            return null;
        }
    }

    public Account retreiveAccountByUsername(String username){
        return this.accountDAO.getAccountByUsername(username);
    }
    
}
