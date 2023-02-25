package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void draw(){
        System.out.println(ANSI_GREEN + """
                
                █▀▀ █▀█ █▄░█ ▀█▀ ▄▀█ █▀▀ ▀█▀ █▀   █▀▄▀█ ▄▀█ █▄░█ ▄▀█ █▀▀ █▀▀ █▀█
                █▄▄ █▄█ █░▀█ ░█░ █▀█ █▄▄ ░█░ ▄█   █░▀░█ █▀█ █░▀█ █▀█ █▄█ ██▄ █▀▄
                """);
    }
    public static void showMainMenu() {
        System.out.println("1. View contacts.");
        System.out.println(ANSI_BLUE + "2. Add a new contact.");
        System.out.println(ANSI_YELLOW + "3. Search contact by name.");
        System.out.println(ANSI_RED + "4. Delete an existing contact." + ANSI_GREEN);
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4 or 5): ");
    }

    public static ArrayList<Contact> loadContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            Path path =  Paths.get("Contacts.txt");
            File file  = path.toFile();
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    contacts.add(new Contact(parts[0], parts[1]));
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public static void addContact(ArrayList<Contact> contacts) {
        System.out.println(ANSI_BLUE + """
                ▄▀█ █▀▄ █▀▄   █▀▀ █▀█ █▄░█ ▀█▀ ▄▀█ █▀▀ ▀█▀
                █▀█ █▄▀ █▄▀   █▄▄ █▄█ █░▀█ ░█░ █▀█ █▄▄ ░█░
                """);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        String number = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        System.out.println("Contact added.\n" + ANSI_GREEN);
        Contact aNewContact = Contact.add(new Contact(capitalizeWords(name), number));
        contacts.add(aNewContact);
        printContacts(contacts);
    }




    public static void searchContact(ArrayList<Contact> contacts) {
        System.out.println(ANSI_YELLOW + """
                █▀ █▀▀ ▄▀█ █▀█ █▀▀ █░█   █▀▀ █▀█ █▄░█ ▀█▀ ▄▀█ █▀▀ ▀█▀
                ▄█ ██▄ █▀█ █▀▄ █▄▄ █▀█   █▄▄ █▄█ █░▀█ ░█░ █▀█ █▄▄ ░█░
                """);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name: ");
        String name = scanner.nextLine();
        for (Contact contact : contacts) {
            if (contact.contactName.contains(capitalizeWords(name))) {
                System.out.printf(ANSI_YELLOW + """
                    
                    ---------------------------------
                    | SEARCH RESULTS                |
                    ---------------------------------
                    """);
                System.out.printf("| %-15s| %-13s|\n", "Name", "Number");
                System.out.printf("---------------------------------\n");
                System.out.printf("| %-15s| %-13s|\n", contact.contactName, contact.contactNumber);
                System.out.printf("---------------------------------\n" + ANSI_GREEN);

            }
        }
    }

    public static void deleteContact(ArrayList<Contact> contacts) {
        System.out.println(ANSI_RED + """
                █▀▄ █▀▀ █░░ █▀▀ ▀█▀ █▀▀   █▀▀ █▀█ █▄░█ ▀█▀ ▄▀█ █▀▀ ▀█▀
                █▄▀ ██▄ █▄▄ ██▄ ░█░ ██▄   █▄▄ █▄█ █░▀█ ░█░ █▀█ █▄▄ ░█░
                """);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name to delete: ");
        String name = scanner.nextLine();

        for (int i = contacts.size() -1; i >= 0; i--) {
            if (contacts.get(i).contactName.contains(capitalizeWords(name))) {
                System.out.println("Would you like to delete " + contacts.get(i).contactName + "(yes/no) :");
                String deleteChoice = scanner.nextLine();
                if (deleteChoice.equalsIgnoreCase("yes")){
                    System.out.println("Deleting: " + contacts.get(i).contactName);
                    contacts.remove(contacts.get(i));
                }
            }
        }
        printContacts(contacts);
    }

    public static void saveContacts(ArrayList<Contact> contacts) {
        try {
            FileWriter writer = new FileWriter("Contacts.txt");
            for (Contact contact : contacts) {
                writer.write(contact.contactName + "," + contact.contactNumber + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printContacts(ArrayList<Contact> contacts) {
        System.out.printf(ANSI_GREEN + "-----------------------------------\n");
        System.out.printf("| %-17s| %-13s|\n", "Name", "Number");
        System.out.printf("-----------------------------------\n");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("| %-17s| %-13s|\n", contacts.get(i).contactName, contacts.get(i).contactNumber);
        }
        System.out.printf("-----------------------------------\n");
    }

    public static String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    capitalized.append(word.substring(1));
                }
            }
            capitalized.append(" ");
        }
        return capitalized.toString().trim();
    }


    public static void main(String[] args) {
        ArrayList<Contact> contacts = loadContacts();
        Scanner scanner = new Scanner(System.in);
        String choice;
        draw();
        do {
            showMainMenu();
            choice = scanner.nextLine();
            System.out.println(choice);
            switch (choice) {
                case "1":
                    System.out.println(" ");
                    printContacts(contacts);
//                    System.out.println(" ");
                    break;
                case "2":
                    System.out.println(" ");
                    addContact(contacts);
                    saveContacts(contacts);
//                    System.out.println(" ");
                    break;
                case "3":
                    System.out.println(" ");
                    searchContact(contacts);
//                    System.out.println(" ");
                    break;
                case "4":
                    System.out.println(" ");
                    deleteContact(contacts);
                    saveContacts(contacts);
//                    System.out.println(" ");
                    break;
                case "5":
                    System.out.println(" ");
                    System.out.println(ANSI_PURPLE + """
                            █▀▀ █▀█ █▀█ █▀▄ █▄▄ █▄█ █▀▀
                            █▄█ █▄█ █▄█ █▄▀ █▄█ ░█░ ██▄
                            """ + ANSI_GREEN);
//                    System.out.println(" ");
                    break;
                default:
                    System.out.println(" ");
                    System.out.println("Invalid choice, please try again.");
//                    System.out.println(" ");
            }
        } while (!choice.equals("5"));
        saveContacts(contacts);
    }
}