package com.zhao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 16:30
 * @描述
 */
public class TestMq {
    Connection connection;
    @Before
    public void init() throws JMSException {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.92.129:61616");
        //创建Connection
        connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
    }
    @Test
    public void sendQueue() throws JMSException {
        //创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
        //创建一个Producer对象。
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage("hello activemq");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
    }


   // @Test
    public void receiveQueue() throws JMSException, IOException {
        //创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
        //创建一个MessageConsumer
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                 if(message instanceof TextMessage){
                     System.out.println(message.getClass());
                     try {
                         System.out.println(((TextMessage)message).getText());
                     } catch (JMSException e) {
                         e.printStackTrace();
                     }
                 }
            }
        });
        System.in.read();
        //关闭资源
        //consumer.close();
        //session.close();
    }

    @After
    public void destroy() throws JMSException {
        //connection.close();
    }
}