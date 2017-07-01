package com.bb.yzbbanban.test_retrofit;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by YZBbanban on 2017/6/18.
 */

public class CountingRequestBudy extends RequestBody {
    private RequestBody delegete;
    private Listener listener;
    private CountingSink countingSink;

    public CountingRequestBudy(RequestBody delegete, Listener listener) {
        this.delegete = delegete;
        this.listener = listener;
    }

    public static interface Listener {
        void oRequestProgress(long byteWritten, long contentLength);
    }

    @Override
    public MediaType contentType() {
        return delegete.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegete.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        countingSink = new CountingSink(sink);
        BufferedSink s = Okio.buffer(countingSink);
        delegete.writeTo(s);
        s.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long byteWritten;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            listener.oRequestProgress(byteWritten, contentLength());
        }
    }
}
