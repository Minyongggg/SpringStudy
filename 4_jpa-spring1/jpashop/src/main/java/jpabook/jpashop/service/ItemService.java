package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.controller.BookUpdateDto;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItem(Long itemId, BookUpdateDto dto) {
        Book item = (Book) itemRepository.findOne(itemId);
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setAuthor(dto.getAuthor());
        item.setStockQuantity(dto.getStockQuantity());
        item.setIsbn(dto.getIsbn());
    }

    @Transactional(readOnly = true)
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
