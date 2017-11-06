package com.visma.lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.InvalidCriteriaException;
import com.visma.lecture.common.exception.NoItemFoundForCriteriaException;
import com.visma.lecture.common.functions.Functions;
import com.visma.lecture.repository.ShopRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for shop
 *
 * @author Håkon Wang Schutt
 */
public class ShopService {
	private final String NO_ITEM_STRING = "No items were found for the given search criteria.";
	private final String INVALID_CRITERIA = "Input was null, empty or lower than 0.";
	
	private final ShopRepository shopRepository;
	
	public ShopService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	public Map<ItemLocation, List<Item>> getLocationMappedWithItems(){
		Map<ItemLocation, List<Item>> locationMapWithItems = shopRepository.getItemsList().stream()
															.collect(Collectors.groupingBy(Item::getItemLocation));

		if (locationMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return locationMapWithItems;
	}

	public Map<ItemType, List<Item>> getItemTypeMappedWithItems(){
		Map<ItemType, List<Item>> itemTypeMapWithItems = shopRepository.getItemsList().stream()
																.collect(Collectors.groupingBy(Item::getItemType));


		if(itemTypeMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemTypeMapWithItems;
	}

	public Map<String, List<Item>> getProducerMappedWithItems(){
		Map<String, List<Item>> producerMapWithItems = shopRepository.getItemsList().stream()
														.collect(Collectors.groupingBy(e -> Functions.getProducer.apply(e)));

		if(producerMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return producerMapWithItems;
	}

	public Map<String, List<Item>> getNameMappedWithItems(){
		Map<String, List<Item>> nameMapWithItems = shopRepository.getItemsList().stream()
															.collect(Collectors.groupingBy(e -> Functions.getName.apply(e)));

		if(nameMapWithItems.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return nameMapWithItems;
	}

	public Map<Boolean, List<Item>> getItemsMappedFromStock_1500(){
		final int STOCK = 1500;
		Map<Boolean, List<Item>> booleanStock_1500_Map = shopRepository.getItemsList().stream()
															.collect(Collectors.partitioningBy(e -> e.getStock() > STOCK));

		if(booleanStock_1500_Map.get(true).size() == 0 && booleanStock_1500_Map.get(false).size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return booleanStock_1500_Map;
	}

	public Item getItemFromID(Integer ID){
		if(ID == null || ID <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		Item item = shopRepository.findItemById(ID);

		if(item == null)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return item;
	}

	public String getAllProducersAsString(String joiner){
		if(joiner == null || joiner.length() <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		String producer = shopRepository.getItemsList().stream()
										.map(e -> Functions.getProducer.apply(e))
										.collect(Collectors.joining(joiner));

		if(producer.length() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return producer;
	}

	public List<ItemLocation> getItemLocationWithTotalStockHigherThen(int stock_amount){
		if(stock_amount <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<ItemLocation> itemLocationList = getLocationMappedWithTotalStock().entrySet().stream()
														.filter(l -> l.getValue() > stock_amount )
														.map(l -> l.getKey())
														.collect(Collectors.toList());

		if(itemLocationList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemLocationList;
	}

	public List<ItemLocation> getItemLocationWithTotalStockLowerThen(int stock_amount){
		if(stock_amount <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<ItemLocation> itemLocationList = getLocationMappedWithTotalStock().entrySet().stream()
														.filter(l -> l.getValue() < stock_amount )
														.map(l -> l.getKey())
														.collect(Collectors.toList());

		if(itemLocationList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemLocationList;
	}

	public List<Item> getItemsFromLocationWithHigherStockThan(ItemLocation loc, int stock_amount){
		if(loc == null || stock_amount <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<Item> itemList = shopRepository.getItemsList().stream()
											.filter(e -> e.getItemLocation().equals(loc))
											.filter(e -> e.getStock() > stock_amount)
											.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public List<Item> getItemsFromLocationWithLowerStockThan(ItemLocation loc, int stock_amount){
		if(loc == null || stock_amount <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<Item> itemList = shopRepository.getItemsList().stream()
											.filter(e -> e.getItemLocation().equals(loc))
											.filter(e -> e.getStock() < stock_amount)
											.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public List<Item> getItemsBySearchOfName(String name){
		if(name.length() <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		final String NAME = name.toLowerCase();

		List<Item> itemList = shopRepository.getItemsList().stream()
											.filter(e -> Functions.getSpacedName.apply(e).toLowerCase().startsWith(NAME))
											.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public double findAverageStockFromLocation(ItemLocation location){
		if(location == null)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		return shopRepository.getItemsList().stream()
				.filter(e -> e.getItemLocation().equals(location))
				.collect(Collectors.averagingDouble(Item::getStock));

		// No point validating if its empty. It returns 0.0 anyways if it is.
		// This means the user will always get the correct answer.
	}

	public Item findItemWithHighestStock(){
		return shopRepository.getItemsList().stream()
								.max(Comparator.comparing(Item::getStock))
								.get();
	}

	public Item findItemWithLowestStock(){
		return shopRepository.getItemsList().stream()
								.min(Comparator.comparing(Item::getStock))
								.get();
	}

	public List<Item> findItemsFromLocationWithHigherStockThan(ItemLocation location, int stock_value){
		if(location == null || stock_value < 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<Item> itemList = shopRepository.getItemsList().stream()
									.filter(e -> e.getItemLocation().equals(location))
									.filter(e -> e.getStock() > stock_value)
									.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public List<Item> findItemsAlphabeticallyByProducer(){
		List<Item> itemList = shopRepository.getItemsList().stream()
									.sorted((e1, e2) -> Functions.getProducer.apply(e1).compareTo(Functions.getProducer.apply(e2)))
									.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public List<Item> findItemsAlphabeticallyByName(){
		List<Item> itemList = shopRepository.getItemsList().stream()
									.sorted((e1, e2) -> Functions.getName.apply(e1).compareTo(Functions.getName.apply(e2)))
									.collect(Collectors.toList());

		if(itemList.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return itemList;
	}

	public List<Item> findItemsSortedByStockFromHighToLow(){
		List<Item> items = shopRepository.getItemsList().stream()
									.sorted((e1, e2) -> e2.getStock().compareTo(e1.getStock()))
									.collect(Collectors.toList());

		if(items.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return items;
	}

	public List<Item> findItemsWithoutDuplicates(){
		List<Item> items = shopRepository.getItemsList().stream()
										.distinct()
										.collect(Collectors.toList());

		if(items.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return items;
	}

	/**
	 * Given that the first assignment explicitly said that it had to return null if a list was empty.
	 * I have to do this patch width setting first and second range.
	 * If you try Stream.of a list that's null the whole thing crashes. Hens the security measures.
	 */
	public List<Item> createListOfItemsBetweenTwoRanges(int a, int b, int x, int y){
		if(a < 0 || b < 0 || x < 0 || y < 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<Item> firstRange = shopRepository.getItemsWithinRange(a, b) == null ? new ArrayList<>() : shopRepository.getItemsWithinRange(a, b);
		List<Item> secondRange = shopRepository.getItemsWithinRange(x, y) == null ? new ArrayList<>() : shopRepository.getItemsWithinRange(x, y);

		List<Item> finalItems = Stream.of(firstRange, secondRange)
									.flatMap(List::stream)
									.distinct()
									.collect(Collectors.toList());

		if(finalItems.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return finalItems;
	}

	/**
	 * Given that the first assignment explicitly said that it had to return null if a list was empty.
	 * I have to do this patch width setting first and second range.
	 * If you try Stream.of a list that's null the whole thing crashes. Hens the security measures.
	 */
	public List<Item> createListOfItemsFromProducerTypeAndLocation(ItemLocation location, ItemType type, String producer){
		if (location == null || type == null || producer.length() <= 0)
			throw new InvalidCriteriaException(INVALID_CRITERIA);

		List<Item> firstRange = shopRepository.getItemsForLocation(location) == null ? new ArrayList<>() : shopRepository.getItemsForLocation(location);
		List<Item> secondRange = shopRepository.getItemsForType(type) == null ? new ArrayList<>() : shopRepository.getItemsForType(type);
		List<Item> thirdRange = shopRepository.getItemsByProducer(producer) == null ? new ArrayList<>() : shopRepository.getItemsByProducer(producer);

		List<Item> items = Stream.of(firstRange, secondRange, thirdRange)
									.flatMap(List::stream)
									.distinct()
									.collect(Collectors.toList());

		if(items.size() == 0)
			throw new NoItemFoundForCriteriaException(NO_ITEM_STRING);

		return items;
	}

	public double getTotalStock(){
		return shopRepository.getItemsList().stream()
							.mapToDouble(Item::getStock)
							.sum();
	}

	private Map<ItemLocation, Integer> getLocationMappedWithTotalStock(){
		return shopRepository.getItemsList().stream()
							.collect(Collectors.groupingBy(
									Item::getItemLocation,
									Collectors.summingInt(Item::getStock))
							);
	}
	
}
