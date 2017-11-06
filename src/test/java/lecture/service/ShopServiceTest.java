package lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.InvalidCriteriaException;
import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.service.ShopService;
import lecture.util.ShopTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShopServiceTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
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

		String expectedResult = "Producer_1xProducer_2xProducer_6xProducer_3xProducer_4xProducer_5";

		assertNotNull(producer);
		assertEquals(expectedResult, producer);
	}
}