package com.socialmedia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // Add the default admin user to the users list
        Admin admin = new Admin("superadmin", "admin123", "admin@socialmedia.com");
        users.add(admin);

        System.out.println("Welcome to the Social Media Platform Management System!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.println("\nEnter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = LoginManager.authenticate(username, password, users);
        if (user == null) {
            System.out.println("Invalid username or password. Please try again.");
            return;
        }

        System.out.println("Login successful! Welcome, " + user.getUsername() + "!");
        if (user instanceof Admin) {
            adminDashboard((Admin) user);
        } else if (user instanceof BrandManager) {
            brandManagerDashboard((BrandManager) user);
        } else if (user instanceof Influencer) {
            influencerDashboard((Influencer) user);
        }
    }

    private static void adminDashboard(Admin admin) {
        System.out.println("\nAdmin Dashboard");
        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. View All Users");
            System.out.println("4. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter role (Admin, BrandManager, Influencer):");
                    String role = scanner.nextLine();
                    User newUser = createUser(username, password, email, role);
                    if (newUser != null) {
                        users.add(newUser); // Ensure the object is stored permanently
                    }
                    break;
                case 2:
                    System.out.println("Enter username to remove:");
                    String usernameToRemove = scanner.nextLine();
                    admin.removeUser(usernameToRemove);
                    users.removeIf(user -> user.getUsername().equals(usernameToRemove));
                    break;
                case 3:
                    admin.viewAllUsers();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void brandManagerDashboard(BrandManager brandManager) {
        System.out.println("\nBrand Manager Dashboard");
        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Create Campaign");
            System.out.println("2. List My Campaigns");
            System.out.println("3. Offer Contract to Influencer");
            System.out.println("4. View Info");
            System.out.println("5. Filter Influencers");
            System.out.println("6. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter campaign name:");
                    String campaignName = scanner.nextLine();
                    System.out.println("Enter platform:");
                    String platform = scanner.nextLine();
                    brandManager.createCampaign(campaignName, platform);
                    break;
                case 2:
                    brandManager.listCampaigns();
                    break;
                case 3:
                    System.out.println("Enter influencer's username:");
                    String influencerUsername = scanner.nextLine();
                    System.out.println("Enter campaign name:");
                    String campaignForContract = scanner.nextLine();
                    User influencerUser = findUserByUsername(influencerUsername);
                    if (influencerUser instanceof Influencer) {
                        brandManager.offerContract((Influencer) influencerUser, campaignForContract);
                    } else {
                        System.out.println("Influencer not found.");
                    }
                    break;
                case 4:
                    brandManager.viewInfo();
                    break;
                case 5:
                    // Get all influencers from the users list
                    ArrayList<Influencer> allInfluencers = new ArrayList<>();
                    for (User user : users) {
                        if (user instanceof Influencer) {
                            allInfluencers.add((Influencer) user);
                        }
                    }
                    
                    if (allInfluencers.isEmpty()) {
                        System.out.println("No influencers available in the system.");
                    } else {
                        brandManager.findMatchingInfluencers(allInfluencers);
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void influencerDashboard(Influencer influencer) {
        System.out.println("\nInfluencer Dashboard");
        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. View Details");
            System.out.println("2. View Contracts");
            System.out.println("3. Accept a Contract");
            System.out.println("4. Reject a Contract");
            System.out.println("5. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    influencer.viewDetails(); // Display influencer details
                    break;
                case 2:
                    influencer.viewContracts(); // List all contracts
                    break;
                case 3:
                    System.out.println("Enter the contract number to accept:");
                    int acceptIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    influencer.acceptContract(acceptIndex);
                    break;
                case 4:
                    System.out.println("Enter the contract number to reject:");
                    int rejectIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    influencer.rejectContract(rejectIndex);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static User createUser(String username, String password, String email, String role) {
        String niche = getRandomNiche(); // Assign a random niche
        switch (role) {
            case "Admin":
                return new Admin(username, password, email);
            case "BrandManager":
                return new BrandManager(username, password, email, role, niche);
            case "Influencer":
                return new Influencer(username, password, email, role, niche);
            default:
                System.out.println("Invalid role. User not created.");
                return null;
        }
    }

    private static String getRandomNiche() {
        String[] niches = {"FITNESS", "FOOD", "TRAVEL", "BEAUTY", "FASHION", "TECH", "GAMING"};
        Random random = new Random();
        return niches[random.nextInt(niches.length)];
    }

    private static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}