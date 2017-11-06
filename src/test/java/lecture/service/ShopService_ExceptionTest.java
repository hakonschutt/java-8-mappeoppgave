package lecture.service;

import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.service.ShopService;
import lecture.util.ShopTestUtil;
import lecture.util.ShopTestUtilEmpty;
import org.junit.Before;

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


}
