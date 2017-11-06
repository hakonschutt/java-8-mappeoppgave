package com.visma.lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.InvalidCriteriaException;
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

	public Map<String, List<Item>> getNameMappedWithItems(){
		Map<String, List<Item>> nameMapWithItems = shopRepository.getItemsList().stream()
															.collect(Collectors.groupingBy(e -> Functions.getName.apply(e)));

		if(nameMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return nameMapWithItems;
	}

	public Map<Boolean, List<Item>> getItemsMappedFromStock_1500(){
		final int STOCK = 1500;
		Map<Boolean, List<Item>> booleanStock_1500_Map = shopRepository.getItemsList().stream()
															.collect(Collectors.partitioningBy(e -> e.getStock() > STOCK));

		if(booleanStock_1500_Map.get(true).size() == 0 && booleanStock_1500_Map.get(false).size() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return booleanStock_1500_Map;
	}

	public Item getItemFromID(Integer ID){
		if(ID == null || ID <= 0)
			throw new InvalidCriteriaException("Input was null, empty or lower than 0.");

		Item item = shopRepository.findItemById(ID);

		if(item == null)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return item;
	}

	public String getAllProducersAsString(String joiner){
		if(joiner == null || joiner.length() <= 0)
			throw new InvalidCriteriaException("Input was null, empty or lower than 0.");

		String producer = shopRepository.getItemsList().stream()
										.map(e -> Functions.getProducer.apply(e))
										.collect(Collectors.joining(joiner));

		if(producer.length() == 0)
			throw new NoItemFoundForCriteriaException("No items were found for the given search criteria.");

		return producer;
	}
	
}
