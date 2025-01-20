package com.apple.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final WhatRepository whatRepository;

    @GetMapping("/date")
    @ResponseBody
    String date() {
        return ZonedDateTime.now().toString();
    }

    @GetMapping("/")
    String home() {
        return "index.html";
    }

    @GetMapping("/texts")
    String getTexts(Model model) {
        List<What> lists = whatRepository.findAll();
        model.addAttribute("texts", lists);
        return "what.html";
    }

}
