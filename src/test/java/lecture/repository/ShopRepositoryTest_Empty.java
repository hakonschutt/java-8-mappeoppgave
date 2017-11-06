package lecture.repository;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.repository.ShopRepository;
import lecture.util.ShopTestUtilEmpty;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNull;

/**
 * Created by hakonschutt on 06/11/2017.
 */
public class ShopRepositoryTest_Empty {
    private List<Item> items;
    private ShopRepository shopRepository_Empty;

    @Before
    public void setUp() throws Exception {
        items = new ShopTestUtilEmpty().getItems();
        shopRepository_Empty = new ShopRepository(items);
    }

    @Test
    public void getItemsWithinRangeTest_Return_Null(){
        final int LOWER_BOUND = 2003;
        final int UPPER_BOUND = 2006;
        List<Item> itemsWithinRange = shopRepository_Empty.getItemsWithinRange(LOWER_BOUND, UPPER_BOUND);
        assertNull(itemsWithinRange);
    }

    @Test
    public void getItemsPerLocationTest_Return_Null(){
        List<Item> itemsInOslo = shopRepository_Empty.getItemsPerLocation(ItemLocation.OSLO);
        assertNull(itemsInOslo);
    }

    @Test
    public void getItemsPerTypeTest_Return_Null(){
        List<Item> itemsWithType = shopRepository_Empty.getItemsPerType(ItemType.ELECTRONICS);
        assertNull(itemsWithType);
    }

    @Test
    public void getItemsPerProducerTest_Return_Null(){
        List<Item> itemsProducer = shopRepository_Empty.getItemsByProducer("producer 1");
        assertNull(itemsProducer);
    }

    @Test
    public void getItemsPerNameTest_Return_Null(){
        List<Item> itemsProducer = shopRepository_Empty.getItemsByName("Test1");
        assertNull(itemsProducer);
    }

    @Test
    public void getItemsWithHigherStockTest_Return_Null(){
        List<Item> itemsWithHighStock = shopRepository_Empty.getItemsWithHigherStock(1500);
        assertNull(itemsWithHighStock);
    }

    @Test
    public void getItemsWithLowerStockTest_Return_Null(){
        List<Item> itemsWithLowerStock = shopRepository_Empty.getItemsWithLowerStock(1500);
        assertNull(itemsWithLowerStock);
    }
}
