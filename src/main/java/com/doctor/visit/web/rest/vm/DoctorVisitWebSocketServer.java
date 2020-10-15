package com.doctor.visit.web.rest.vm;

import com.doctor.visit.service.WebSocketMessageService;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kuanwang
 * websock 的服务层
 * https://my.oschina.net/u/3580577/blog/2088114
 * https://blog.csdn.net/qq_38455201/article/details/80374712
 * <p>
 * 所有的配置项都在这个注解的属性中
 * <p>
 * 属性	默认值	说明
 * path	"/"	WebSocket的path,也可以用value来设置
 * host	"0.0.0.0"	WebSocket的host,"0.0.0.0"即是所有本地地址
 * port	80	WebSocket绑定端口号。如果为0，则使用随机端口(端口获取可见 多端点服务)
 * bossLoopGroupThreads	0	bossEventLoopGroup的线程数
 * workerLoopGroupThreads	0	workerEventLoopGroup的线程数
 * useCompressionHandler	false	是否添加WebSocketServerCompressionHandler到pipeline
 * optionConnectTimeoutMillis	30000	与Netty的ChannelOption.CONNECT_TIMEOUT_MILLIS一致
 * optionSoBacklog	128	与Netty的ChannelOption.SO_BACKLOG一致
 * childOptionWriteSpinCount	16	与Netty的ChannelOption.WRITE_SPIN_COUNT一致
 * childOptionWriteBufferHighWaterMark	64*1024	与Netty的ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK一致,但实际上是使用ChannelOption.WRITE_BUFFER_WATER_MARK
 * childOptionWriteBufferLowWaterMark	32*1024	与Netty的ChannelOption.WRITE_BUFFER_LOW_WATER_MARK一致,但实际上是使用 ChannelOption.WRITE_BUFFER_WATER_MARK
 * childOptionSoRcvbuf	-1(即未设置)	与Netty的ChannelOption.SO_RCVBUF一致
 * childOptionSoSndbuf	-1(即未设置)	与Netty的ChannelOption.SO_SNDBUF一致
 * childOptionTcpNodelay	true	与Netty的ChannelOption.TCP_NODELAY一致
 * childOptionSoKeepalive	false	与Netty的ChannelOption.SO_KEEPALIVE一致
 * childOptionSoLinger	-1	与Netty的ChannelOption.SO_LINGER一致
 * childOptionAllowHalfClosure	false	与Netty的ChannelOption.ALLOW_HALF_CLOSURE一致
 * readerIdleTimeSeconds	0	与IdleStateHandler中的readerIdleTimeSeconds一致，并且当它不为0时，将在pipeline中添加IdleStateHandler
 * writerIdleTimeSeconds	0	与IdleStateHandler中的writerIdleTimeSeconds一致，并且当它不为0时，将在pipeline中添加IdleStateHandler
 * allIdleTimeSeconds	0	与IdleStateHandler中的allIdleTimeSeconds一致，并且当它不为0时，将在pipeline中添加IdleStateHandler
 * maxFramePayloadLength	65536	最大允许帧载荷长度
 */
//@ServerEndpoint(host = "${ws.host}",port = "${ws.port}")
//接下来即可在application.properties中配置
//ws.host=0.0.0.0
//ws.port=80

@Component
@ServerEndpoint(path = "/im/{sid}", port = "${ws.port}")
public class DoctorVisitWebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(DoctorVisitWebSocketServer.class);

    @Autowired
    private WebSocketMessageService webSocketMessageService;

    private static final AtomicInteger onLineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();

    /**
     * 当有新的连接进入时，对该方法进行回调 注入参数的类型:Session、HttpHeaders ...
     *
     * @param session
     * @param headers
     * @param req
     * @param reqMap
     * @param arg
     * @param pathMap
     */
    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap) {
        session.setSubprotocols("stomp");
        if (StringUtils.isNotBlank(req) && !req.equals("ok")) {
            logger.debug("Authentication failed!");
            session.close();
        }
    }

    /**
     * 打开connection
     * 当有新的WebSocket连接完成时，对该方法进行回调 注入参数的类型:Session、HttpHeaders...
     *
     * @param session
     * @param headers
     * @param req
     * @param reqMap
     * @param arg
     * @param pathMap
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String arg, @PathVariable Map pathMap) {
        logger.debug("new connection");
        session.setAttribute("sid", pathMap.get("sid"));

        sessionSet.add(session);
        int cnt = onLineCount.incrementAndGet();
        logger.info("有连接加入，当前连接数为：{} ", cnt);
        session.sendText("连接成功");
    }

    /**
     * close connection
     * 当有WebSocket连接关闭时，对该方法进行回调 注入参数的类型:Session
     *
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        logger.debug("one connection closed");
        sessionSet.remove(session);
        int cnt = onLineCount.decrementAndGet();
        logger.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 异常
     * 当有WebSocket抛出异常时，对该方法进行回调 注入参数的类型:Session、Throwable
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
        logger.error(throwable.getMessage());
    }

    /**
     * 消息
     * 当接收到字符串消息时，对该方法进行回调 注入参数的类型:Session、String
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        //收到消息
        logger.info("session-->{},message-->{}", session, message);

        //发送消息-想发给谁、单发还是群发、还是图片之类的
        session.sendText(message);

    }

    /**
     * 发送字节码
     * 当接收到二进制消息时，对该方法进行回调 注入参数的类型:Session、byte[]
     *
     * @param session
     * @param bytes
     */
    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        logger.debug("byte message");
        session.sendBinary(bytes);
    }

    /**
     * 发送消息
     * @param id
     * @param message
     * @return
     */
    public static boolean sendMessage(String id, String message) {
        Session session = null;
        for (Session s : sessionSet) {
            if (id.equalsIgnoreCase(s.getAttribute("sid"))) {
                session = s;
                break;
            }
        }
        if (null != session) {
            session.sendText(message);
            return true;
        }
        return false;
    }

    /**
     * 当接收到Netty的事件时，对该方法进行回调 注入参数的类型:Session、Object
     *
     * @param session
     * @param evt
     */
    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    logger.debug("read idle");
                    break;
                case WRITER_IDLE:
                    logger.debug("write idle");
                    break;
                case ALL_IDLE:
                    logger.debug("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
