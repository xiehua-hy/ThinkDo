package com.thinkdo.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.thinkdo.entity.GloVariable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/25.
 */
public class SocketClientPic extends SocketClient {
    public SocketClientPic(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    public void run() {
        if (isClose()) return;
        byte[] head = new byte[16];
        try {
            while (in.read(head) != -1) {
                String msg = headParse(head);
                if (msg != null) {
                    int size = getStreamDataSize(head);
                    if (size > 0 && size < 0xc800) {
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(size);

                        int div = size / 1024;
                        int remain = size % 1024;

                        byte[] byte1 = new byte[remain];
                        byte[] byte2 = new byte[1024];

                        if (in.read(byte1) != -1)
                            byteArray.write(byte1);

                        for (int i = 0; i < div && in.read(byte2) != -1; i++) {
                            byteArray.write(byte2);
                        }

                        InputStream input = new ByteArrayInputStream(byteArray.toByteArray());
                        Bitmap bitmap = BitmapFactory.decodeStream(input);

                        sendToHandler(msg, bitmap);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onStop();
        }
    }

    //向handle发送数据
    public void sendToHandler(String msg, Bitmap bitmap) {
        Bundle bundle = new Bundle();
        bundle.putString(GloVariable.head, msg);
        bundle.putParcelable(GloVariable.simpleBitmap, bitmap);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
