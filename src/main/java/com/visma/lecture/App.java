package com.visma.lecture;

import com.visma.lecture.common.database.Database;
import com.visma.lecture.common.domain.Item;
import com.visma.lecture.repository.ShopRepository;
import com.visma.lecture.appHandler.Instructions;
import com.visma.lecture.appHandler.PrintHandler;
import com.visma.lecture.appHandler.SearchHandler;

import java.util.List;
import java.util.Scanner;

/**
 * Basic gui class for printing and searching the database
 * Created by hakonschutt on 07/11/2017.
 */
public class App {
    private List<Item> items;
    private ShopRepository shopRepository;
    private Scanner sc = new Scanner(System.in);
    private SearchHandler searchHandler;


    public App() {
        items = Database.itemTable;
        shopRepository = new ShopRepository(items);
        searchHandler = new SearchHandler(shopRepository);
    }

    private void start(){
        printInstructions("man");

        boolean run = true;
        while(run){
            run = runApp();
        }
    }

    public void printInstructions(String command){
        if (command.replace(" ", "").toLowerCase().equals("search")){
            Instructions.printSearchIntructions();
        } else {
            Instructions.printBasicInstructions();
        }
    }

    public boolean runApp(){
        System.out.println();
        System.out.print("What command do you want to do? ");
        String userInput = sc.nextLine().toLowerCase();

        return executeCommand(userInput);
    }

    public String getCommands(String[] commands){
        if(commands[0].equals("man")) {
            return commands.length > 1 ? commands[1] : "";
        } else if (commands[0].equals("search")){
            if(commands.length == 3){
                String command = commands[1];
                String search = commands[2];

                if(command.equals("l")){
                    return "loc=" + search;
                } else if (command.equals("t")){
                    return "type=" + search;
                } else if (command.equals("id")){
                    return "id=" + search;
                } else if (command.equals("s")){
                    return "stock=" + search;
                } else if (command.equals("n")){
                    return "name=" + search;
                } else if (command.equals("p")){
                    return "prod=" + search;
                }
            }
        }

        return "";
    }

    public String breakUpCommand(String commandString){
        String[] commands = commandString.replace("-", "").split(" ");

        if(commands[0].equals("search") && commands[1].equals("n") || commands[1].equals("p") && commands.length > 2){
            String[] temp = new String[3];
            temp[0] = commands[0];
            temp[1] = commands[1];
            temp[2] = "";
            for (int i = 2; i < commands.length; i++){
                temp[2] += commands[i] + (commands.length > (i + 1) ? " " : "");
            }
            commands = temp;
        }

        if(commands.length > 3){
            System.out.println("You are not allowed more then one parameter");
            return "";
        }

        return getCommands(commands);
    }

    public boolean executeCommand(String command){
        String[] cases = {"man", "all", "search", "quit"};

        int i;
        for (i = 0; i < cases.length; i++)
            if(command.startsWith(cases[i])) break;

        if(command.contains("-") && (i != 1 && i != 3)){
            command = breakUpCommand(command);
        }

        System.out.println();

        switch (i){
            case 0:
                printInstructions(command);
                return true;
            case 1:
                PrintHandler.printItemList(shopRepository.getItemsList());
                return true;
            case 2:
                if(command.startsWith("id")){
                    Item item = searchHandler.getSingleItem(command);
                    if(item != null) PrintHandler.printSingleItem(item);
                    else PrintHandler.printNoResult();
                } else {
                    List<Item> searchItems = searchHandler.getSearchItem(command);
                    if(searchItems != null) PrintHandler.printItemList(searchItems);
                    else PrintHandler.printNoResult();
                }
                return true;
            case 3:
                System.out.println("Quiting program.");
                return false;
            default:
                System.out.println("Illegal command. Try again!");
                return true;
        }
    }

    public static void main(String[] args) {
        new App().start();
    }
}

