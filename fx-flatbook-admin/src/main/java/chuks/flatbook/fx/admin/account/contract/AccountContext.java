/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package chuks.flatbook.fx.admin.account.contract;

import chuks.flatbook.fx.common.account.order.Order;
import chuks.flatbook.fx.common.listener.ConnectionListener;
import chuks.flatbook.fx.common.listener.OrderActionListener;
import chuks.flatbook.fx.common.listener.SymbolUpdateListener;
import io.netty.channel.ChannelHandlerContext;
import chuks.flatbook.fx.admin.listener.AdminAccountListener;
import chuks.flatbook.fx.admin.listener.ClientIPListener;
import chuks.flatbook.fx.admin.listener.RemoteConfigListener;
import chuks.flatbook.fx.admin.listener.RemoteLogListener;
import chuks.flatbook.fx.admin.listener.TraderAccountListener;

/**
 *
 * @author user
 */
public interface AccountContext extends OrderActionListener,
        SymbolUpdateListener,
        ConnectionListener,
        AdminAccountListener,
        TraderAccountListener,
        RemoteConfigListener,
        RemoteLogListener,
        ClientIPListener{

    void setContext(ChannelHandlerContext ctx);

    public void onOrderRemoteError(Order order, String reason);
}
