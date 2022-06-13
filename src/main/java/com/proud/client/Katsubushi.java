package com.proud.client;

import com.proud.config.KatsubushiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;
@Component
public class Katsubushi {

    private final Socket socket;
    private final OutputStreamWriter outputStreamWriter;
    private final PrintWriter printWriter;

    @Autowired
    private KatsubushiProperties katsubushiProperties;

    private static ExecutorService pool = new ThreadPoolExecutor(3,5,6,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(2),
            Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    public Katsubushi() {
        try {
            this.socket = new Socket(katsubushiProperties.getAddress(), katsubushiProperties.getPort());
            this.outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream());
            this.printWriter = new PrintWriter(this.outputStreamWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void GetId(String name, ReceiveIdCallBack callBack) throws IOException {
        pool.execute(() -> {
            this.printWriter.println("GET " + name);

            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String input = null;
            while (true) {
                try {
                    if ((input = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("接收服务端数据：	" + input);
                break;
            }
        });
    }

    public interface ReceiveIdCallBack {

        public void receive(long id);
    }
}
