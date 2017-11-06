package lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.functions.Functions;
import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.service.ShopService;
import lecture.util.ShopTestUtil;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopServiceTest {
	
	private ShopRepository shopRepository;
	private ShopService shopService;
	
	@Before
	public void setUp() throws Exception {
		shopRepository = new ShopRepository(new ShopTestUtil().getItems());
		shopService = new ShopService(shopRepository);
	}

	@Test
	public void getLocationMappedWithItemsTest(){
		Map<ItemLocation, List<Item>> locationMap = shopService.getLocationMappedWithItems();
		assertEquals(2, locationMap.size());
		assertEquals(3, locationMap.get(ItemLocation.OSLO).size());
		assertEquals(3, locationMap.get(ItemLocation.HAMAR).size());
	}

	@Test
	public void getItemTypeMappedWithItemsTest(){
		Map<ItemType, List<Item>> typeMap = shopService.getItemTypeMappedWithItems();

		assertEquals(2, typeMap.size());
		assertEquals(3, typeMap.get(ItemType.BEVERAGE).size());
		assertEquals(3, typeMap.get(ItemType.ELECTRONICS).size());
	}

	@Test
	public void getProducerMappedWithItemsTest(){
		Map<String, List<Item>> producerListMap = shopService.getProducerMappedWithItems();

		assertEquals(6, producerListMap.size());

		int i = 0;
		while (i < producerListMap.size()){
			String producer = "Producer_" + (i + 1);

			assertEquals(1, producerListMap.get(producer).size());
			i++;
		}
	}

	@Test
	public void getNameMappedWithItemsTest(){
		Map<String, List<Item>> nameListMap = shopService.getNameMappedWithItems();

		assertEquals(6, nameListMap.size());

		int i = 0;
		while (i < nameListMap.size()){
			String name = "Test" + (i + 1);

			assertEquals(1, nameListMap.get(name).size());
			i++;
		}
	}

	@Test
	public void getItemsMappedFromStock_1500_Test(){
		Map<Boolean, List<Item>> stockCountMap = shopService.getItemsMappedFromStock_1500();

		assertEquals(3, stockCountMap.get(true).size());
		assertEquals(3, stockCountMap.get(false).size());
	}

	@Test
	public void getItemFromIDTest(){
		final Integer ID = 2002;
		Item item = shopService.getItemFromID(ID);
		assertNotNull(item);

		assertEquals(ID, item.getItemID());
	}

	@Test
	public void getAllProducersAsStringTest(){
		String producer = shopService.getAllProducersAsString("x");

		String expectedResult = "Producer_1xProducer_2xProducer_3xProducer_4xProducer_5xProducer_6";

		assertNotNull(producer);
		assertEquals(expectedResult, producer);
	}

	@Test
	public void getItemLocationWithTotalStockHigherThenTest(){
		final int STOCK = 2000;
		List<ItemLocation> locationList = shopService.getItemLocationWithTotalStockHigherThen(STOCK);

		assertEquals(2, locationList.size());
	}

	@Test
	public void getItemLocationWithTotalStockLowerThenTest(){
		final int STOCK = 5000;
		List<ItemLocation> locationList = shopService.getItemLocationWithTotalStockLowerThen(STOCK);

		assertEquals(1, locationList.size());
	}

	@Test
	public void getItemsFromLocationWithHigherStockThanTest(){
		final int STOCK_AMOUNT = 1000;
		List<Item> osloItems = shopService.getItemsFromLocationWithHigherStockThan(ItemLocation.OSLO, STOCK_AMOUNT);
		List<Item> hamarItems = shopService.getItemsFromLocationWithHigherStockThan(ItemLocation.HAMAR, STOCK_AMOUNT);

		assertEquals(2, osloItems.size());
		assertEquals(1, hamarItems.size());

		osloItems.stream().forEach(e -> {
			assertEquals(e.getItemLocation(), ItemLocation.OSLO);
			assertTrue(e.getStock() > STOCK_AMOUNT);
		});

		hamarItems.stream().forEach(e -> {
			assertEquals(e.getItemLocation(), ItemLocation.HAMAR);
			assertTrue(e.getStock() > STOCK_AMOUNT);
		});
	}

	@Test
	public void getItemsFromLocationWithLowerStockThanTest(){
		final int STOCK_AMOUNT = 1000;
		List<Item> osloItems = shopService.getItemsFromLocationWithLowerStockThan(ItemLocation.OSLO, STOCK_AMOUNT);
		List<Item> hamarItems = shopService.getItemsFromLocationWithLowerStockThan(ItemLocation.HAMAR, STOCK_AMOUNT);

		assertEquals(1, osloItems.size());
		assertEquals(2, hamarItems.size());

		osloItems.stream().forEach(e -> {
			assertEquals(e.getItemLocation(), ItemLocation.OSLO);
			assertTrue(e.getStock() < STOCK_AMOUNT);
		});

		hamarItems.stream().forEach(e -> {
			assertEquals(e.getItemLocation(), ItemLocation.HAMAR);
			assertTrue(e.getStock() < STOCK_AMOUNT);
		});
	}

	@Test
	public void getItemsBySearchOfNameTest(){
		List<Item> itemsWithGeneralSearch = shopService.getItemsBySearchOfName("te");
		List<Item> itemsWithSpecificSearch = shopService.getItemsBySearchOfName("test2");

		assertEquals(6, itemsWithGeneralSearch.size());
		assertEquals(1, itemsWithSpecificSearch.size());
	}

	@Test
	public void findAverageStockFromLocationTest(){
		double osloAvgStock = shopService.findAverageStockFromLocation(ItemLocation.OSLO);
		double hamarAvgStock = shopService.findAverageStockFromLocation(ItemLocation.HAMAR);

		assertEquals(1700.3, osloAvgStock, 0.1);
		assertEquals(1351.6, hamarAvgStock, 0.1);
	}

	@Test
	public void findItemWithHighestStockTest(){
		Item item = shopService.findItemWithHighestStock();

		assertEquals((Integer) 4000, item.getStock());
	}

	@Test
	public void findItemWithLowestStockTest(){
		Item item = shopService.findItemWithLowestStock();

		assertEquals((Integer) 5, item.getStock());
	}

	@Test
	public void findItemsFromLocationWithHigherStockThanTest(){
		List<Item> osloItemList = shopService.findItemsFromLocationWithHigherStockThan(ItemLocation.OSLO, 3000);
		List<Item> hamarItemList = shopService.findItemsFromLocationWithHigherStockThan(ItemLocation.HAMAR, 10);

		assertEquals(1, osloItemList.size());
		assertEquals(2, hamarItemList.size());

		osloItemList.stream().forEach(e -> {
			assertEquals(ItemLocation.OSLO, e.getItemLocation());
			assertTrue(e.getStock() > 3000);
		});

		hamarItemList.stream().forEach(e -> {
			assertEquals(ItemLocation.HAMAR, e.getItemLocation());
			assertTrue(e.getStock() > 10);
		});
	}

	@Test
	public void findItemsAlphabeticallyByProducerTest(){
		List<Item> itemList = shopService.findItemsAlphabeticallyByProducer();

		for (int i = 0; i < itemList.size() - 1; i++){
			String first = Functions.getProducer.apply(itemList.get(i));
			String last = Functions.getProducer.apply(itemList.get(i + 1));

			assertTrue(last.compareTo(first) > 0);
		}
	}

	@Test
	public void findItemsAlphabeticallyByNameTest(){
		List<Item> items = shopService.findItemsAlphabeticallyByName();

		for (int i = 0; i < items.size() - 1; i++){
			String first = Functions.getName.apply(items.get(i));
			String last = Functions.getName.apply(items.get(i + 1));

			assertTrue(last.compareTo(first) > 0);
		}
	}

	@Test
	public void findItemsSortedByStockFromHighToLowTest(){
		List<Item> items = shopService.findItemsSortedByStockFromHighToLow();

		for (int i = 0; i < items.size() - 1; i++){
			int first = items.get(i).getStock();
			int last = items.get(i + 1).getStock();

			assertTrue(first >= last);
		}
	}

	@Test
	public void findItemsWithoutDuplicatesTest(){
		List<Item> items = shopService.findItemsWithoutDuplicates();

		for (int i = 0; i < items.size(); i++){
			for (int j = i+1; j < items.size(); j++){
				assertFalse(items.get(i).equals(items.get(j)));
			}
		}
	}

	@Test
	public void createListOfItemsBetweenTwoRangesTest(){
		List<Item> items = shopService.createListOfItemsBetweenTwoRanges(2000, 2004, 2003, 2005);

		items.stream().forEach(e -> {
			assertTrue(e.getItemID() >= 2000);
			assertTrue(e.getItemID() <= 2005);
		});
	}

	@Test
	public void createListOfItemsFromProducerTypeAndLocationTest(){
		List<Item> items = shopService.createListOfItemsFromProducerTypeAndLocation(ItemLocation.OSLO, ItemType.BEVERAGE, "Producer 5");

		items.stream().forEach(e -> assertTrue(
				e.getItemLocation() == ItemLocation.OSLO
						|| e.getItemType() == ItemType.BEVERAGE
						|| e.getItemName().substring(0, e.getItemName().indexOf(" ")).equals("Producer_5")));

		for (int i = 0; i < items.size(); i++){
			for (int j = i+1; j < items.size(); j++){
				assertFalse(items.get(i).equals(items.get(j)));
			}
		}
	}

	@Test
	public void getTotalStockTest(){
		double total = shopService.getTotalStock();

		assertEquals(9156.0, total, .1);
	}
}