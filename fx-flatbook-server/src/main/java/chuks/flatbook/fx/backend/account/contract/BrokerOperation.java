/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.backend.account.contract;

import chuks.flatbook.fx.backend.account.Client;
import chuks.flatbook.fx.common.account.order.ManagedOrder;
import chuks.flatbook.fx.common.account.order.SymbolInfo;
import chuks.flatbook.fx.backend.listener.ConnectionListener;
import chuks.flatbook.fx.backend.listener.OrderActionListener;
import chuks.flatbook.fx.backend.listener.SymbolUpdateListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.Set;

/**
 *
 * @author user
 */
public interface BrokerOperation {

    
    public Set<String> getAllSymbols();
    
    public List<SymbolInfo> getSymbolInfoList(String[] symbols);
           
    public void sendMarketOrder(String req_identifier, ManagedOrder order);
    
    public void modifyOpenOrder(String req_identifier, String clOrdId, double target_price, double stoploss_price);

    public void sendClosePosition(String req_identifier, String clOrdId, double lot_size, double price, int slippage);
    
    public void placePendingOrder(String req_identifier, ManagedOrder order);

    public void modifyPendingOrder(String req_identifier, String clOrdId, double open_price, double target_price, double stoploss_price);
    
    public void deletePendingOrder(String req_identifier, String clOrdId);
    
    public void addListeners(Client client); 
    
    public void clearListeners(Client client);
    
    public void clearListeners(ChannelHandlerContext ctx);
    
    public void refreshContent(int account_number);            

    public void shutdown();
        
}
