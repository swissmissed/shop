package com.apple.shop.item;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemService.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam Map<String, Object> datas) {
        System.out.println("서버에서 받은 데이터: " + datas);

        if (!datas.containsKey("imageurl")) {
            System.out.println("🚨 'imageurl' 키가 없음! 클라이언트에서 제대로 전달되지 않음.");
        } else {
            System.out.println("✅ 받은 imageUrl: " + datas.get("imageurl"));
        }

        itemService.saveItem(datas);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {

        Optional<Item> result = itemService.findById(id);
        List<Comment> comments = commentRepository.findAllByParentId(id);

        if (result.isPresent()) {
            Item item = result.get();
            model.addAttribute("data", item);

            model.addAttribute("comments", comments);

            return "detail.html";
        }
        else {
            return "redirect:/list";
        }
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {

        Optional<Item> result = itemService.findById(id);
        if (result.isPresent()) {
            Item item = result.get();
            model.addAttribute("data", item);
            return "edit.html";
        }
        else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    String editItem(Long id, String title, Integer price) {
        try {
            itemService.editItem(id, title, price);
        } catch (Exception e) {
            e.printStackTrace();

            return "redirect:/list";
        }

        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/list/page/{abc}")
    String getListPAge(Model model, @PathVariable Integer abc) {

        Page<Item> result = itemRepository.findPageBy(PageRequest.of(abc - 1,5));
        model.addAttribute("items", result);
        return "list.html";
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model) {
        var res = itemRepository.rawQuery1(searchText);
        model.addAttribute("items", res);
        return "searchlist.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        var res = s3Service.createPresignedUrl("test/" + filename);

        return res;
    }

}