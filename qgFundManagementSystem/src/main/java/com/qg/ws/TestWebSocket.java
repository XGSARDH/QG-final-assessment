package com.qg.ws;

import com.qg.service.FundService;
import com.qg.util.JwtUtils.JwtUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/userFundsWebSocket")
public class TestWebSocket {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New connection opened");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        // 解析接收到的消息，这里假设客户端发送的是用户ID
        Claims claims = JwtUtils.parseJWT(message);
        Long userId = Long.valueOf(claims.get("userId", String.class));

        // 获取用户资金数据
        String userFunds = FundService.getInstance().viewUserFunds(userId);

        // 发送数据回客户端
        session.getBasicRemote().sendText("{\"status\":200, \"message\":\"请求成功\", \"data\":" + userFunds + "}");
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }
}
