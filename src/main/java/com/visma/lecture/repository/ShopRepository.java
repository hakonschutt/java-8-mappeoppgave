package com.visma.lecture.repository;

import com.visma.lecture.common.functions.Functions;
import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class for shop
 *
 * @author Håkon Wang Schütt
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

	public List<Item> getItemsList(){
		return items;
	}

	public List<Item> getItemsWithinRange(int lowerBound, int upperBound){
		return items.stream()
					.filter(e -> e.getItemID() >= lowerBound)
					.filter(e -> e.getItemID() <= upperBound)
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsForLocation(ItemLocation loc){
		return items.stream()
					.filter(e -> e.getItemLocation().equals(loc))
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsForType(ItemType type){
		return items.stream()
					.filter(e -> e.getItemType().equals(type))
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsByProducer(String producer){
		return items.stream()
					.filter(e -> Functions.getSpacedProducer.apply(e).toLowerCase().equals(producer.toLowerCase()))
					.collect(Collectors.collectingAndThen(
						Collectors.toList(),
						list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsByName(String name){
		return items.stream()
					.filter(e -> Functions.getSpacedName.apply(e).toLowerCase().equals(name.toLowerCase()))
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsWithHigherStock(int stock){
		return items.stream()
					.filter(e -> e.getStock() >= stock)
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
	}

	public List<Item> getItemsWithLowerStock(int stock){
		return items.stream()
					.filter(e -> e.getStock() <= stock)
					.collect(Collectors.collectingAndThen(
							Collectors.toList(),
							list -> list.isEmpty() ? null : list
					));
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
