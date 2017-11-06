package com.visma.lecture.appHandler;

import com.visma.lecture.common.domain.Item;
import com.visma.lecture.common.domain.support.ItemLocation;
import com.visma.lecture.common.domain.support.ItemType;
import com.visma.lecture.repository.ShopRepository;

import java.util.List;

/**
 * Created by hakonschutt on 07/11/2017.
 */
public class SearchHandler {
    private ShopRepository shopRepository;

    public SearchHandler(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Item> getSearchItem(String command){
        if (command.startsWith("loc")){
            ItemLocation loc = getItemLocation(command.substring(command.indexOf("=") + 1));
            return loc != null ? shopRepository.getItemsForLocation(loc) : null;
        } else if (command.startsWith("type")){
            ItemType typ = getItemType(command.substring(command.indexOf("=") + 1));
            return typ != null ? shopRepository.getItemsForType(typ) : null;
        } else if (command.startsWith("stock")){
            String stock = command.substring(command.indexOf("=") + 1);
            return stockSearch(stock);
        } else if (command.startsWith("name")){
            String search = command.substring(command.indexOf("=") + 1);
            return shopRepository.getItemsByName(search);
        } else if (command.startsWith("prod")){
            String search = command.substring(command.indexOf("=") + 1);
            return shopRepository.getItemsByProducer(search);
        } else {
            return null;
        }
    }

    public Item getSingleItem(String command){
        String number = command.substring(command.indexOf("=") + 1);
        try {
            int id = Integer.parseInt(number);
            return shopRepository.findItemById(id);
        } catch (NumberFormatException e){
            System.out.println("Illegal id input...");
            return null;
        }
    }

    public List<Item> stockSearch(String stock){
        try {
            int amount = Integer.parseInt(stock);
            return shopRepository.getItemsWithHigherStock(amount);
        } catch (NumberFormatException e){
            System.out.println("Illegal stock input...");
            return null;
        }
    }

    public ItemType getItemType(String type){
        String[] cases = {"electronics", "clothing", "beverage"};

        int i;
        for (i = 0; i < cases.length; i++)
            if(cases[i].equals(type)) break;

        switch(i){
            case 0:
                return ItemType.ELECTRONICS;
            case 1:
                return ItemType.CLOTHING;
            case 2:
                return ItemType.BEVERAGE;
            default:
                return null;
        }
    }

    public ItemLocation getItemLocation(String loc){
        String[] cases = {"oslo", "sarpsborg", "hamar", "lillehammer", "drammen"};

        int i;
        for (i = 0; i < cases.length; i++)
            if(cases[i].equals(loc)) break;

        switch(i){
            case 0:
                return ItemLocation.OSLO;
            case 1:
                return ItemLocation.SARPSBORG;
            case 2:
                return ItemLocation.HAMAR;
            case 3:
                return ItemLocation.LILLEHAMMER;
            case 4:
                return ItemLocation.DRAMMEN;
            default:
                return null;
        }
    }
}
