/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chuks.flatbook.fx.transport.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class TestMsg {

    protected void encode(ByteBuf buffer, ChannelMessage msg, List<Object> out) throws Exception {


        //write one byte which represent message type
        buffer.writeByte(msg.getType().getValue());
        
        //write length of request-response-identifier
        String identifier = msg.getIdentifier();
        
        if(identifier != null && !identifier.isEmpty()){
            buffer.writeInt(identifier.length());
            buffer.writeBytes(identifier.getBytes());
        }else{
            buffer.writeInt(0);
        }
        
        
        buffer = encode0(buffer, msg.getArguments());
        out.add(buffer);
    }

    void writeArguPrefix(ByteBuf buffer, int variable_type, int byte_length, int array_length) {
        buffer.writeInt(variable_type);//write the vaiable type - whether int, float e.t.c
        buffer.writeInt(byte_length);//write number of bytes used to store the variable type
        buffer.writeInt(array_length); //write whether it is array or not. -1 means is not an array otherwise is an array
    }

    void writeByte(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.BYTE, Byte.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeByte((byte) argument);
        } else {//is an array
            byte[] arr = (byte[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeByte(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeChar(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.CHAR, Character.BYTES, array_length);

        if (array_length == -1) {//not an array           
            buffer.writeChar((char) argument);
        } else {//is an array
            char[] arr = (char[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeChar(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeBoolean(ByteBuf buffer, Object argument, int argument_index, int array_length) {
        //in the case of Boolean we will short as integer but mark it as Boolean
        writeArguPrefix(buffer, VarType.BOOLEAN, Integer.BYTES, array_length);

        if (array_length == -1) {//not an array
            int bool = ((boolean) argument)? 1 : 0; //Convert to interger or Zero or One
            //NOTE: for boolean we will store as integer because 
            //theER is no writeBoolean method on the buffer object
            buffer.writeInt(bool);//Yes in the case of boolean
        } else {//is an array
            boolean[] arr = (boolean[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeBoolean(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeShort(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.SHORT, Short.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeShort((short) argument);
        } else {//is an array
            short[] arr = (short[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeShort(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeInt(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.INT, Integer.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeInt((int) argument);
        } else {//is an array
            int[] arr = (int[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeInt(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeLong(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.LONG, Long.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeLong((long) argument);
        } else {//is an array
            long[] arr = (long[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeLong(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeFloat(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.FLOAT, Float.BYTES, array_length);

        if (array_length == -1) {//not an array                    
            buffer.writeFloat((float) argument);
        } else {//is an array
            float[] arr = (float[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeFloat(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }
    }

    void writeDouble(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        writeArguPrefix(buffer, VarType.DOUBLE, Double.BYTES, array_length);

        if (array_length == -1) {//not an array
            buffer.writeDouble((double) argument);
        } else {//is an array
            double[] arr = (double[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeDouble(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }

    }

    void writeString(ByteBuf buffer, Object argument, int argument_index, int array_length) {

        if (array_length == -1) {//not an array
            if (argument == null) {
                argument = "";
            }
            int str_byte_len = ((String) argument).getBytes(StandardCharsets.UTF_8).length;
            writeArguPrefix(buffer, VarType.STRING, str_byte_len, array_length);
            buffer.writeBytes(((String) argument).getBytes());
        } else {//is an array
            writeArguPrefix(buffer, VarType.STRING, 0, array_length);
            String[] arr = (String[]) argument;
            for (int i = 0; i < arr.length; i++) {
                writeString(buffer, arr[i], i, -1); //-1 since it is just an element of the array and not an array in itself 
            }
        }

    }

    private ByteBuf encode0(ByteBuf buffer, Object... varargs) {

        //first write the number of arguments
        buffer.writeInt(varargs.length);

        for (int i = 0; i < varargs.length; i++) {

            if (varargs[i] instanceof Byte) {
                writeByte(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Character) {
                writeChar(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Boolean) {
                writeBoolean(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Short) {
                writeShort(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Integer) {
                writeInt(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Long) {
                writeLong(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Float) {
                writeFloat(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof Double) {
                writeDouble(buffer, varargs[i], i, -1);
            } else if (varargs[i] instanceof String) {
                writeString(buffer, varargs[i], i, -1);
            }

            if (varargs[i] instanceof byte[]) {
                writeByte(buffer, varargs[i], i, ((byte[]) varargs[i]).length);
            } else if (varargs[i] instanceof char[]) {
                writeChar(buffer, varargs[i], i, ((char[]) varargs[i]).length);
            } else if (varargs[i] instanceof boolean[]) {
                writeBoolean(buffer, varargs[i], i, ((boolean[]) varargs[i]).length);
            } else if (varargs[i] instanceof short[]) {
                writeShort(buffer, varargs[i], i, ((short[]) varargs[i]).length);
            } else if (varargs[i] instanceof int[]) {
                writeInt(buffer, varargs[i], i, ((int[]) varargs[i]).length);
            } else if (varargs[i] instanceof long[]) {
                writeLong(buffer, varargs[i], i, ((long[]) varargs[i]).length);
            } else if (varargs[i] instanceof float[]) {
                writeFloat(buffer, varargs[i], i, ((float[]) varargs[i]).length);
            } else if (varargs[i] instanceof double[]) {
                writeDouble(buffer, varargs[i], i, ((double[]) varargs[i]).length);
            } else if (varargs[i] instanceof String[]) {
                writeString(buffer, varargs[i], i, ((String[]) varargs[i]).length);
            }

        }

        return buffer;
    }

    protected void decode(ByteBuf in, List<Object> out) throws Exception {

        //read the message type
        byte typeValue = in.readByte();
        MessageType type = MessageType.fromValue(typeValue);

        //read the length of resquest-response-identifier
        int len_identifier = in.readInt();
        
        //read the resquest-response-identifier
        String str_identifier = null;
        if(len_identifier>0){
            byte[] identifier_bytes = new byte[len_identifier];
            in.readBytes(identifier_bytes);
            str_identifier = new String(identifier_bytes);
        }
        
        //read the number of arguments     
        int argument_length = in.readInt();
        Object[] arguments = new Object[argument_length];
        decode0(in, arguments);
        ChannelMessage msg = MessageFactory.create(type, str_identifier).assign(arguments);
        out.add(msg);
    }


    private void decode0(ByteBuf buffer, Object[] arguments) {

        for (int i = 0; i < arguments.length; i++) {
            //read the variable type
            int variable_type = buffer.readInt();

            //read number of byte used to store the variable type
            int byte_length = buffer.readInt();

            //check if is array by read array length. -1 means it is not an array
            int array_length = buffer.readInt();

            switch (variable_type) {
                case VarType.BYTE ->
                    arguments[i] = readByte(buffer, byte_length, array_length);
                case VarType.CHAR ->
                    arguments[i] = readChar(buffer, byte_length, array_length);
                case VarType.BOOLEAN ->
                    arguments[i] = readBoolean(buffer, byte_length, array_length);
                case VarType.SHORT ->
                    arguments[i] = readShort(buffer, byte_length, array_length);
                case VarType.INT ->
                    arguments[i] = readInt(buffer, byte_length, array_length);
                case VarType.LONG ->
                    arguments[i] = readLong(buffer, byte_length, array_length);
                case VarType.FLOAT ->
                    arguments[i] = readFloat(buffer, byte_length, array_length);
                case VarType.DOUBLE ->
                    arguments[i] = readDouble(buffer, byte_length, array_length);
                case VarType.STRING ->
                    arguments[i] = readString(buffer, byte_length, array_length);
                default -> {
                }
            }

        }

    }

    private Object readByte(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readByte();
        } else {// is an array

            byte[] arr = new byte[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (byte) readByte(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readChar(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readChar();
        } else {// is an array

            char[] arr = new char[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (char) readChar(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readBoolean(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            //NOTE: in the case of boolean we will read as integer
            int var = buffer.readInt();
            return var == 1; //else zero since we expect 1 or 0            
        } else {// is an array

            boolean[] arr = new boolean[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (boolean) readBoolean(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readShort(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readShort();
        } else {// is an array

            short[] arr = new short[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (short) readShort(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readInt(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readInt();
        } else {// is an array

            int[] arr = new int[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (int) readInt(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readLong(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readLong();
        } else {// is an array

            long[] arr = new long[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (long) readLong(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readFloat(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readFloat();
        } else {// is an array

            float[] arr = new float[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (float) readFloat(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readDouble(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            return buffer.readDouble();
        } else {// is an array

            double[] arr = new double[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (double) readDouble(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    private Object readString(ByteBuf buffer, int byte_length, int array_length) {
        if (array_length == -1) {//not an array
            //for the case of string we will get the byte from 
            //the current readable positon with the specified length
            //and then skip the lenght for next read

            ByteBuf buff = buffer.readBytes(byte_length);

            return buff.toString(StandardCharsets.UTF_8);

        } else {// is an array

            String[] arr = new String[array_length];

            for (int i = 0; i < array_length; i++) {
                //read the variable type
                buffer.readInt(); //neccessary to advance the position of the readable index

                //read number of byte used to store the variable type
                byte_length = buffer.readInt();

                //check if is array by read array length. -1 means it is not an array
                int el_arr_lenth = buffer.readInt(); //should be -1

                arr[i] = (String) readString(buffer, byte_length, el_arr_lenth);
            }
            return arr;
        }
    }

    public static void main(String[] args) throws Exception {
        TestMsg test = new TestMsg();
        ByteBuf buffer = Unpooled.buffer(9000);
        ChannelMessage msg = MessageFactory.create(MessageType.LOGGED_IN, "this is my request-response-tracker")
                .assign(
                45,
                84.9,
                63.0,
                6894,
                new String[]{null, "66", null},
                new String[]{"97", "98", "99"},
                false,
                90,
                true,                
                new byte[]{97, 98, 99},
                new boolean[]{false, true, true},
                98.33);

        
        List<Object> en_out = new LinkedList();
        test.encode(buffer, msg, en_out);

        List<Object> de_out = new LinkedList();
        test.decode(buffer, de_out);

        ChannelMessage decoded_msg = (ChannelMessage) de_out.get(0);
        //for (int i = 0; i < decoded_msg.arguments.length; i++) {
        //    System.out.println(decoded_msg.arguments[i]);
        //}

        
        System.out.println(decoded_msg.getStringArray(5)[0]);
        System.out.println(decoded_msg.getDouble(1));
        System.out.println(decoded_msg.getInt(7));
        System.out.println(decoded_msg.getBoolean(8));
        System.out.println(decoded_msg.getBooleanArray(10)[1]);
        System.out.println(decoded_msg.getIdentifier());
        
    }
}
