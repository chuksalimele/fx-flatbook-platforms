/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.admin.transport;

import chuks.flatbook.fx.common.account.order.Order;
import chuks.flatbook.fx.common.account.order.SymbolInfo;
import chuks.flatbook.fx.common.listener.ConnectionListener;
import chuks.flatbook.fx.common.listener.OrderActionListener;
import chuks.flatbook.fx.common.listener.SymbolUpdateListener;
import chuks.flatbook.fx.transport.message.MessageType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import chuks.flatbook.fx.admin.account.contract.AdminAccount;
import chuks.flatbook.fx.common.util.log.LogLevel;
import chuks.flatbook.fx.common.account.profile.TraderInfo;
import chuks.flatbook.fx.admin.listener.AdminAccountListener;
import chuks.flatbook.fx.admin.listener.ClientIPListener;
import chuks.flatbook.fx.admin.listener.RemoteConfigListener;
import chuks.flatbook.fx.admin.listener.RemoteLogListener;
import chuks.flatbook.fx.admin.listener.TraderAccountListener;
import chuks.flatbook.fx.common.account.profile.AdminInfo;
import chuks.flatbook.fx.common.account.profile.ClientIPInfo;
import chuks.flatbook.fx.common.util.OnceAccessStore;
import chuks.flatbook.fx.transport.message.MessageFactory;

/**
 *
 * @author user
 */
public class AdminManager implements AdminAccount {

    ChannelHandlerContext ctx;
    private boolean isLoggedIn;
    private int accountNumber;
    private String accountName;
    OnceAccessStore onceAccesStore = new OnceAccessStore();

    List<OrderActionListener> orderActionListenerList = new LinkedList();
    List<SymbolUpdateListener> symbolUpdateListenerList = new LinkedList();
    List<ConnectionListener> connectionListenerList = new LinkedList();
    List<AdminAccountListener> adminAccountListenerList = new LinkedList();
    List<TraderAccountListener> traderAccountListenerList = new LinkedList();
    List<RemoteConfigListener> remoteConfigListenerList = new LinkedList();
    List<RemoteLogListener> remoteLogListenerList = new LinkedList();
    List<ClientIPListener> clientIPListenerList = new LinkedList();
    
    

    final static private String ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR = "Could not send request. Please check your connection";

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public void setContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public char[] getPassword() {
        return new char[0];
    }

    @Override
    public SymbolInfo getSymbolInfo(String symbol) {
        return null;
    }

    void handleWriteCompletion(ChannelFuture future, Consumer success, Consumer error) {
        // Add a listener to the future to check if the write was successful
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    System.out.println("Message sent successfully");
                    success.accept("");
                } else {
                    System.err.println("Failed to send message");
                    error.accept(future.cause().getMessage());
                    future.cause().printStackTrace();
                }
                // Remove this listener since it won't be needed again
                future.removeListener(this);
            }
        });

    }

    @Override
    public void signUp(String email, byte[] hash_password, String first_name, String last_name) {

        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.SIGN_UP)
                        .assign(email, hash_password, first_name, last_name)
        );

        handleWriteCompletion(future, success -> {

        }, error -> {

        });

    }

    @Override
    public void login(int account_number, byte[] hash_password) {

        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.LOGIN)
                        .assign(account_number, hash_password
                ));

        handleWriteCompletion(future, success -> {

        }, error -> {

        });
    }

    @Override
    public void sendMarketOrder(Order order) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.SEND_MARKET_ORDER)
                .assign( order.stringify()));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(order, "Could not send market order. Check connection.");
        });
    }

    @Override
    public void modifyOpenOrder(String clOrdId, double target_price, double stoploss_price) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.MODIFY_OPEN_ORDER)
                .assign(clOrdId, target_price, stoploss_price));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(null, "Could not modify order. Check connection.");
        });
    }

    @Override
    public void sendClosePosition(String clOrdId, double lot_size) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.SEND_CLOSE_POSITION)
                .assign(clOrdId, lot_size));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(null, "Could not close position. Check connection.");
        });
    }

    @Override
    public void placePendingOrder(Order order) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.PLACE_PENDING_ORDER)
                .assign(order.stringify()));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(null, "Could not place pending order. Check connection.");
        });
    }

    @Override
    public void modifyPendingOrder(String clOrdId, double target_price, double stoploss_price) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.MODIFY_PENDING_ORDER)
                .assign(clOrdId, target_price, stoploss_price));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(null, "Could not modify pending order. Check connection.");
        });
    }

    @Override
    public void deletePendingOrder(String clOrdId) {
        ChannelFuture future = ctx.writeAndFlush(MessageFactory
                .create(MessageType.DELETE_PENDING_ORDER)
                .assign(clOrdId));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onOrderSendFailed(null, "Could not delete pending order. Check connection.");
        });
    }

    @Override
    public void addOrderActionListener(OrderActionListener listener) {
        orderActionListenerList.add(listener);
    }

    @Override
    public void addSymbolUpdateListener(SymbolUpdateListener listener) {
        symbolUpdateListenerList.add(listener);
    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {
        connectionListenerList.add(listener);
    }

    @Override
    public void addAdminAccountListener(AdminAccountListener listener) {
        adminAccountListenerList.add(listener);
    }

    @Override
    public void addTraderAccountListener(TraderAccountListener listener) {
        traderAccountListenerList.add(listener);
    }

    @Override
    public void addRemoteConfigListener(RemoteConfigListener listener) {
        remoteConfigListenerList.add(listener);
    }

    @Override
    public void addRemoteLogListener(RemoteLogListener listener) {
        remoteLogListenerList.add(listener);
    }

    @Override
    public void addClientIPListener(ClientIPListener listener) {
        clientIPListenerList.add(listener);
    }    

    @Override
    public List<String> getSelectedSymbols() {
        return null;
    }

    @Override
    public void setSelectedSymbols(List<String> list) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void refreshContent() {
        ctx.writeAndFlush(MessageFactory
                .create(MessageType.REFRESH_CONTENT)
                .assign( ""));
    }

    @Override
    public void onAdminRequestFailed(String errMsg) {
        adminAccountListenerList.forEach(listener -> {
            listener.onAdminRequestFailed(errMsg);
        });
    }

    @Override
    public void onNewMarketOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onNewMarketOrder(order);
        });
    }

    @Override
    public void onClosedMarketOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onClosedMarketOrder(order);
        });
    }

    @Override
    public void onModifiedMarketOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onModifiedMarketOrder(order);
        });
    }

    @Override
    public void onTriggeredPendingOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onTriggeredPendingOrder(order);
        });
    }

    @Override
    public void onNewPendingOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onNewPendingOrder(order);
        });
    }

    @Override
    public void onDeletedPendingOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onDeletedPendingOrder(order);
        });
    }

    @Override
    public void onModifiedPendingOrder(Order order) {
        orderActionListenerList.forEach(listener -> {
            listener.onModifiedPendingOrder(order);
        });
    }

    @Override
    public void onOrderSendFailed(Order order, String errMsg) {
        orderActionListenerList.forEach(listener -> {
            listener.onOrderSendFailed(order, errMsg);
        });
    }

    @Override
    public void onOrderRemoteError(Order order, String errMsg) {
        orderActionListenerList.forEach(listener -> {
            listener.onOrderSendFailed(order, errMsg);
        });
    }

    @Override
    public void onOrderNotAvailable(String req_identifier, String errMsg) {
        orderActionListenerList.forEach(listener -> {
            listener.onOrderSendFailed(null, errMsg);
        });
    }

    @Override
    public void onAddAllOpenOrders(List<Order> orders) {
        orderActionListenerList.forEach(listener -> {
            listener.onAddAllOpenOrders(orders);
        });
    }

    @Override
    public void onAddAllPendingOrders(List<Order> orders) {
        orderActionListenerList.forEach(listener -> {
            listener.onAddAllOpenOrders(orders);
        });
    }

    @Override
    public void onAddAllHistoryOrders(List<Order> orders) {
        orderActionListenerList.forEach(listener -> {
            listener.onAddAllOpenOrders(orders);
        });
    }

    @Override
    public void onSwapChange(SymbolInfo symbolInfo) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onSwapChange(symbolInfo);
        });
    }

    @Override
    public void onPriceChange(SymbolInfo symbolInfo) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onPriceChange(symbolInfo);
        });
    }

    @Override
    public void onSymbolInfoAdded(SymbolInfo symbolInfo) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onSymbolInfoAdded(symbolInfo);
        });
    }

    @Override
    public void onSymbolInfoRemoved(SymbolInfo symbolInfo) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onSymbolInfoRemoved(symbolInfo);
        });
    }

    @Override
    public void onGetFullRefereshSymbol(String symbolName) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onGetFullRefereshSymbol(symbolName);
        });
    }

    @Override
    public void onfullSymbolList(List<String> symbol_list) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onfullSymbolList(symbol_list);
        });
    }

    @Override
    public void onSeletedSymbolList(List<String> symbol_list) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onSeletedSymbolList(symbol_list);
        });
    }

    @Override
    public void onSeletedSymbolInfoList(List<SymbolInfo> symbol_info_list) {
        symbolUpdateListenerList.forEach(listener -> {
            listener.onSeletedSymbolInfoList(symbol_info_list);
        });
    }

    @Override
    public void onConnectionProgress(String status) {
        connectionListenerList.forEach(listener -> {
            listener.onConnectionProgress(status);
        });
    }

    @Override
    public void onLoggedIn(int admin_id) {
        isLoggedIn = true;
        adminAccountListenerList.forEach(listener -> {
            listener.onLoggedIn(admin_id);
        });
    }

    @Override
    public void onLoggedOut() {
        isLoggedIn = false;
        adminAccountListenerList.forEach(listener -> {
            listener.onLoggedOut();
        });
    }

    @Override
    public void onLogInFailed(String reason) {
        adminAccountListenerList.forEach(listener -> {
            listener.onLogInFailed(reason);
        });
    }

    @Override
    public void onLogOutFailed(String reason) {
        adminAccountListenerList.forEach(listener -> {
            listener.onLogOutFailed(reason);
        });
    }

    @Override
    public void onConnected() {
        connectionListenerList.forEach(listener -> {
            listener.onConnected();
        });
    }

    @Override
    public void onDisconnected(String errMsg) {
        connectionListenerList.forEach(listener -> {
            listener.onDisconnected(errMsg);
        });
    }

    @Override
    public void onAccountOpen(int admin_id) {
        adminAccountListenerList.forEach(listener -> {
            listener.onAccountOpen(admin_id);
        });
    }

    @Override
    public void onSignUpFailed(String reason) {
        adminAccountListenerList.forEach(listener -> {
            listener.onSignUpFailed(reason);
        });
    }

    @Override
    public void onAdminDisabled() {
        adminAccountListenerList.forEach(listener -> {
            listener.onAdminDisabled();
        });
    }

    @Override
    public void onAdminEnabled() {
        adminAccountListenerList.forEach(listener -> {
            listener.onAdminEnabled();
        });
    }

    @Override
    public void onAdminApproved() {
        adminAccountListenerList.forEach(listener -> {
            listener.onAdminApproved();
        });
    }

    @Override
    public void onAdminClosed() {
        adminAccountListenerList.forEach(listener -> {
            listener.onAdminClosed();
        });
    }

    @Override
    public void onRegister(TraderInfo trader) {
        traderAccountListenerList.forEach(listener -> {
            listener.onRegister(trader);
        });
    }

    @Override
    public void onEmailVerified(String trader_email, long verified_time) {
        traderAccountListenerList.forEach(listener -> {
            listener.onEmailVerified(trader_email, verified_time);
        });
    }

    @Override
    public void onAccountOpened(String trader_email) {
        traderAccountListenerList.forEach(listener -> {
            listener.onAccountOpened(trader_email);
        });
    }

    @Override
    public void onAccountClosed(String trader_email) {
        traderAccountListenerList.forEach(listener -> {
            listener.onAccountClosed(trader_email);
        });
    }

    @Override
    public void onAccountDeactivated(String trader_email) {
        traderAccountListenerList.forEach(listener -> {
            listener.onAccountDeactivated(trader_email);
        });
    }

    @Override
    public void onAccountActivated(String trader_email) {
        traderAccountListenerList.forEach(listener -> {
            listener.onAccountActivated(trader_email);
        });
    }

    @Override
    public void onAccountEnabled(String trader_email) {
        traderAccountListenerList.forEach(listener -> {
            listener.onAccountEnabled(trader_email);
        });
    }

    @Override
    public void onAccountDisabled(String trader_email) {
        this.traderAccountListenerList.forEach(listener -> {
            listener.onAccountDisabled(trader_email);
        });
    }

    @Override
    public void onAccountApproved(String trader_email, long approval_time) {
        this.traderAccountListenerList.forEach(listener -> {
            listener.onAccountApproved(trader_email, approval_time);
        });
    }

    @Override
    public void onAccountApproveFail(String trader_email) {
        this.traderAccountListenerList.forEach(listener -> {
            listener.onAccountApproveFail(trader_email);
        });
    }

    @Override
    public void onPaginatedTraders(List<TraderInfo> traders, int overall_total) {
        this.traderAccountListenerList.forEach(listener -> {
            listener.onPaginatedTraders(traders, overall_total);
        });
    }

    @Override
    public void onAdminPasswordChanged(char[] new_password) {
        this.adminAccountListenerList.forEach(listener -> {
            listener.onAdminPasswordChanged(new_password);
        });
    }
       
    @Override
    public void onPaginatedAdmins(List<AdminInfo> admins, int overall_total) {              
        this.adminAccountListenerList.forEach(listener -> {
            listener.onPaginatedAdmins(admins, overall_total);
        });
    }
    
    @Override
    public void onWhitelistedIPs(String[] IPs) {
        this.remoteConfigListenerList.forEach(listener -> {
            listener.onWhitelistedIPs(IPs);
        });
    }

    @Override
    public void onBlacklistedIPs(String[] IPs) {
        this.remoteConfigListenerList.forEach(listener -> {
            listener.onBlacklistedIPs(IPs);
        });
    }

    @Override
    public void onMaxConnecionPerIP(int max) {
        this.remoteConfigListenerList.forEach(listener -> {
            listener.onMaxConnecionPerIP(max);
        });    }

    @Override
    public void onMaxRequestPerSecondPerIP(int max) {
        this.remoteConfigListenerList.forEach(listener -> {
            listener.onMaxRequestPerSecondPerIP(max);
        });
    }
    @Override
    public void onDebugLogs(String[] records) {
        this.remoteLogListenerList.forEach(listener -> {
            listener.onDebugLogs(records);
        });
    }

    @Override
    public void onInfoLogs(String[] records) {
        this.remoteLogListenerList.forEach(listener -> {
            listener.onInfoLogs(records);
        });
    }

    @Override
    public void onWarnLogs(String[] records) {
        this.remoteLogListenerList.forEach(listener -> {
            listener.onWarnLogs(records);
        });
    }

    @Override
    public void onErrorLogs(String[] records) {
        this.remoteLogListenerList.forEach(listener -> {
            listener.onErrorLogs(records);
        });
    }

    @Override
    public void onConnectedWhitelistedIP(ClientIPInfo ipInfo) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onConnectedWhitelistedIP(ipInfo);
        });
    }

    @Override
    public void onConnectedBlacklistedIP(ClientIPInfo ipInfo) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onConnectedBlacklistedIP(ipInfo);
        });
    }

    @Override
    public void onDiconnectedWhitelistedIP(ClientIPInfo ipInfo) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onDiconnectedWhitelistedIP(ipInfo);
        });
    }

    @Override
    public void onDiconnectedBlacklistedIP(ClientIPInfo ipInfo) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onDiconnectedBlacklistedIP(ipInfo);
        });
    }

    @Override
    public void onWhitelistedIPList(List<ClientIPInfo> ipInfoList) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onWhitelistedIPList(ipInfoList);
        });
    }

    @Override
    public void onBlacklistedIPList(List<ClientIPInfo> ipInfoList) {
        this.clientIPListenerList.forEach(listener -> {
            listener.onBlacklistedIPList(ipInfoList);
        });
    }

    @Override
    public void getAccountList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_ACCOUNT_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getAdminList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_ADMIN_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getDeactivatedAccountList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_DEACTIVATED_ACCOUNT_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getClosedAccountList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_CLOSED_ACCOUNT_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getUnapprovedAccountList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_UNAPPROVED_ACCOUNT_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getDisabledAccountList(int page_index, int page_size, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_DISABLED_ACCOUNT_LIST)
                        .assign( page_index, page_size, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void approveAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.APPROVE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void enableAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.ENABLE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void disableAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.DISABLE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void activateAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.ACTIVATE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void deactivateAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.DEACTIVATE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void closeAccount(String email, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.CLOSE_ACCOUNT)
                        .assign( email, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void whitelistIPs(String[] IPs, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.WHITELIST_IPS)
                        .assign( IPs, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void blacklistIPs(String[] IPs, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.BLACKLIST_IPS)
                        .assign( IPs, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getWhitelistIPs(int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_WHITELISTED_IPS)
                        .assign( admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getBlacklistIPs(int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_BLACKLISTED_IPS)
                        .assign( admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getLogs(LogLevel log_level, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_LOGS)
                        .assign(log_level.getValue(), admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getLogs(LogLevel log_level, long start_time, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_LOGS)
                        .assign(log_level.getValue(), start_time, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getLogs(LogLevel log_level, long start_time, long end_time, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_LOGS)
                        .assign(log_level.getValue(),start_time, end_time, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }
    

    @Override
    public void setMaxConnectionPerIP(int max, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.SET_MAX_CONNECTION_PER_IP)
                        .assign(max, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void setMaxRequestPerSecondPerIP(int max, int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.SET_MAX_REQUEST_PER_SECOND_PER_IP)
                        .assign(max, admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getMaxConnectionPerIP(int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_MAX_CONNECTION_PER_IP)
                        .assign(admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }

    @Override
    public void getMaxRequestPerSecondPerIP(int admin_id) {
        ChannelFuture future = ctx.writeAndFlush(
                MessageFactory
                        .create(MessageType.GET_MAX_REQUEST_PER_SECOND_PER_IP)
                        .assign(admin_id));
        handleWriteCompletion(future, success -> {

        }, error -> {
            onAdminRequestFailed(ERR_REQUEST_SEND_FAIL_BY_CONNECTION_ERROR);
        });
    }


}
