package com.visma.lecture.repository;

import com.visma.lecture.common.domain.Item;

import java.util.List;

/**
 * Repository class for shop
 *
 * @author Leo-Andreas Ervik
 */
public class ShopRepository {

	private final List<Item> items;

	public ShopRepository(List<Item> items) {
		this.items = items;
	}

	public Item findItemById(Integer id) {
		return items.stream()
				.filter(e -> e.getItemID().equals(id))
				.findFirst()
				.orElse(null);
	}

	public Boolean create(Item item) {
		return items.add(item);
	}

	public Boolean update(Item item) {
		Item i = findItemById(item.getItemID());
		delete(i.getItemID());
		return create(item);
	}

	public Boolean delete(Integer itemId) {
		return items.removeIf(e -> e.getItemID().equals(itemId));
	}
}
