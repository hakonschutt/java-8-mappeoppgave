package lecture.util;

import com.visma.lecture.common.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakonschutt on 06/11/2017.
 */
public class ShopTestUtilEmpty {

    private final List<Item> items;

    public ShopTestUtilEmpty(){
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }
}
