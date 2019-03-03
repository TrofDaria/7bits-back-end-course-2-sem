package it.sevenbits.courses.practices.base.spring.core.repository;

import java.util.List;

public interface ItemsRepository {
    List<Item> getAllItems();
    Item create(Item item);
}
