package lecture.util;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;

import java.util.ArrayList;
import java.util.List;

public class ShopTestUtil {
	
	private final List<Item> items;
	
	public ShopTestUtil(){
		items = new ArrayList<>();
		items.add(new Item(2001, "Producer_1 Test1", ItemLocation.OSLO, ItemType.BEVERAGE, 1501));
		items.add(new Item(2002, "Producer_2 Test2", ItemLocation.OSLO, ItemType.ELECTRONICS, 200));
		items.add(new Item(2006, "Producer_6 Test6", ItemLocation.HAMAR, ItemType.BEVERAGE, 5));
		items.add(new Item(2003, "Producer_3 Test3", ItemLocation.OSLO, ItemType.ELECTRONICS, 3400));
		items.add(new Item(2004, "Producer_4 Test4", ItemLocation.HAMAR, ItemType.BEVERAGE, 50));
		items.add(new Item(2005, "Producer_5 Test5", ItemLocation.HAMAR, ItemType.ELECTRONICS, 4000));
	}
	
	public List<Item> getItems() {
		return items;
	}
}
