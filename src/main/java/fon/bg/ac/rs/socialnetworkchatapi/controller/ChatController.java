package fon.bg.ac.rs.socialnetworkchatapi.controller;

import fon.bg.ac.rs.socialnetworkchatapi.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    //so we can create dynamic topics with this template

    @MessageMapping("/message") // when user is sending message to web socket he will be using /app/message topic
    @SendTo("/chatroom/public")
    //when user wants to receive message from this public chatroom he needs to listen on /chatroom/public topic
    private Message receivePublicMessage(@Payload Message message) {

        return message;
    }

    @MessageMapping("/private-message")
    private Message receivePrivateMessage(@Payload Message message) {

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        //user ce slusati na /user/USERNAME/private
        return message;
    }

}
