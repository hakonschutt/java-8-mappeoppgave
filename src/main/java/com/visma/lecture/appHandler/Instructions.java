package com.visma.lecture.appHandler;

/**
 * Created by hakonschutt on 07/11/2017.
 */
public class Instructions {
    public static void printBasicInstructions(){
        System.out.println(getLine(60));
        System.out.println(String.format("%-10S %-25S", "command", "Description"));
        System.out.println(getLine(60));
        System.out.println(String.format("%-10s %-25s", "man", "Prints the command page"));
        System.out.println(String.format("%-10s %-25s", "all", "Prints all the items in the database."));
        System.out.println(String.format("%-10s %-25s", "search", "Search for item. Type man -search for more info"));
        System.out.println(String.format("%-10s %-25s", "quit", "Quits the program"));
        System.out.println(getLine(60));
    }

    public static void printSearchIntructions(){
        System.out.println();
        System.out.println(String.format("%-50S", "Search options"));
        System.out.println(getLine(100));
        System.out.println(String.format("%-10S %-20S %-40S", "extension", "example", "Description"));
        System.out.println(getLine(100));
        System.out.println(String.format("%-10s %-20s %-40s", "-id", "search -id 1", "Will print the single item with that id"));
        System.out.println(String.format("%-10s %-20s %-40s", "-l", "search -l oslo", "Will print a list of items from a given location"));
        System.out.println(String.format("%-10s %-20s %-40s", "-t", "search -t clothing", "Will print a list of items with the given type"));
        System.out.println(String.format("%-10s %-20s %-40s", "-s", "search -s 1500", "Will print a list of items with stock higher then the set parameter"));
        System.out.println(String.format("%-10s %-20s %-40s", "-n", "search -p iphone", "Will print a list of items with the given name"));
        System.out.println(String.format("%-10s %-20s %-40s", "-p", "search -s apple", "Will print a list of items with the given producer"));
        System.out.println(getLine(100));
    }

    private static String getLine(int size){
        String line = "";
        for (int i = 0; i < size; i+=5){
            line += "-----";
        }

        return String.format("%-"+ size + "s", line);
    }
}
