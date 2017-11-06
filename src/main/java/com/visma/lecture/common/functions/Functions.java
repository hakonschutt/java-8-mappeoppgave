package com.visma.lecture.common.functions;

import com.visma.lecture.common.domain.Item;

import java.util.function.Function;

/**
 * Created by hakonschutt on 06/11/2017.
 */
public class Functions {
    public static Function<Item, String> getProducer = (Item item) -> {
        return item.getItemName().substring(0, item.getItemName().indexOf(" "));
    };

    public static Function<Item, String> getSpacedProducer = (Item item) -> {
        return item.getItemName().substring(0, item.getItemName().indexOf(" ")).replace("_", " ");
    };

    public static Function<Item, String> getName = (Item item) -> {
        return item.getItemName().substring(item.getItemName().indexOf(" ") + 1);
    };

    public static Function<Item, String> getSpacedName = (Item item) -> {
        return item.getItemName().substring(item.getItemName().indexOf(" ") + 1).replace("_", " ");
    };
}
