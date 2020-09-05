package com.thanhle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SimpleSearchEngine {

    
//  public static void inputText(List<String> text, Scanner scanner) {
//      System.out.println("Enter the number of people:");
//      int numOfText = Integer.parseInt(scanner.nextLine());
//      for (int i = 0; i < numOfText; i++) {
//          text.add(scanner.nextLine());
//      }
//  }
  
//  public static void funcQuery(List<String> text, String textQuery) {
//      List<String> resultQuery = new ArrayList<>();
//      for (String subText : text) {
//          if (subText.toLowerCase().contains(textQuery.toLowerCase())) {
//              resultQuery.add(subText);
//          }
//      }
//      if (resultQuery.size() == 0) {
//      System.out.println("No matching people found.");
//      } else {
//          resultQuery.forEach((result) -> System.out.println(result));
//      }
//      resultQuery.clear();
//  }
  
  public static void displayMenu() {
      System.out.println("=== Menu ===");
      System.out.println("1. Find a person");
      System.out.println("2. Print all people");   
      System.out.println("0. Exit");                     
  }
  
  public static void displayAllPeople(List<String> text) {
      text.forEach(people -> System.out.println(people));
  }

  public static void readFileAsList (List<String> text, String filePath) {
      File file = new File(filePath);
      try (Scanner scanner = new Scanner(file)) {
          while (scanner.hasNext()) {
              text.add(scanner.nextLine());
          }
      } catch (FileNotFoundException e) {
              System.out.println("No file found: " + filePath);
      }
  }

  public static void writeListToFile (List<String> list, String filePath) {
      File file = new File(filePath);
      try (PrintWriter printWriter = new PrintWriter(file)) {
          list.forEach(text -> printWriter.println(text));
      } catch (FileNotFoundException e) {
          System.out.printf("An exception occurs %s", e.getMessage());
      }
  }

  public static Set<String> splitStringToSet(List<String> text) {
      Set<String> setWords = new HashSet<>();
      text.forEach(string -> {
          String[] words = string.split("\\s+");
          for (String word : words) {
              setWords.add(word.toLowerCase());
          }
      });
      return setWords;
  }

  public static Map<String, List<Integer>> invertedIndex (List<String> text, Set<String> words) {
      Map<String, List<Integer>> invertedIndex = new HashMap<>();
      words.forEach(word -> {
          List<Integer> value = new ArrayList<>();
          for (int i = 0; i < text.size(); i++) {
              if (text.get(i).toLowerCase().contains(word)) {
                  value.add(i);
              }
          }
          invertedIndex.put(word, value);
      });

      return invertedIndex;
  }

//  public static List<Integer> getValueFromMap (Map<String, List<Integer>> inverted, String keyQuery) {
//      List<Integer> result = inverted.getOrDefault(keyQuery, new ArrayList<>());
//      return result;
//  }

  public static void displayQueryResult (List<String> list, List<Integer> resultQuery) {
      if (resultQuery.size() == 0) {
          System.out.println("No matching people found.");
      } else {
          resultQuery.forEach(index -> System.out.println(list.get(index)));
      }
  }

  public static StrategySearch selectSearch(String type) {
      switch (type) {
          case "ALL":
              return new StrategySearchAllImpl();
          case "ANY":
              return new StrategySearchAnyImpl();
          case "NONE":
              return new StrategySearchNoneImpl();
          default:
              return null;
      }
  }

  public static List<Integer> processingQuery(Map<String, List<Integer>> inverted, String keySearch, String type) {
      StrategySearch strategy = selectSearch(type);
      SelectionSearchType typeSearch = new SelectionSearchType(strategy);
      if (strategy == null) {
          System.out.println("Type is not correct.");
          return new ArrayList<>();
      } else {
          return typeSearch.search(inverted, keySearch);
      }

  }
  public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      List<String> text = new ArrayList<>();
      boolean flag = true;
      String filePath = "";
      if ("--data".equals(args[0])) {
          filePath = args[1];
      }
      readFileAsList(text, filePath);
//      writeListToFile(text, "myData.txt"); -> this line to save data for check
      Set<String> keyWords = splitStringToSet(text);
      Map<String, List<Integer>> invertedIndex = invertedIndex(text, keyWords);
      while (flag) {
          displayMenu();
          int key = Integer.parseInt(scanner.nextLine());
          switch (key) {
              case 1: 
                  System.out.println("Select a matching strategy: ALL, ANY, NONE");
                  String typeQuery = scanner.nextLine();
                  System.out.println("Enter a name or email to search all suitable people.");
                  String textQuery = scanner.nextLine();
                  List<Integer> resultQuery = processingQuery(invertedIndex, textQuery, typeQuery);
                  displayQueryResult(text, resultQuery);
                  break;
              case 2:
                  displayAllPeople(text);
                  break;
              case 0:
                  flag = false;
                  scanner.close();
                  break;
              default:
                  displayMenu();
          }
      }

  }
}
