package com.mycompany.practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            menu();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    createAccount(sc);
                    break;
                case 3:
                    view();
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
            }

        } while (choice != 0);

    }

    private static void menu() {
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void login(Scanner sc) {

        System.out.print("Enter username: ");
        String username = sc.next();

        System.out.print("Enter password: ");
        String password = sc.next();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = Database.connection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Welcome, " + username + "!");
                } else {
                    System.out.println("Wrong credentials.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void createAccount(Scanner sc) {

        System.out.print("Enter username: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        User user = new User(username, password);

        String sql = "INSERT INTO users(username, password) VALUE(?,?)";

        try (Connection connection = Database.connection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void view() {
        String sql = "SELECT * FROM users;";
        try (Connection connection = Database.connection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
