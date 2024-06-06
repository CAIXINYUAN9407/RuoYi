package com.ruoyi.web.test;

public class TunaWebSocketClientExample {
//    public static void main(String[] args) {
//        /*
//         * 开放平台1688环境
//         */
//        String url = "ws://message.1688.com/websocket";
//        /**
//         * 您的 AppKey
//         */
//        String appKey = "yourAppKey";
//        /**
//         * 您的应用秘钥
//         */
//        String secret = "yourSecret";
//        /**
//         * 您客户端设置的接收线程池大小 默认为虚拟机内核数*40 用户可以自行修改
//         */
//        int threadNum = 160;
//
//        /**
//         * 1. 创建 Client，如果不传入threadNum参数的话，client将使用默认线程数
//         */
//        TunaWebSocketClient client = new TunaWebSocketClient(appKey, secret, url);
//        /**
//         * 2. 创建 消息处理 Handler
//         */
//        WebSocketMessageHandler tunaMessageHandler = new WebSocketMessageHandler() {
//            /**
//             * 消费消息。
//             *
//             */
//            public boolean onMessage(WebSocketMessage message) throws MessageProcessException {
//                boolean success = true;
//                /**
//                 * 服务端推送的消息分为2种，
//                 * 业务数据：SERVER_PUSH
//                 * 系统消息：SYSTEM，如 appKey 与 secret 不匹配等，一般可忽略
//                 */
//                if(WebSocketMessageType.SERVER_PUSH.name().equals(message.getType())) {
//                    try {
//                        /**
//                         * json串
//                         */
//                        System.out.println("message: " + message);
//                    } catch (Exception e) {
//
//                    }
//                }
//                /**
//                 * 只能返回true，否则会导致消息一直处于发送中状态
//                 */
//                return success;
//            }
//        };
//        client.setTunaMessageHandler(tunaMessageHandler);
//
//        /**
//         * 3. 启动 Client
//         */
//        client.connect();
//
//        /**
//         * 4. 在应用关闭时，关闭客户端
//         */
//        // client.shutdown();
//    }
}
