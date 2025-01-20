package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Map<String, Object> datas){
        Item item = new Item();
        String title = datas.get("title").toString();
        Integer price = Integer.valueOf(datas.get("price").toString());
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public void editItem(Long id, String title, Integer price) throws Exception {
        if (title.length() >= 100) {
            throw new Exception("상품 제목이 너무 긺");
        }
        if (price < 0) {
            throw new Exception("가격이 잘못 됨");
        }
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }

}
