package lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.InvalidCriteriaException;
import com.visma.lecture.common.exception.NoItemFoundForCriteriaException;
import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.service.ShopService;
import lecture.util.ShopTestUtilEmpty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by hakonschutt on 06/11/2017.
 */
public class ShopService_ExceptionTest {

    private ShopRepository shopRepository;
    private ShopService shopService_Empty;

    @Before
    public void setUp() throws Exception {
        shopRepository = new ShopRepository(new ShopTestUtilEmpty().getItems());
        shopService_Empty = new ShopService(shopRepository);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getLocationMappedWithItemsTest_Throws_NoItem(){
        Map<ItemLocation, List<Item>> locationMap = shopService_Empty.getLocationMappedWithItems();
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemTypeMappedWithItemsTest_Throws_NoItem(){
        Map<ItemType, List<Item>> itemTypeMap = shopService_Empty.getItemTypeMappedWithItems();
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getProducerMappedWithItemsTest_Throws_NoItem(){
        Map<String, List<Item>> producerMap = shopService_Empty.getProducerMappedWithItems();
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getNameMappedWithItemsTest_Throws_NoItem(){
        Map<String, List<Item>> nameMap = shopService_Empty.getNameMappedWithItems();
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemsMappedFromStock_1500_Test_Throws_NoItem(){
        Map<Boolean, List<Item>> stockCountMap = shopService_Empty.getItemsMappedFromStock_1500();
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getItemFromIDTest_Throws_Invalid(){
        Item item = shopService_Empty.getItemFromID(null);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemFromIDTest_Throws_NoItem(){
        final Integer ID = 2002;
        Item item = shopService_Empty.getItemFromID(ID);
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getAllProducersAsStringTest_Throws_Invalid(){
        String producer = shopService_Empty.getAllProducersAsString(null);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getAllProducersAsStringTest_Throws_NoItem(){
        String producer = shopService_Empty.getAllProducersAsString("x");
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getItemLocationWithTotalStockHigherThenTest_Throws_Invalid(){
        List<ItemLocation> locations = shopService_Empty.getItemLocationWithTotalStockHigherThen(-1);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemLocationWithTotalStockHigherThenTest_Throws_NoItem(){
        List<ItemLocation> locations = shopService_Empty.getItemLocationWithTotalStockHigherThen(1000);
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getItemLocationWithTotalStockLowerThenTest_Throws_Invalid(){
        List<ItemLocation> locations = shopService_Empty.getItemLocationWithTotalStockLowerThen(-1);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemLocationWithTotalStockLowerThenTest_Throws_NoItem(){
        List<ItemLocation> locations = shopService_Empty.getItemLocationWithTotalStockLowerThen(1000);
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getItemsFromLocationWithHigherStockThanTest_Throws_Invalid(){
        List<Item> items = shopService_Empty.getItemsFromLocationWithHigherStockThan(null, -1);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemsFromLocationWithHigherStockThanTest_Throws_NoItem(){
        List<Item> items = shopService_Empty.getItemsFromLocationWithHigherStockThan(ItemLocation.OSLO, 1000);
    }

    @Test (expected = InvalidCriteriaException.class)
    public void getItemsFromLocationWithLowerStockThanTest_Throws_Invalid(){
        List<Item> items = shopService_Empty.getItemsFromLocationWithLowerStockThan(null, -1);
    }

    @Test (expected = NoItemFoundForCriteriaException.class)
    public void getItemsFromLocationWithLowerStockThanTest_Throws_NoItem(){
        List<Item> items = shopService_Empty.getItemsFromLocationWithLowerStockThan(ItemLocation.OSLO, 1000);
    }
}
