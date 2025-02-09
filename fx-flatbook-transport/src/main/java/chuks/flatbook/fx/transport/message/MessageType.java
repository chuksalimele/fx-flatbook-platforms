/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package chuks.flatbook.fx.transport.message;

/**
 *
 * @author user
 */
public enum MessageType {

    //FROM CLIENT 
    SIGN_UP((byte) 0x02),
    LOGIN((byte) 0x03),
    LOGOUT((byte) 0x04),
    SUBCRIBE_MARKET_DATA((byte) 0x05),
    UNSUBCRIBE_MARKET_DATA((byte) 0x06),
    REQUEST_CURRENT_OPEN_POSITIONS((byte) 0x07),
    REQUEST_ACTIVE_POSITIONS((byte) 0x08),
    SEND_MARKET_ORDER((byte) 0x10),
    MODIFY_OPEN_ORDER((byte) 0x11),
    SEND_CLOSE_POSITION((byte) 0x12),
    PLACE_PENDING_ORDER((byte) 0x13),
    MODIFY_PENDING_ORDER((byte) 0x14),
    DELETE_PENDING_ORDER((byte) 0x15),
    REFRESH_CONTENT((byte) 0x16),//No need for the client to request it. the server sends it automatically after login
    GET_SUPPORTED_SYMBOLS((byte) 0x17),
    GET_SELECTED_SYMBOL_INFOF_LIST((byte) 0x18),
    SUBCRIBE_SYMBOLS((byte) 0x19),
    APPROVE_ACCOUNT((byte) 0x20),
    ENABLE_ACCOUNT((byte) 0x21),
    DISABLE_ACCOUNT((byte) 0x22),
    ACTIVATE_ACCOUNT((byte) 0x23),
    DEACTIVATE_ACCOUNT((byte) 0x24),
    CLOSE_ACCOUNT((byte) 0x25),
    GET_ACCOUNT_LIST((byte) 0x26),
    GET_UNAPPROVED_ACCOUNT_LIST((byte) 0x27),
    GET_DISABLED_ACCOUNT_LIST((byte) 0x28),
    GET_DEACTIVATED_ACCOUNT_LIST((byte) 0x29),
    GET_CLOSED_ACCOUNT_LIST((byte) 0x30),   
    WHITELIST_IPS((byte) 0x31),   
    BLACKLIST_IPS((byte) 0x32),   
    GET_WHITELISTED_IPS((byte) 0x33),   
    GET_BLACKLISTED_IPS((byte) 0x34),   
    GET_LOGS((byte) 0x35), 
    SET_MAX_CONNECTION_PER_IP((byte) 0x36), 
    SET_MAX_REQUEST_PER_SECOND_PER_IP((byte) 0x37), 
    GET_MAX_CONNECTION_PER_IP((byte) 0x38), 
    GET_MAX_REQUEST_PER_SECOND_PER_IP((byte) 0x39), 
    GET_ADMIN_LIST((byte) 0x40), 
    
    
    
    //FROM SERVER
    LOGGED_IN((byte) 0x70),
    LOGGED_OUT((byte) 0x71),
    NEW_MARKET_ORDER((byte) 0x72),
    CLOSED_MARKET_ORDER((byte) 0x73),
    MODIFIED_MARKET_ORDER((byte) 0x74),
    TRIGGERED_PENDING_ORDER((byte) 0x75),
    NEW_PENDING_ORDER((byte) 0x76),
    DELETED_PENDING_ORDER((byte) 0x77),
    MODIFIED_PENDING_ORDER((byte) 0x78),
    ORDER_REMOTE_ERROR((byte) 0x79),
    ADD_ALL_OPEN_ORDERS((byte) 0x80),
    ADD_ALL_PENDING_ORDERS((byte) 0x81),
    ADD_ALL_HISTORY_ORDERS((byte) 0x82),
    SWAP_CHANGE((byte) 0x83),
    PRICE_CHANGE((byte) 0x84),
    FULL_SYMBOL_LIST((byte) 0x85),
    SELECTED_SYMBOL_INFO_LIST((byte) 0x86),   
    LOGIN_FAILED((byte) 0x87),
    LOGOUT_FAILED((byte) 0x88),
    ACCOUNT_APPROVED((byte) 0x89),
    ACCOUNT_ACTIVATED((byte) 0x90),    
    ACCOUNT_DEACTIVATED((byte) 0x91),    
    ACCOUNT_ENABLED((byte) 0x92),    
    ACCOUNT_DISABLED((byte) 0x93),    
    ACCOUNT_CLOSED((byte) 0x94),  
    PAGINATED_ACCOUNT_LIST((byte) 0x95),  
    PAGINATED_UNAPPROVED_ACCOUNT_LIST((byte) 0x96),  
    PAGINATED_DEACTIVATED_ACCOUNT_LIST((byte) 0x97),  
    PAGINATED_DISABLED_ACCOUNT_LIST((byte) 0x98),  
    PAGINATED_CLOSED_ACCOUNT_LIST((byte) 0x99),   
    WHITELISTED_IPS((byte) 0x100),   
    BLACKLISTED_IPS((byte) 0x101), 
    SIGN_UP_FAILED((byte) 0x102), 
    INFO_LOGS((byte) 0x103), 
    WARN_LOGS((byte) 0x104), 
    DEBUG_LOGS((byte) 0x105),
    ERROR_LOGS((byte) 0x106), 
    TRACE_LOGS((byte) 0x107), 
    REJECTED_IPS_LOGS((byte) 0x108), 
    SUSPICIOUS_IPS_LOGS((byte) 0x109),     
    MAX_CONNECTION_PER_IP((byte) 0x110),     
    MAX_REQUEST_PER_SECOND_PER_IP((byte) 0x111), 
    REQUEST_FAILED((byte) 0x112), 
    PAGINATED_ADMIN_LIST((byte) 0x113),    
    ORDER_NOT_AVAILABLE((byte) 0x114),    
    SIGN_UP_INITIATED((byte) 0x115),
    ;

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MessageType fromValue(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
