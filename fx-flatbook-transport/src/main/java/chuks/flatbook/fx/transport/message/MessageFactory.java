/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport.message;

/**
 *
 * @author user
 */
public class MessageFactory {

    public static ChannelMessage create(MessageType msgType) {
        return new ChannelMessage(msgType);
    }

    public static ChannelMessage create(MessageType msgType, String requestResponseIdentifier) {
        return new ChannelMessage(msgType, requestResponseIdentifier);
    }
}
