package com.lanpang.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
//websocket的url配置
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    /**
     * 存储websocket容器
     */
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        log.info("【websocket消息】 有新的连接，总数：{}"+webSockets.size());
    }
    @OnClose
    public void onClose(Session session){
        webSockets.remove(this);
        log.info("【websocket消息】 连接断开，总数：{}"+webSockets.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】 收到客户端发送的消息：{}"+message);

    }
    public void sendMessage(String message){
        for (WebSocket webSocket : webSockets){

            log.info("【websocket消息】 广播消息：{}"+message);
            try {
                //发送消息
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.err.println("【websocket消息】 广播消息失败!");
                e.printStackTrace();
            }
        }
    }
}
