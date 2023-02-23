package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {

    public static void draw(){
        System.out.println("""
                ______ _                       ______             _   \s
                | ___ | |                      | ___ \\           | |  \s
                | |_/ | |__   ___  _ __   ___  | |_/ / ___   ___ | | __
                |  __/| '_ \\ / _ \\| '_ \\ / _ \\ | ___ \\/ _ \\ / _ \\| |/ /
                | |   | | | | (_) | | | |  __/ | |_/ | (_) | (_) |   <\s
                \\_|   |_| |_|\\___/|_| |_|\\___| \\____/ \\___/ \\___/|_|\\_\\
                """);
    }
    public static void showMainMenu() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        String number = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
        System.out.println("Contact added.");
        Contact aNewContact = Contact.add(new Contact(capitalizeWords(name), number));
        contacts.add(aNewContact);
        printContacts(contacts);
    }




    public static void searchContact(ArrayList<Contact> contacts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name: ");
        String name = scanner.nextLine();
        for (Contact contact : contacts) {
            if (contact.contactName.contains(capitalizeWords(name))) {
                System.out.printf("""
                    
                    ---------------------------------
                    | SEARCH RESULTS                |
                    ---------------------------------
                    """);
                System.out.printf("| %-15s| %-13s|\n", "Name", "Number");
                System.out.printf("---------------------------------\n");
                System.out.printf("| %-15s| %-13s|\n", contact.contactName, contact.contactNumber);
                System.out.printf("---------------------------------\n");
            }
        }
    }

    public static void deleteContact(ArrayList<Contact> contacts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name to delete: ");
        String name = scanner.nextLine();

        for (int i = contacts.size() -1; i >= 0; i--) {
            if (contacts.get(i).contactName.contains(capitalizeWords(name))) {
                System.out.println("deleting: " + contacts.get(i).contactName);
                contacts.remove(contacts.get(i));
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
        System.out.printf("---------------------------------\n");
        System.out.printf("| %-15s| %-13s|\n", "Name", "Number");
        System.out.printf("---------------------------------\n");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.printf("| %-15s| %-13s|\n", contacts.get(i).contactName, contacts.get(i).contactNumber);
        }
        System.out.printf("---------------------------------\n");
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
                    System.out.println(" ");
                    break;
                case "2":
                    System.out.println(" ");
                    addContact(contacts);
                    saveContacts(contacts);
                    System.out.println(" ");
                    break;
                case "3":
                    System.out.println(" ");
                    searchContact(contacts);
                    System.out.println(" ");
                    break;
                case "4":
                    System.out.println(" ");
                    deleteContact(contacts);
                    saveContacts(contacts);
                    System.out.println(" ");
                    break;
                case "5":
                    System.out.println(" ");
                    System.out.println("Goodbye...");
                    System.out.println(" ");
                    break;
                default:
                    System.out.println(" ");
                    System.out.println("Invalid choice, please try again.");
                    System.out.println(" ");
            }
        } while (!choice.equals("5"));
        saveContacts(contacts);
    }
}