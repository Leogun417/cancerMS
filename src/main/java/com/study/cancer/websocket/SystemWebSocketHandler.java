package com.study.cancer.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * websocket处理的类，包括发送消息、获取用户在线等功能
 *
 */
@Component("webSockertHandle")
public class SystemWebSocketHandler implements WebSocketHandler {
	private static final int HEARTBEAT_INTERVAL = 60 * 1000;// js端心跳的时间必须大于这里
	private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);

	private static final ConcurrentMap<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	/**
	 * 定时发送心跳消息到客户端,以应对断网客户端服务端无法感知的问题
	 */
	static {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (Map.Entry<String, WebSocketSession> entry : webSocketSessionMap.entrySet()) {
					try {
						WebSocketSession session = entry.getValue();
						session.sendMessage(new TextMessage("heartbeat"));
					} catch (Exception e) {
						logger.warn(entry.getKey() + "用户的websocket貌似已断开连接");
						webSocketSessionMap.remove(entry.getKey());
					}

				}

			}
		}, 1000, HEARTBEAT_INTERVAL);

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("connect to the websocket success......");
		String userId = (String) session.getAttributes().get("loginUserId");
		// 应对多个地方登陆一个账号
		if (webSocketSessionMap.containsKey(userId) && webSocketSessionMap.get(userId).isOpen()) {
			webSocketSessionMap.get(userId).close();
		}
		webSocketSessionMap.put(userId, session);
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 *
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message.getPayloadLength() == 0 || message.getPayload().toString().equals("heartbeat"))
			return;
		// sendMessageToUsers();
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.warn("websocket connection closed......", exception);
		webSocketSessionMap.remove(session.getAttributes().get("loginUserId"));
	}

	/**
	 * 客服端断开移除客服端session
	 *
	 * @param session
	 * @param closeStatus
	 * @throws Exception
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.debug("websocket connection closed......");
		// 应对多个地方登陆一个账号
		if (session.equals(webSocketSessionMap.get(session.getAttributes().get("loginUserId")))) {
			webSocketSessionMap.remove(session.getAttributes().get("loginUserId"));
		}

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message 消息
	 */
	public void sendMessageToUsers(TextMessage message) {
		// TODO
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userId
	 *          用户的userId
	 * @param message
	 *          消息
	 */
	public void sendMessageToUser(String userId, TextMessage message) {
		try {
			WebSocketSession session = webSocketSessionMap.get(userId);
			if (session != null) {
				webSocketSessionMap.get(userId).sendMessage(message);
			}
		} catch (IOException e) {
			logger.error("websocker发送失败", e);
		}
	}

	/**
	 * 判断用户是否在线
	 * @param userId
	 * @return
	 */
	public boolean isUserOnline(String userId) {
		if (webSocketSessionMap.containsKey(userId)) {
			return true;
		}
		return false;

	}
}
