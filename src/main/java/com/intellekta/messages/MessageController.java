package com.intellekta.messages;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;
@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/messages", method = {RequestMethod.GET, RequestMethod.POST})
    public String messagesPage(@RequestParam("senderName") String senderName, Model model, HttpSession session) {
        if (senderName != null) {
            session.setAttribute("senderName", senderName);
        } else {
            senderName = (String) session.getAttribute("senderName");
        }
        model.addAttribute("senderName", senderName);
//        // Получение списка записей из базы данных
//        List<SalesJpa> sales = saleRepository.findAll();
//        model.addAttribute("sales", sales);

        return "messages";
    }
    @PostMapping("/messages/add")
//    @Transactional
    public String addMessage(@RequestParam("textMessage") String textMessage, RedirectAttributes redirectAttributes,
                          HttpSession session)  {
        String senderName = (String) session.getAttribute("senderName");
//         Сохранение новой записи в базу данных
        MessageJpa message2 = new MessageJpa();
        message2.setTextMessage(textMessage);
        message2.setSenderMessage(senderName);
        messageRepository.saveAndFlush(message2);
        redirectAttributes.addAttribute("senderName", senderName);
        return "redirect:/messages";
//        return "index";
    }
    @PostMapping("/search")
//    @Transactional
    public String searchMessage(@RequestParam("searchMessages") String searchMessages,
                                RedirectAttributes redirectAttributes,Model model,
                                HttpSession session)  {
        String senderName = (String) session.getAttribute("senderName");
//         Сохранение новой записи в базу данных
        List<MessageJpa> messages = messageRepository.findBySenderMessage(searchMessages);
        model.addAttribute("messages", messages);
        redirectAttributes.addAttribute("senderName", senderName);
        return "redirect:/messages";
//        return "index";
    }
}
