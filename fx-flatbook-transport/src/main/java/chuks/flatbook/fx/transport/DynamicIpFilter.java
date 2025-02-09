/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author user
 */
public class DynamicIpFilter extends RuleBasedIpFilter {

    String[] DEFAUT_WHITELIST_IPS = {"127.0.0.1"};
    private final ConcurrentHashMap<String, Integer> IPsReply;
    private DynamicIpFilter.IPHook ipHook = null;
    private final String DEFAULT_IP_REJECTION_REASON = "Not whitelisted";
    private final int IP_REPLY_WHITELISTED = 100;
    private final int IP_REPLY_NOT_WHITELISTED = 200;//ips not included in whitelist
    private final int IP_REPLY_BLACKLISTED_SINGLE = 300; //explicitly denied single ip usually due to suspicious reason  
    //Not yet implemented
    private final int IP_REPLY_BLACKLISTED_RANGE = 400; //explicitly denied ip within predefined range 
    
    public interface IPHook {
        void onAccepted(String ip);
        void onRejected(String ip, String reason);
    }
    

    public DynamicIpFilter() {
        // No rules initially, we will manage the rules dynamically
        super();
        this.IPsReply = new ConcurrentHashMap<>();
        this.replyIPs(DEFAUT_WHITELIST_IPS, IP_REPLY_WHITELISTED);
    }

    // Add an IP to the allowed list dynamically
    private void replyIPs(String[] ip_arr, int reason) {

        for (String ip : ip_arr) {
            IPsReply.put(ip, reason);
        }
    }

    // Add an IP to the whitelist list dynamically
    public void whitelistIPs(String[] ip_arr) {
        replyIPs(ip_arr, IP_REPLY_WHITELISTED);
    }

    // Remove or disallow an IP dynamically
    public void blacklistIPs(String[] ip_arr) {
        replyIPs(ip_arr, IP_REPLY_BLACKLISTED_SINGLE);
    }

    @Override
    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) {
        String clientIp = remoteAddress.getAddress().getHostAddress();
        // Check if the client IP is in the IPsReply list
        int ip_reply = IPsReply.getOrDefault(clientIp, IP_REPLY_NOT_WHITELISTED);
        
        boolean is_accepted = ip_reply == IP_REPLY_WHITELISTED;
        
        if (is_accepted) {
            if (ip_reply == IP_REPLY_WHITELISTED) {
                ipHook.onAccepted(clientIp);                
            } else {
                String reason =  DEFAULT_IP_REJECTION_REASON;
                switch(ip_reply){
                    case IP_REPLY_NOT_WHITELISTED -> reason = "Not whitelisted";
                    case IP_REPLY_BLACKLISTED_SINGLE -> reason = "Blacklisted single";
                    case IP_REPLY_BLACKLISTED_RANGE -> reason = "Blacklisted range";
                }
                
                ipHook.onRejected(clientIp, reason);
            }
        }

        return is_accepted;
    }

    public void setFilterHook(DynamicIpFilter.IPHook ipHook) {
        this.ipHook = ipHook;
    }
}
