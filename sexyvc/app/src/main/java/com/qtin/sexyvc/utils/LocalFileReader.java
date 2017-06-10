package com.qtin.sexyvc.utils;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ls on 17/6/10.
 */
public class LocalFileReader {
    public static interface ReadListener{
        void complete(String result);
    }

    public void readAssets(final Context context, final String fileName, final ReadListener listener) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream is = null;
                ByteArrayOutputStream out=new ByteArrayOutputStream();
                try {
                    is = context.getAssets().open(fileName);
                    byte[] buffer = new byte[1024];
                    int len = is.read(buffer, 0, 1024);
                    while (len != -1) {
                        out.write(buffer, 0, len);
                        len = is.read(buffer, 0, 1024);
                    };
                    out.flush();
                    listener.complete(new String(out.toByteArray()));

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
