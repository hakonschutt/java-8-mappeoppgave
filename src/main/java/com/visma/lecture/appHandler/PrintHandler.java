package com.visma.lecture.appHandler;

import com.visma.lecture.common.domain.Item;
import java.util.List;

/**
 * Created by hakonschutt on 07/11/2017.
 */
public class PrintHandler {
    public static void printItemList(List<Item> items){
        System.out.printf("%-15S %-15S %-15S %-15S %-15S", "Name", "Producer", "Location", "Type", "Stock");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");
        items.stream().forEach(e -> {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s",
                    e.getItemName().substring(e.getItemName().indexOf(" ") + 1),
                    e.getItemName().substring(0, e.getItemName().indexOf(" ")),
                    e.getItemLocation(),
                    e.getItemType(),
                    e.getStock());
            System.out.println();
        });
    }

    public static void printSingleItem(Item item){
        System.out.printf("%-15S %-15S %-15S %-15S %-15S", "Name", "Producer", "Location", "Type", "Stock");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s",
                item.getItemName().substring(item.getItemName().indexOf(" ") + 1),
                item.getItemName().substring(0, item.getItemName().indexOf(" ")),
                item.getItemLocation(),
                item.getItemType(),
                item.getStock()
        );
    }

    public static void printNoResult(){
        System.out.println("--------------------------------------------");
        System.out.println("No result was found for that given search...");
        System.out.println("--------------------------------------------");
    }
}
