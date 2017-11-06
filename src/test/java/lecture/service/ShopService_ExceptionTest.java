package lecture.service;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.common.exception.NoItemFoundForCriteriaException;
import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.service.ShopService;
import lecture.util.ShopTestUtil;
import lecture.util.ShopTestUtilEmpty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
}
