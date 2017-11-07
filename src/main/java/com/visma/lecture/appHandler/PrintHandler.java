package com.visma.lecture.appHandler;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.functions.Functions;

import java.util.List;

/**
 * Created by hakonschutt on 07/11/2017.
 */
public class PrintHandler {
    public static void printItemList(List<Item> items){
        System.out.printf("%-20S %-20S %-15S %-15S %-15S", "Name", "Producer", "Location", "Type", "Stock");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        items.stream().forEach(e -> {
            System.out.printf("%-20s %-20s %-15s %-15s %-15s",
                    Functions.getSpacedName.apply(e),
                    Functions.getSpacedProducer.apply(e),
                    e.getItemLocation(),
                    e.getItemType(),
                    e.getStock());
            System.out.println();
        });
    }

    public static void printSingleItem(Item item){
        System.out.printf("%-20S %-20S %-15S %-15S %-15S", "Name", "Producer", "Location", "Type", "Stock");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-20s %-20s %-15s %-15s %-15s",
                Functions.getSpacedName.apply(item),
                Functions.getSpacedProducer.apply(item),
                item.getItemLocation(),
                item.getItemType(),
                item.getStock()
        );
        System.out.println();
    }

    public static void printNoResult(){
        System.out.println("--------------------------------------------");
        System.out.println("No result was found for that given search...");
        System.out.println("--------------------------------------------");
    }
}
