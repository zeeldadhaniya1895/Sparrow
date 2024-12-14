//seats

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

interface Data {

    String url = "jdbc:mysql://localhost:3306/seats";
    String user = "root";
    String pass = "ZeEl@51271895@";
}

public abstract class seats {
    public abstract void UpdateSeats(user x);

}

class SeatBooking extends seats implements Data {
    public void UpdateSeats(user x) {
        String type = null;
        switch (x.bustype) {
            case "E": {
                type = "Express";

                break;
            }
            case "L": {
                type = "Luxury";

                break;
            }
            case "N": {
                type = "NonAC_Sleeper";

                break;
            }
            case "A": {
                type = "AC_Sleeper";

                break;
            }
            case "e": {
                type = "Express";

                break;
            }
            case "l": {
                type = "Luxury";

                break;
            }
            case "n": {
                type = "NonAC_Sleeper";

                break;
            }
            case "a": {
                type = "AC_Sleeper";

                break;
            }

        }
        try (Connection conn1 = DriverManager.getConnection(url, user, pass);
                Statement stt1 = conn1.createStatement();) {
            String update = "UPDATE listOfSeats" + Integer.toString(x.busNO) + " set " + type + "=" + type + "-"
                    + x.NoOfSeats + " where date=" + x.date;
            stt1.executeUpdate(update);
            AskingRequirements.flag = 1;
            new InsertIntoDatabase().UpdateData(x);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

class SeatCancel extends seats implements Data {
    public void UpdateSeats(user x) {
        String type = null;
        switch (x.bustype) {
            case "E": {
                type = "Express";

                break;
            }
            case "L": {
                type = "Luxury";

                break;
            }
            case "N": {
                type = "NonAC_Sleeper";

                break;
            }
            case "A": {
                type = "AC_Sleeper";

                break;
            }
            case "e": {
                type = "Express";

                break;
            }
            case "l": {
                type = "Luxury";

                break;
            }
            case "n": {
                type = "NonAC_Sleeper";

                break;
            }
            case "a": {
                type = "AC_Sleeper";

                break;
            }

        }

        try (Connection conn1 = DriverManager.getConnection(url, user, pass);
                Statement stt1 = conn1.createStatement();) {

            String update = "UPDATE listOfSeats" + Integer.toString(x.busNO) + " set " + type + "=" + type + "+"
                    + x.NoOfSeats + " where date=" + x.date;
            stt1.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

abstract class UpdateDetails implements Data {
    final static String url1 = "jdbc:mysql://localhost:3306/UserData";

    public abstract void UpdateData(user x);
}

class InsertIntoDatabase extends UpdateDetails {
    public void UpdateData(user x) {
        try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                Statement stt1 = conn1.createStatement();) {
            String insertt = "insert into BookingData values (" + (new TicketSPRNo().idx + 1) + "," + "'" + x.UserId
                    + "'"
                    + "," + "'" + x.source
                    + "'" + "," + "'" + x.destination + "'" + ","
                    + "'" + x.bustype + "'" + "," + x.NoOfSeats + "," + x.date + "," + x.busNO + ")";
            stt1.executeUpdate(insertt);

            System.out.println();
            System.out.println("Your booking is confirmed successfully.");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

class DeleteIntoDatabase implements Data {
    final static String url1 = "jdbc:mysql://localhost:3306/UserData";

    public void UpdateData(int idx) {
        try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                Statement stt1 = conn1.createStatement();) {

            String delete = "delete from BookingData where idx=" + idx;
            stt1.executeUpdate(delete);
            System.out.println();
            System.out.println("Your Booking is cancelled successfully.");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

class TicketSPRNo implements Data {
    int idx;

    TicketSPRNo() {
        final String url1 = "jdbc:mysql://localhost:3306/UserData";

        try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                Statement stt1 = conn1.createStatement();) {
            String lidx = "SELECT idx FROM BookingData ORDER BY idx DESC LIMIT 1";
            ResultSet lastidx = stt1.executeQuery(lidx);
            if (lastidx.next()) {
                idx = lastidx.getInt("idx");
            } else {
                idx = 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

class BookingDetail implements Data {
    BookingDetail(user x) {
        final String url1 = "jdbc:mysql://localhost:3306/UserData";
        System.out.println();
        try (Connection conn1 = DriverManager.getConnection(url1, user, pass);
                Statement stt1 = conn1.createStatement();) {
            String info = "SELECT * FROM BookingData where UserId=" + "'" + x.UserId + "'" + "ORDER BY idx ASC ";
            ResultSet ainfo = stt1.executeQuery(info);
            int j = 0;
            while (ainfo.next()) {
                System.out.println("TicketNo: SPR" + (13830000 + ainfo.getInt("idx")) + "   Source: "
                        + ainfo.getString("Source") + "   Destination: " + ainfo.getString("Destination")
                        + "   BusType:"
                        + ainfo.getString("bustype") + "   NoOfSeats:" + ainfo.getInt("NoOfSeats") + "   Date:"
                        + ainfo.getInt("date"));
                j = 1;
            }
            if (j == 0) {
                System.out.println("There is no Booking.");
            }
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}