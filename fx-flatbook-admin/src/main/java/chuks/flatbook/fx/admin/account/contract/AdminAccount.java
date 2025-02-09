/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.account.contract;

/**
 *
 * @author user
 */
public interface AdminAccount extends AdminOperation, AccountContext{

    public void signUp(String email, byte[] hash_password, String first_name, String last_name);

    public void login(int account_number, byte[] hash_password);
    
    public int getAccountNumber();
    
    public String getAccountName();    
    
    public char[] getPassword();   

    public boolean isLoggedIn();

}
