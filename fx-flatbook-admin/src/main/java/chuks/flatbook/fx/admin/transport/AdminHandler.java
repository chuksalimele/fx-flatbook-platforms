/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.transport;

import chuks.flatbook.fx.transport.SharableTransportHandler;
import chuks.flatbook.fx.admin.account.contract.AccountContext;
import chuks.flatbook.fx.common.account.order.Order;
import chuks.flatbook.fx.common.account.order.SymbolInfo;
import chuks.flatbook.fx.common.account.profile.AdminInfo;
import chuks.flatbook.fx.common.account.profile.TraderInfo;
import chuks.flatbook.fx.transport.message.ChannelMessage;
import static chuks.flatbook.fx.transport.message.MessageType.ACCOUNT_APPROVED;
import io.netty.channel.ChannelHandlerContext;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author user
 */

class AdminHandler extends SharableTransportHandler {

    private final AccountContext accountCtx;

    public AdminHandler(AccountContext context) {
        this.accountCtx = context;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send login message
        accountCtx.setContext(ctx);
        //ctx.writeAndFlush(new ChannelMessage(MessageType.LOGIN, username));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChannelMessage msg) throws Exception {
        
        switch (msg.getType()) {
            case LOGGED_IN ->
                handleLoggedIn(msg);
            case NEW_MARKET_ORDER ->
                handleNewMarketOrder(msg);
            case CLOSED_MARKET_ORDER ->
                handleClosedMarketOrder(msg);
            case MODIFIED_MARKET_ORDER ->
                handleModifiedMarketOrder(msg);
            case TRIGGERED_PENDING_ORDER ->
                handleTriggerredPendingOrder(msg);
            case NEW_PENDING_ORDER ->
                handleNewPendingOrder(msg);
            case DELETED_PENDING_ORDER ->
                handleDeletedPendingOrder(msg);
            case MODIFIED_PENDING_ORDER ->
                handleModifiedPendingOrder(msg);
            case ORDER_REMOTE_ERROR ->
                handleOrderRemoteError(msg);
            case ADD_ALL_OPEN_ORDERS ->
                handleAddAllOpenOrders(msg);
            case ADD_ALL_PENDING_ORDERS ->
                handleAddAllPendingOrders(msg);        
            case ADD_ALL_HISTORY_ORDERS ->
                handleAddAllHistoryOrders(msg);
            case SWAP_CHANGE ->
                handleSwapChange(msg);
            case PRICE_CHANGE ->
                handlePriceChange(msg);        
            case FULL_SYMBOL_LIST ->
                handleFullSymbolList(msg);
            case SELECTED_SYMBOL_INFO_LIST ->
                handleSelectedSymbolInfoList(msg);                
            case LOGGED_OUT ->
                handleLogout(msg);
            case LOGIN_FAILED ->
                handleLoginFailed(msg);
            case LOGOUT_FAILED ->
                handleLogoutFaied(msg);   
            case ACCOUNT_APPROVED ->
                handleAccountApproved(msg);  
            case ACCOUNT_ACTIVATED ->
                handleAccountActivated(msg);
            case ACCOUNT_DEACTIVATED ->
                handleAccountDeactivated(msg);                
            case ACCOUNT_ENABLED ->
                handleAccountEnabled(msg);     
            case ACCOUNT_DISABLED ->
                handleAccountDisabled(msg);      
            case ACCOUNT_CLOSED ->
                handleAccountClosed(msg);     
            case PAGINATED_ADMIN_LIST ->
                handlePaginatedAdminList(msg);                   
            case PAGINATED_ACCOUNT_LIST ->
                handlePaginatedAccountList(msg);                                  
            case PAGINATED_UNAPPROVED_ACCOUNT_LIST ->
                handlePaginatedUnapprovedAccountList(msg);     
            case PAGINATED_DEACTIVATED_ACCOUNT_LIST ->
                handlePaginatedDeacitvatedAccountList(msg);      
            case PAGINATED_DISABLED_ACCOUNT_LIST ->
                handlePaginatedDisabledAccountList(msg);     
            case PAGINATED_CLOSED_ACCOUNT_LIST ->
                handlePaginatedClosedAccountList(msg);                                  
            case WHITELISTED_IPS ->
                handleWhitelistedIPs(msg);     
            case BLACKLISTED_IPS ->
                handleBlacklistedIPs(msg); 
            case MAX_CONNECTION_PER_IP ->
                handleMaxConnecionPerIP(msg);     
            case MAX_REQUEST_PER_SECOND_PER_IP ->
                handleMaxRequestPerSecondPerIP(msg);                      
            case SIGN_UP_FAILED ->
                handleSignUpFailed(msg);                                  
            case INFO_LOGS ->
                handleInfoLogs(msg);                                   
            case WARN_LOGS ->
                handleWarnLogs(msg);                                                   
            case DEBUG_LOGS ->
                handleDebugLogs(msg);   
            case ERROR_LOGS ->
                handleErrorLogs(msg); 
            case REQUEST_FAILED ->
                handleRequestFailed(msg);                 
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void handleLoggedIn(ChannelMessage msg) {
        this.accountCtx.onLoggedIn(msg.getInt(0));
    }

    private void handleNewMarketOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onNewMarketOrder(order);
    }

    private void handleClosedMarketOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onClosedMarketOrder(order);        
    }

    private void handleModifiedMarketOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onModifiedMarketOrder(order);        
    }

    private void handleTriggerredPendingOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onTriggeredPendingOrder(order);        
    }

    private void handleNewPendingOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onNewPendingOrder(order);        
    }

    private void handleDeletedPendingOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onDeletedPendingOrder(order);        
    }

    private void handleModifiedPendingOrder(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        this.accountCtx.onModifiedPendingOrder(order);        
    }

    private void handleOrderRemoteError(ChannelMessage msg) {
        Order order = new Order(msg.getString(0));
        String reason = msg.getString(1);
        this.accountCtx.onOrderRemoteError(order, reason);        
    }
    
    private void addAllOrders0(ChannelMessage msg){
        List<Order> orders = new LinkedList();
        String[] stringified_order_arr = msg.getStringArray(0);
        for (String order_str : stringified_order_arr) {
            orders.add(new Order(order_str));
        }
        this.accountCtx.onAddAllOpenOrders(orders);        
    }
    
    private void handleAddAllOpenOrders(ChannelMessage msg) {
        addAllOrders0(msg);
    }

    private void handleAddAllPendingOrders(ChannelMessage msg) {
        addAllOrders0(msg);
    }

    private void handleAddAllHistoryOrders(ChannelMessage msg) {
        addAllOrders0(msg);
    }

    private void handleSwapChange(ChannelMessage msg) {
        SymbolInfo symbolInfo = new SymbolInfo(msg.getString(0));
        this.accountCtx.onSwapChange(symbolInfo);        
    }

    private void handlePriceChange(ChannelMessage msg) {
        SymbolInfo symbolInfo = new SymbolInfo(msg.getString(0));
        this.accountCtx.onSwapChange(symbolInfo);        
    }

    private void handleFullSymbolList(ChannelMessage msg) {
        List<String> symbols = new LinkedList();
        String [] symb_arr = msg.getStringArray(0);
        for (String payload1 : symb_arr) {
            symbols.add((String)payload1);
        }
        this.accountCtx.onfullSymbolList(symbols);                
    }

    private void handleSelectedSymbolInfoList(ChannelMessage msg) {
        List<SymbolInfo> symbolInfoList = new LinkedList();
        String [] stringified_symbInfo_arr = msg.getStringArray(0);
        for (String str_info : stringified_symbInfo_arr) {
            symbolInfoList.add(new SymbolInfo(str_info));
        }
        this.accountCtx.onSeletedSymbolInfoList(symbolInfoList);
    }

    private void handleLogout(ChannelMessage msg) {
        this.accountCtx.onLoggedOut();
    }

    private void handleLoginFailed(ChannelMessage msg) {
        this.accountCtx.onLogInFailed(msg.getString(1));
    }

    private void handleLogoutFaied(ChannelMessage msg) {
        this.accountCtx.onLogOutFailed(msg.getString(0));
    }

    private void handleAccountApproved(ChannelMessage msg) {
        this.accountCtx.onAccountApproved(msg.getString(0), msg.getLong(1));
    }

    private void handleAccountActivated(ChannelMessage msg) {
        this.accountCtx.onAccountActivated(msg.getString(0));
    }

    private void handleAccountDeactivated(ChannelMessage msg) {
        this.accountCtx.onAccountDeactivated(msg.getString(0));
    }

    private void handleAccountEnabled(ChannelMessage msg) {
        this.accountCtx.onAccountEnabled(msg.getString(0));
    }

    private void handleAccountDisabled(ChannelMessage msg) {
        this.accountCtx.onAccountDisabled(msg.getString(0));
    }

    private void handleAccountClosed(ChannelMessage msg) {
        this.accountCtx.onAccountClosed(msg.getString(0));
    }
        
    private List toAdminProfileList(String[] strTrades) {        
        List list = new LinkedList();
        for (String strTrade : strTrades) {
            list.add(new AdminInfo(strTrade));
        }
        return list;
    }
    
    private List toTraderProfileList(String[] strTrades) {        
        List list = new LinkedList();
        for (String strTrade : strTrades) {
            list.add(new TraderInfo(strTrade));
        }
        return list;
    }
    
    private void handlePaginatedAdminList(ChannelMessage msg) {                
        this.accountCtx.onPaginatedAdmins(
                toAdminProfileList(msg.getStringArray(0)),
                msg.getInt(1));
    }
    
    private void handlePaginatedAccountList(ChannelMessage msg) {                
        this.accountCtx.onPaginatedTraders(
                toTraderProfileList(msg.getStringArray(0)),
                msg.getInt(1));
    }

    private void handlePaginatedUnapprovedAccountList(ChannelMessage msg) {
        this.accountCtx.onPaginatedTraders(
                toTraderProfileList(msg.getStringArray(0)),
                msg.getInt(1));
    }

    private void handlePaginatedDeacitvatedAccountList(ChannelMessage msg) {
        this.accountCtx.onPaginatedTraders(
                toTraderProfileList(msg.getStringArray(0)), 
                msg.getInt(1));
    }

    private void handlePaginatedDisabledAccountList(ChannelMessage msg) {
        this.accountCtx.onPaginatedTraders(
                toTraderProfileList(msg.getStringArray(0)),
                msg.getInt(1));
    }

    private void handlePaginatedClosedAccountList(ChannelMessage msg) {
        this.accountCtx.onPaginatedTraders(
                toTraderProfileList(msg.getStringArray(0)),
                msg.getInt(1));
    }

    private void handleWhitelistedIPs(ChannelMessage msg) {
        this.accountCtx.onWhitelistedIPs(msg.getStringArray(0));
    }

    private void handleBlacklistedIPs(ChannelMessage msg) {
        this.accountCtx.onBlacklistedIPs(msg.getStringArray(0));
    }

    private void handleMaxConnecionPerIP(ChannelMessage msg) {
        this.accountCtx.onMaxConnecionPerIP(msg.getInt(0));
    }

    private void handleMaxRequestPerSecondPerIP(ChannelMessage msg) {
        this.accountCtx.onMaxRequestPerSecondPerIP(msg.getInt(0));
    }

    private void handleSignUpFailed(ChannelMessage msg) {
        this.accountCtx.onSignUpFailed(msg.getString(0));
    }

    private void handleInfoLogs(ChannelMessage msg) {
        this.accountCtx.onInfoLogs(msg.getStringArray(0));
    }

    private void handleWarnLogs(ChannelMessage msg) {
        this.accountCtx.onWarnLogs(msg.getStringArray(0));
    }

    private void handleDebugLogs(ChannelMessage msg) {
        this.accountCtx.onDebugLogs(msg.getStringArray(0));
    }

    private void handleErrorLogs(ChannelMessage msg) {
        this.accountCtx.onErrorLogs(msg.getStringArray(0));
    }

    private void handleRequestFailed(ChannelMessage msg) {
        this.accountCtx.onAdminRequestFailed(msg.getString(0));
    }
}
