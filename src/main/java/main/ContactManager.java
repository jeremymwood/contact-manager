package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {

    public static int showMainMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4 or 5): ");
        int menueChoice = input.nextInt();
        return menueChoice;
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

    public static ArrayList<Contact> overrideContacts() {
        ArrayList<Contact> contacts2 = new ArrayList<>();
        return contacts2;
    }

    public static void addContact(ArrayList<Contact> contacts) {
        System.out.println(contacts);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Contact added.");
        Contact aNewContact =  Contact.add(new Contact(name, phoneNumber));
        contacts.add(aNewContact);
//        scanner.close();
        System.out.println(contacts);
        overrideContacts();
    }

    public static void deleteContact(ArrayList<Contact> contacts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name to delete: ");
        String name = scanner.nextLine();
//        scanner.close();
        System.out.println(contacts);
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).contactName.contains(name)) {
                System.out.println("deleting: " + contacts.get(i).contactName);
                contacts.remove(contacts.get(i));
            }
        }
        System.out.println(contacts);
        overrideContacts();
    }
    public static void printContacts(ArrayList<Contact> contacts) {
        System.out.println(contacts);
        System.out.printf("---------------------------------\n");
        System.out.printf("| %-15s| %-13s|\n", "Name", "Number");
        System.out.printf("---------------------------------\n");
        for (int i = 0; i < contacts.size(); i++) {
        System.out.printf("| %-15s| %-13s|\n", contacts.get(i).contactName, contacts.get(i).contactNumber);
        }
        System.out.printf("---------------------------------\n");
    }

    public static void searchContact(ArrayList<Contact> contacts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the contact name: ");
        String name = scanner.nextLine();
        scanner.close();
        for (Contact contact : contacts) {
            if (contact.contactName.contains(name)) {
                System.out.println(contact.contactName + " | " + contact.contactNumber);
            }
        }
    }



    public static void getCategoryName(int userMenueChoice, ArrayList<Contact> contacts) {
        switch (userMenueChoice) {
            case 1:
                System.out.println(loadContacts());
                break;
            case 2:
                addContact(contacts);
                break;
            case 3:
                searchContact(contacts);
                break;
            case 4:
                deleteContact(contacts);
                break;
            case 5:
                System.out.println("Goodbye...");
                break;
            default:
                System.out.println("That wasn't a proper input");
                break;
        }
    }

    public static void main(String[] args) {
        ArrayList<Contact> contacts = loadContacts();
////        System.out.println(contacts);
//
//        int userMenueChoice = showMainMenu();
//        getCategoryName(userMenueChoice, contacts);

//        System.out.println(contacts);
        printContacts(contacts);


    }
}