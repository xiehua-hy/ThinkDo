package com.thinkdo.net;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.thinkdo.entity.GloVariable;
import com.thinkdo.util.CommonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Administrator on 2015/5/25.
 */
public class SocketClient extends Thread {
    protected Socket socket;
    protected Handler handler;
    protected OutputStream out;
    protected InputStream in;

    public SocketClient(Context context, Handler handler) {
        this(context, handler, true);
    }



    public SocketClient(Context context, Handler handler, boolean listen) {
        this.handler = handler;

        String ip = CommonUtil.getIp(GloVariable.ip);
        if (ip == null) return;
        try {
            Log.d("TAG", String.format("IP = %s, port = %d", ip, GloVariable.port));
            socket = new Socket(ip, GloVariable.port);
            Log.d("TAG", "Connect Success");
            in = socket.getInputStream();
            out = socket.getOutputStream();
            if (listen) start();

        } catch (UnknownHostException e) {
            if (handler != null)
                handler.post(new ToastRun(context, "Failed Connect Host"));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��������������
     */
    public void send(int questCode) {
        this.send(questCode, 0);
    }

    public void send(int questCode, int param) {
        this.send(questCode, param, null);
    }

    public void send(int questCode, String msg) {
        this.send(questCode, 0, msg);
    }

    public void send(int questCode, int param, String data) {
        try {
            if (!isClosed()) {
                Log.d("TAG", String.format("Uri: questCode = %d, param = %d, data = %s", questCode, param, data));
                out.write(getQuestUri(questCode, param, data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void sendToHandler(String msg) {
        Log.d("TAG", msg);
        if (handler != null) {
            Bundle bundle = new Bundle();
            bundle.putString(GloVariable.head, msg);
            Message message = handler.obtainMessage();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    @Override
    public void run() {
        if (isClosed()) return;
        byte[] head = new byte[16];
        try {
            while (in.read(head) != -1) {
                String msg = headParse(head);
                if (msg != null) {
                    int dataSize = getStreamDataSize(head);
                    if (dataSize == 0) {
                        sendToHandler(msg);
                    } else if (0 < dataSize && dataSize < 0xc800) {
                        byte[] bytes = new byte[dataSize];

                        if (in.read(bytes) != -1) {
                            String data = dataParse(bytes);
                            sendToHandler(msg + data);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onStop();
        }
    }

    public boolean isClosed() {
        return socket == null || socket.isClosed();
    }

    public void onStop() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (!isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected byte[] getQuestUri(int questCode, int param, String msg) {
        byte[] data;
        int size;

        if (msg == null) {
            data = null;
            size = 0;
        } else {
            data = msg.getBytes();
            size = data.length;
        }

        byte[] stream = new byte[size + 16];

        stream[0] = 0x00;
        stream[1] = 0x00;
        stream[2] = 0x00;
        stream[3] = 0x00;

        stream[4] = (byte) (questCode % 256);
        stream[5] = (byte) ((questCode >> 8) % 256);
        stream[6] = (byte) ((questCode >> 16) % 256);
        stream[7] = (byte) ((questCode >> 24) % 256);

        stream[8] = (byte) (param % 256);
        stream[9] = (byte) ((param >> 8) % 256);
        stream[10] = (byte) ((param >> 16) % 256);
        stream[11] = (byte) ((param >> 24) % 256);

        stream[12] = (byte) (size % 256);
        stream[13] = (byte) ((size >> 8) % 256);
        stream[14] = (byte) ((size >> 16) % 256);
        stream[15] = (byte) ((size >> 24) % 256);

        if (data != null) System.arraycopy(data, 0, stream, 16, size);

        return stream;
    }

    protected int getStreamDataSize(byte[] bytes) {
        return (bytes[12] & 0xFF)
                | ((bytes[13] & 0xFF) << 8)
                | ((bytes[14] & 0xFF) << 16)
                | ((bytes[15] & 0xFF) << 24);
    }

    protected String headParse(byte[] bytes) {
        if (bytes.length == 16) {
            if (bytes[0] == 15 && bytes[1] == 15 && bytes[2] == 15 && bytes[3] == 15) {
                int int1 = (bytes[4])
                        | (bytes[5] << 8)
                        | (bytes[6] << 16)
                        | (bytes[7] << 24);

                int int2 = (bytes[8])
                        | (bytes[9] << 8)
                        | (bytes[10] << 16)
                        | (bytes[11] << 24);

                int int3 = (bytes[12])
                        | (bytes[13] << 8)
                        | (bytes[14] << 16)
                        | (bytes[15] << 24);

                return String.valueOf(int1) + "&" + String.valueOf(int2) + "&" + String.valueOf(int3);
            }
        }
        return null;
    }


    protected String dataParse(byte[] by) {
        if (by[0] == 0 && by[1] == 0) return null;
        return "&&" + new String(by);
    }
}
