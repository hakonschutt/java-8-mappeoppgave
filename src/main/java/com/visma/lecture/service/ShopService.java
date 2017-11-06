package com.visma.lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.NoItemFoundForCriteriaException;
import com.visma.lecture.common.functions.Functions;
import com.visma.lecture.repository.ShopRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for shop
 *
 * @author Håkon Wang Schutt
 */
public class ShopService {
	
	private final ShopRepository shopRepository;
	
	public ShopService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	public Map<ItemLocation, List<Item>> getLocationMappedWithItems(){
		Map<ItemLocation, List<Item>> locationMapWithItems = shopRepository.getItemsList().stream()
															.collect(Collectors.groupingBy(Item::getItemLocation));

		if (locationMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return locationMapWithItems;
	}

	public Map<ItemType, List<Item>> getItemTypeMappedWithItems(){
		Map<ItemType, List<Item>> itemTypeMapWithItems = shopRepository.getItemsList().stream()
																.collect(Collectors.groupingBy(Item::getItemType));


		if(itemTypeMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return itemTypeMapWithItems;
	}

	public Map<String, List<Item>> getProducerMappedWithItems(){
		Map<String, List<Item>> producerMapWithItems = shopRepository.getItemsList().stream()
														.collect(Collectors.groupingBy(e -> Functions.getProducer.apply(e)));

		if(producerMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return producerMapWithItems;
	}
	
}
