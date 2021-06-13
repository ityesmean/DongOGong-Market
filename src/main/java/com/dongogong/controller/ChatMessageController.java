package com.dongogong.controller;

import com.dongogong.domain.ChatMessage;
import com.dongogong.domain.ChatSummary;
import com.dongogong.domain.Post;
import com.dongogong.service.ChatMessageFacade;
import com.dongogong.service.UserInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatMessageController {

    @Autowired
    private ChatMessageFacade chatMessageFacade;
    @Autowired
    private UserInfoFacade userInfoFacade;

    @GetMapping("/chat/room/{userId}")
    public ModelAndView showChatList(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSession userSession =
                (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        model.addAttribute("chatRoomList", chatMessageFacade.getChatRoomList(userId));
        model.addAttribute("userSession", userSession);
        model.addAttribute("userId", userSession.getUserInfo().getUserId());

        return new ModelAndView("showChatRoom");
    }

    @RequestMapping("/chat/message.do")
    public ModelAndView showChatMessage(@RequestParam("chatRelationIdx") int relationIdx,
                                        HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSession userSession =
                (UserSession) WebUtils.getSessionAttribute(request, "userSession");


//        mav.addObject("post", postFacade.getPostIdx(postIdx));
        List<ChatSummary> chatMessageList = chatMessageFacade.getChatMessageList(relationIdx);
        model.addAttribute("chatMessageList", chatMessageList);
        model.addAttribute("userSession", userSession);
        model.addAttribute("userId", userSession.getUserInfo().getUserId());

        String readYn = chatMessageList.get(chatMessageList.size() - 1).getReadYn();

        if (chatMessageList.get(((ArrayList) chatMessageList).size() - 1).getReadYn().equals("N")) {
            if (chatMessageList.get(((ArrayList) chatMessageList).size() - 1).getReceiverId().equals(userSession.getUserInfo().getUserId())) {
                chatMessageFacade.updateReadYn(relationIdx, userSession.getUserInfo().getUserId());
            }
        }

        if (chatMessageList.get(0).getSenderId().equals(userSession.getUserInfo().getUserId())) {
            model.addAttribute("chatRoomName", userInfoFacade.getUserInfo(chatMessageList.get(0).getReceiverId()).getNickName());
        } else {
            model.addAttribute("chatRoomName", userInfoFacade.getUserInfo(chatMessageList.get(0).getSenderId()).getNickName());
        }

        return new ModelAndView("showChatMessage");
    }

    @RequestMapping("/send/room/message.do")
    public Map<String, Boolean> sendMessageOnChat(@RequestBody ChatMessage chatMessage, HttpServletRequest request, HttpServletResponse reponse) {
        chatMessageFacade.insertMessage(chatMessage);

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("result", true);

        return map;
    }

    //    chatRoomList 모델맵객체에 저장
    @RequestMapping("/send/post/message.do")
    public String sendMessageOnPost(@ModelAttribute("post") Post post, @ModelAttribute("ChatMessage") ChatMessage chatMessage, HttpServletRequest request, HttpServletResponse response) {

        UserSession userSession =
                (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        boolean check = chatMessageFacade.isRelationExist(userSession.getUserInfo().getUserId(), post.getRegisterId());

        if (!check) {
            chatMessageFacade.insertRelation(userSession.getUserInfo().getUserId(), post.getRegisterId());
        }

        int relationIdx = chatMessageFacade.findRelationIdx(userSession.getUserInfo().getUserId(), post.getRegisterId());

        chatMessage.setChatRelationIdx(relationIdx);

        chatMessageFacade.insertMessage(chatMessage);

        return "index";
    }
}
