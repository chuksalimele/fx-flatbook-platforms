package chuks.flatbook.fx.admin.transport;

import chuks.flatbook.fx.transport.SharableTransportHandler;
import chuks.flatbook.fx.transport.TransportClient;
import chuks.flatbook.fx.admin.account.contract.AccountContext;
import chuks.flatbook.fx.admin.account.contract.AdminAccount;

public class Admin extends TransportClient {
    private final AdminAccount adminAccount;
    private final AdminHandler handler;
    
    public Admin(AdminAccount adminAccount, String host, int port) throws Exception {
        super(host, port);
        this.adminAccount = adminAccount;
        handler =  new AdminHandler((AccountContext) adminAccount);
        
    }

    @Override
    protected SharableTransportHandler getHandler() {
       return handler; 
    }

    @Override
    protected void onConnected() {
        adminAccount.onConnected();
    }

    @Override
    protected void onDisconnected(String msg) {
        adminAccount.onDisconnected(msg);
    }

}
