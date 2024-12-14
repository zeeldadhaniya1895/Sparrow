/// DataBase

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dataase {
    static final String url = "jdbc:mysql://localhost:3306/route";
    static final String user = "root";
    static final String pass = "ZeEl@51271895@";
    static int busNO;
    static String busType = null;

    public static void main(String[] args, user x) {
        System.out.println();
        ArrayList<Integer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stt = conn.createStatement();) {
            Bus expressBus = new ExpressBus();
            Bus luxuryBus = new LuxuryBus();
            Bus acSleeperBus = new ACSleeperBus();
            Bus nonAcSleeperBus = new NonACSleeperBus();
            int a = 0, b = 0, distance = 0;
            for (int i = 1; i < 21; i++) {
                String lfirst = "SELECT name FROM route" + Integer.toString(i) + " ORDER BY distance ASC LIMIT 1";
                String llast = "SELECT name FROM route" + Integer.toString(i) + " ORDER BY distance DESC LIMIT 1";
                ResultSet rf = stt.executeQuery(lfirst);
                rf.next();
                String rf1 = rf.getString("name");
                ResultSet rl = stt.executeQuery(llast);
                rl.next();
                String rl1 = rl.getString("name");
                String sql = "select * from route" + Integer.toString(i) + " where name=" + "'" + x.source + "'" + " or"
                        + " name='" + x.destination + "'";
                ResultSet rs = stt.executeQuery(sql);
                if (rs.next()) {
                    a = rs.getInt("distance");
                    String name = rs.getString("name");
                    if (rs.next()) {
                        b = rs.getInt("distance");
                        distance = Math.abs(b - a);
                        list.add(i);
                        AllTNoOfSeats ATS = new AllTNoOfSeats(i, x.date);
                        if (name.equalsIgnoreCase(x.source)) {
                            System.out.println("Press " + Integer.toString(list.size() - 1) + " to book yourself in "
                                    + "Bus " + rf1 + "-" + rl1 + " is available in " + x.source + " to "
                                    + x.destination + " route.");

                            System.out.println("Express Bus Fare: " + expressBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.E);

                            System.out.println("Luxury Bus Fare: " + luxuryBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.L);

                            System.out.println("AC Sleeper Bus Fare: " + acSleeperBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.A);

                            System.out.println("Non-AC Sleeper Bus Fare: " + nonAcSleeperBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.N);

                        } else {
                            System.out.println("Press " + Integer.toString(list.size() - 1) + " to book yourself in "
                                    + "Bus " + rl1 + "-" + rf1 + " is available in " + x.source + " to "
                                    + x.destination + " route.");
                                    System.out.println("Express Bus Fare: " + expressBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.E);

                            System.out.println("Luxury Bus Fare: " + luxuryBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.L);

                            System.out.println("AC Sleeper Bus Fare: " + acSleeperBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.A);

                            System.out.println("Non-AC Sleeper Bus Fare: " + nonAcSleeperBus.calculateFare(distance)
                                    + "  Available seats: " + ATS.N);
                        }
                        System.out.println();
                    }
                }
            }
            Scanner sc = new Scanner(System.in);
            int t = sc.nextInt();
            x.busNO = list.get(t);
            System.out.println("Press L for Luxury bus");
            System.out.println("Press E for Express bus");
            System.out.println("Press A for AC Sleeper bus");
            System.out.println("Press N for Non Ac Sleeper bus");
            System.out.println("Press B for bake to menu");
            System.out.println();
            Scanner sce = new Scanner(System.in);
            x.bustype = sce.nextLine();
            while (!x.bustype.equalsIgnoreCase("L") && !x.bustype.equalsIgnoreCase("E")
                    && !x.bustype.equalsIgnoreCase("A") && !x.bustype.equalsIgnoreCase("N")
                    && !x.bustype.equalsIgnoreCase("B")) {
                System.out.println("Press L for Luxury bus");
                System.out.println("Press E for Express bus");
                System.out.println("Press A for AC Sleeper bus");
                System.out.println("Press N for Non Ac Sleeper bus");
                System.out.println("Press B for bake to menu");
                System.out.println();
                x.bustype = sce.nextLine();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!x.bustype.equalsIgnoreCase("B")) {
            Scanner sr = new Scanner(System.in);
            System.out.print("Enter the number of seats that you want to book: ");
            x.NoOfSeats = sr.nextInt();

            if(x.bustype.equalsIgnoreCase("L") && x.NoOfSeats<= new AllTNoOfSeats(x.busNO, x.date).L){
                new SeatBooking().UpdateSeats(x);
            }else  if(x.bustype.equalsIgnoreCase("E") && x.NoOfSeats<= new AllTNoOfSeats(x.busNO, x.date).E){
                new SeatBooking().UpdateSeats(x);
            }else  if(x.bustype.equalsIgnoreCase("A") && x.NoOfSeats<= new AllTNoOfSeats(x.busNO, x.date).A){
                new SeatBooking().UpdateSeats(x);
            }else if(x.bustype.equalsIgnoreCase("N") && x.NoOfSeats<= new AllTNoOfSeats(x.busNO, x.date).N){
                new SeatBooking().UpdateSeats(x);
            }
            else{
                System.out.println("No seats available.");
                System.out.println();
            }
            
        }

    }
}

class AllTNoOfSeats implements Data {
    int L, E, A, N;

    AllTNoOfSeats(int i, int date) {
        try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stt = conn.createStatement();) {
            String av = "select * from listOfSeats" + Integer.toString(i) + " where date=" + date;
            ResultSet ats = stt.executeQuery(av);
            ats.next();
            L = ats.getInt("Luxury");
            E = ats.getInt("Express");
            A = ats.getInt("AC_Sleeper");
            N = ats.getInt("NonAC_Sleeper");
          
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}