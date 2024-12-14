// SPARROW

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.sql.ResultSet;

public class Sparrow implements Data {
    public static void main(String[] args) {
        userInterface UI = new userInterface();

        try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stt = conn.createStatement();) {
            for (int i = 1; i < 21; i++) {
                String reset = "UPDATE listOfSeats" + i + " set Express=52,Luxury=41,NonAC_Sleeper=30,AC_Sleeper=30 ";
                stt.executeUpdate(reset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            UI.userStatus();
        }
    }
}

class user {

    String name;
    String mobileNumber;
    String UserId;
    private String password;
    int NoOfSeats;
    String source;
    String destination;
    int date;
    int busNO;
    String bustype;

    user(String name, String mobileNumber, String UserId, String password) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.UserId = UserId;
        this.password = password;

    }

    user(String source, String destination, int date) {
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    user(String UserId, String password) {
        this.UserId = UserId;
        this.password = password;
    }

    boolean checkpassword(String pass, String userid) {
        return this.password.equalsIgnoreCase(pass) && this.UserId.equalsIgnoreCase(userid);
    }
}

class userInterface implements Data {
    static int k = 0;
    final static String url1 = "jdbc:mysql://localhost:3306/UserData";

    public void userStatus() {

        lable: while (true) {

            int press;
            while (true) {
                try {
                    System.out.println("Press 1:- Sign Up");
                    System.out.println("Press 2:- Login");
                    Scanner sc = new Scanner(System.in);
                    press = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Enter valid number.");
                }
            }

            switch (press) {
                case 1: {
                    Scanner sce = new Scanner(System.in);
                    System.out.print("Enter name : ");
                    String name = sce.nextLine();
                    System.out.print("Enter mobile number: ");
                    String mobileNumber = sce.nextLine();
                    System.out.print("Enter UserId : ");
                    String UserId = sce.nextLine();

                    while (true) {
                        try (Connection conn = DriverManager.getConnection(url1, user, pass);
                                Statement stt = conn.createStatement();) {
                            String up = "SELECT UserId FROM dataTable where UserId=" + "'" + UserId + "'";
                            ResultSet rf = stt.executeQuery(up);
                            if (rf.next()) {
                                System.out.println("UserId exists");
                                System.out.print("Enter another UserId or Press \"B\" and enter for back to menu: ");
                                UserId = sce.nextLine();
                                if (UserId.equalsIgnoreCase("B")) {
                                    continue lable;
                                }

                            } else {
                                break;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print("Enter password:");
                    String password = sce.nextLine();
                    System.out.println("Please remember your userId and password to login again as an existing user.");
                    System.out.println();
                    System.out.println("Welcome to Sparrow travel service.");
                    System.out.println();
                    user x = new user(name, mobileNumber, UserId, password);

                    try (Connection conn = DriverManager.getConnection(url1, user, pass);
                            Statement stt = conn.createStatement();) {
                        String insert = "insert into dataTable values (" + "'" + name + "'" + "," + "'" + mobileNumber
                                + "'"
                                + "," + "'" + UserId + "'" + ","
                                + "'" + password + "'" + ")";
                        stt.executeUpdate(insert);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    AskingRequirements req = new AskingRequirements();
                    req.BookOrAlreadyBooked(x);
                    break lable;
                }

                case 2:
                    // enter password and username
                    Scanner sce = new Scanner(System.in);
                    System.out.println("Enter UserId:");
                    String UserId = sce.nextLine();
                    System.out.println("Enter password:");
                    String password = sce.nextLine();
                    try (Connection conn = DriverManager.getConnection(url1, user, pass);
                            Statement stt = conn.createStatement();) {
                        String up = "SELECT UserId FROM dataTable where UserId=" + "'" + UserId + "'" + " and PassWord="
                                + "'" + password + "'";
                        ResultSet rf = stt.executeQuery(up);
                        if (rf.next()) {
                            user x = new user(UserId, password);
                            AskingRequirements req = new AskingRequirements();
                            req.BookOrAlreadyBooked(x);
                            break lable;

                        } else {
                            System.out.println("Invalid Username or Password.Please try again.");
                            System.out.println("Press \"B\" and enter for back to menu or ");
                            System.out.print("Press \"F\" and enter if you forget the password: ");
                            String tp = sce.nextLine();
                            while (!tp.equalsIgnoreCase("B") && !tp.equalsIgnoreCase("F")) {
                                System.out.println("Enter valid character.");
                                System.out.println("Press \"B\" and enter for back to menu or ");
                                System.out.print("Press \"F\" and enter if you forget the password: ");
                                tp = sce.nextLine();
                            }
                            if (tp.equalsIgnoreCase("B")) {
                                continue lable;
                            } else if (tp.equalsIgnoreCase("F")) {
                                ReCaptcha: while (true) {
                                    Random rem = new Random();
                                    int varify = rem.nextInt(1000, 10000);
                                    System.out.println();
                                    System.out.println("Captcha Code: " + varify);
                                    System.out.print("Enter captcha Code here to varify that you are not a robot: ");
                                    Scanner s1 = new Scanner(System.in);
                                    int in = s1.nextInt();
                                    System.out.println();
                                    if (varify == in) {
                                        System.out.print("Enter new password: ");
                                        Scanner s2 = new Scanner(System.in);
                                        password = s2.nextLine();
                                        try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                                                Statement stt1 = conn1.createStatement();) {
                                            String up1 = "UPDATE dataTable set PassWord=" + "'" + password + "'"
                                                    + " where UserId=" + "'" + UserId
                                                    + "'";
                                            stt1.executeUpdate(up1);
                                            System.out.println("Your Password changed successfully.");
                                            System.out.println();
                                            user x = new user(UserId, password);
                                            AskingRequirements req = new AskingRequirements();
                                            req.BookOrAlreadyBooked(x);
                                            break ReCaptcha;
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        continue ReCaptcha;
                                    }
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                    break;
            }
        }
    }
}

class AskingRequirements implements Data {
    static int flag;
    final static String url1 = "jdbc:mysql://localhost:3306/UserData";

    AskingRequirements() {
        flag = 0;
    }

    public void BookOrAlreadyBooked(user x) {
        lable2: while (true) {
            int press;
            while (true) {
                try {
                    System.out.println("Press 1 to see your previous booking ");
                    System.out.println("Press 2 for new booking");
                    System.out.println("Press 3 for back to menu");
                    Scanner sc = new Scanner(System.in);
                    press = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Enter valid number.");
                }
            }

            switch (press) {
                case 1: {
                    new BookingDetail(x);
                    System.out.println("Press C and enter for cancel booking ");
                    System.out.println("Press B and enter for back to Menu");
                    Scanner z = new Scanner(System.in);
                    String ze = z.nextLine();
                    while (!ze.equalsIgnoreCase("C") && !ze.equalsIgnoreCase("B")) {
                        System.out.println("Enter valid character.");
                        System.out.println("Press C and enter for cancel booking ");
                        System.out.println("Press B and enter for back to Menu");
                        ze = z.nextLine();
                    }
                    if (ze.equalsIgnoreCase("B")) {
                        continue lable2;
                    } else {
                        System.out.println("Enter your Ticket number which you want to cancel");
                        Scanner kp = new Scanner(System.in);
                        String k = kp.nextLine();
                        System.out.println("Are you sure you want to cancel your booking ?");
                        System.out.println("Press Yes or No");
                        String un = kp.nextLine();

                        if (un.equalsIgnoreCase("Yes")) {
                            int idx = Integer.parseInt(k.substring(3)) - 13830000;
                            try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                                    Statement stt1 = conn1.createStatement();) {
                                String info = "SELECT * FROM BookingData where idx=" + idx;
                                ResultSet ainfo = stt1.executeQuery(info);

                                if (ainfo.next()) {
                                    x.source = ainfo.getString("Source");
                                    x.destination = ainfo.getString("Destination");
                                    x.date = ainfo.getInt("date");
                                    x.bustype = ainfo.getString("bustype");
                                    x.busNO = ainfo.getInt("busNo");
                                    x.NoOfSeats = ainfo.getInt("NoOfSeats");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            new DeleteIntoDatabase().UpdateData(idx);
                            new SeatCancel().UpdateSeats(x);
                        } else {
                            continue lable2;
                        }

                    }
                    break;
                }
                case 2:
                    while (flag == 0) {
                        Scanner sce = new Scanner(System.in);
                        System.out.print("Enter source name : ");
                        x.source = sce.nextLine();
                        System.out.print("Enter destination name : ");
                        x.destination = sce.nextLine();
                        System.out.print("Enter Date when you want a ride (dd/mm/yyyy): ");
                        String Date = sce.nextLine();
                        x.date = Integer.parseInt(Date.substring(0, 2));
                        Dataase.main(null, x);
                    }
                    break;
                case 3:
                    break lable2;
            }

        }
    }
}

interface Bus {
    double calculateFare(int distanceInKm);

    default double getCommonFare(int distanceInKm) {
        if (distanceInKm < 50) {
            return 2.5 * distanceInKm;
        } else {
            return 1.25 * 50 + 1.0 * (distanceInKm - 50);
        }
    }
}

class ExpressBus implements Bus {
    public double calculateFare(int distanceInKm) {
        double baseFare = getCommonFare(distanceInKm);
        return baseFare;
    }
}

class LuxuryBus implements Bus {
    public double calculateFare(int distanceInKm) {
        double baseFare = getCommonFare(distanceInKm);
        return baseFare + distanceInKm * 0.25;
    }
}

class ACSleeperBus implements Bus {
    public double calculateFare(int distanceInKm) {
        double baseFare = getCommonFare(distanceInKm);
        return baseFare + distanceInKm * 1.5;
    }
}

class NonACSleeperBus implements Bus {
    public double calculateFare(int distanceInKm) {
        double baseFare = getCommonFare(distanceInKm);
        return baseFare + distanceInKm * 1;
    }
}