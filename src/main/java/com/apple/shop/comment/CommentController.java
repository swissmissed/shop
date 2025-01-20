package com.apple.shop.comment;

import com.apple.shop.Member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(@RequestParam String content, @RequestParam Long parent, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(user.getUsername());
        comment.setParentId(parent);
        commentRepository.save(comment);
        return "redirect:/list";
    }
}
