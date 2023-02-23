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

    public static void addContact(ArrayList<Contact> contacts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Contact added.");
        Contact aNewContact =  Contact.add(new Contact(name, phoneNumber));
        contacts.add(aNewContact);
    }


    public static void getCategoryName(int userMenueChoice) {
        switch (userMenueChoice) {
            case 1:
                System.out.println(loadContacts());
                break;
            case 2:
//                addContact();
                break;
            default:
                System.out.println("That wasn't a proper input");
                break;
        }
    }












    public static void main(String[] args) {
        ArrayList<Contact> contacts = loadContacts();
        System.out.println(contacts);
        addContact(contacts);
        System.out.println(contacts);

        int userMenueChoice = showMainMenu();
        getCategoryName(userMenueChoice);




    }
}