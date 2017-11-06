package lecture.repository;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.functions.Functions;
import com.visma.lecture.repository.ShopRepository;
import lecture.util.ShopTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Håkon Wang Schütt
 */
public class ShopRepositoryTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	private List<Item> items;
	private ShopRepository shopRepository;

	@Before
	public void setUp() throws Exception {
		items = new ShopTestUtil().getItems();
		shopRepository = new ShopRepository(items);
	}

	@Test
	public void findItemById() throws Exception {
		assertThat(shopRepository.findItemById(2001), is(items.get(0)));
	}

	@Test
	public void getItemsListTest(){
		assertThat(shopRepository.getItemsList(), is(items));
	}
	
	@Test
	public void shouldReturnNullNotFoundById() throws Exception {
		assertThat(shopRepository.findItemById(1), nullValue());
	}

	@Test
	public void getItemsWithinRangeTest(){
		final int LOWER_BOUND = 2003;
		final int UPPER_BOUND = 2006;
		List<Item> itemsWithinRange = shopRepository.getItemsWithinRange(LOWER_BOUND, UPPER_BOUND);

		assertNotNull(itemsWithinRange);

		itemsWithinRange.stream().forEach(e -> {
			assertTrue(e.getItemID() >= LOWER_BOUND);
			assertTrue(e.getItemID() <= UPPER_BOUND);
		});
	}

	@Test
	public void getItemsPerLocationTest(){
		List<Item> itemsInOslo = shopRepository.getItemsForLocation(ItemLocation.OSLO);
		List<Item> itemsInHamar = shopRepository.getItemsForLocation(ItemLocation.HAMAR);

		assertEquals(3, itemsInOslo.size());
		assertEquals(3, itemsInHamar.size());

		itemsInOslo.stream().forEach(e -> assertEquals(ItemLocation.OSLO, e.getItemLocation()));
		itemsInHamar.stream().forEach(e -> assertEquals(ItemLocation.HAMAR, e.getItemLocation()));
	}

	@Test
	public void getItemsPerTypeTest(){
		List<Item> itemsBeverage = shopRepository.getItemsForType(ItemType.BEVERAGE);
		List<Item> itemsElectronics = shopRepository.getItemsForType(ItemType.ELECTRONICS);

		assertEquals(3, itemsBeverage.size());
		assertEquals(3, itemsElectronics.size());

		itemsBeverage.stream().forEach(e -> assertEquals(ItemType.BEVERAGE, e.getItemType()));
		itemsElectronics.stream().forEach(e -> assertEquals(ItemType.ELECTRONICS, e.getItemType()));
	}

	@Test
	public void getItemsPerProducerTest(){
		for (int i = 1; i < 6; i++){
			String searchString = "Producer " + i;
			String actualProd = "Producer_" + i;
			List<Item> itemsProducer = shopRepository.getItemsByProducer(searchString);

			assertEquals(1, itemsProducer.size());

			itemsProducer.stream().forEach(e -> assertEquals(actualProd, Functions.getProducer.apply(e)));
		}
	}

	@Test
	public void getItemsPerNameTest(){
		for (int i = 1; i < 6; i++){
			String searchString = "Test" + i;
			List<Item> itemsProducer = shopRepository.getItemsByName(searchString);

			assertEquals(1, itemsProducer.size());

			itemsProducer.stream().forEach(e -> assertEquals(searchString.toLowerCase(), Functions.getSpacedName.apply(e).toLowerCase()));
		}
	}

	@Test
	public void getItemsWithHigherStockTest(){
		final int STOCK = 1500;
		List<Item> itemList = shopRepository.getItemsWithHigherStock(STOCK);

		itemList.stream().forEach(e -> assertTrue(e.getStock() >= STOCK));
	}

	@Test
	public void getItemsWithLowerStockTest(){
		final int STOCK = 1500;
		List<Item> itemList = shopRepository.getItemsWithLowerStock(STOCK);

		itemList.stream().forEach(e -> assertTrue(e.getStock() <= STOCK));
	}
	
	@Test
	public void add() throws Exception {
		Integer size = items.size();
		Boolean add = shopRepository.create(new Item(2007, "Producer8 Test8", ItemLocation.SARPSBORG, ItemType.CLOTHING, 1));
		
		assertThat(add, is(Boolean.TRUE));
		assertThat(items.size(), is(size + 1)); //replace with .getAll() when implemented
		assertThat(shopRepository.findItemById(2007).getItemName(), is("Producer8 Test8"));
	}

	@Test
	public void update() throws Exception {
		Boolean update = shopRepository.update(new Item(2001, "Producer10 Test1", ItemLocation.OSLO, ItemType.BEVERAGE, 10));
		
		assertThat(update, is(Boolean.TRUE));
		assertThat(shopRepository.findItemById(2001).getStock(), is(10));
		assertThat(shopRepository.findItemById(2001).getItemName(), is("Producer10 Test1"));
	}

	@Test
	public void remove() throws Exception {
		Boolean remove = shopRepository.delete(2001);
		
		assertThat(remove, is(Boolean.TRUE));
		assertThat(items.size(), is(5)); //replace with .getAll() when implemented
		assertThat(shopRepository.findItemById(2001), nullValue());
	}
}