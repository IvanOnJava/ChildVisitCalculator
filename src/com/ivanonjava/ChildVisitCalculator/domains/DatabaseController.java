package com.ivanonjava.ChildVisitCalculator.domains;

import com.ivanonjava.ChildVisitCalculator.Constants;
import com.ivanonjava.ChildVisitCalculator.pojo.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class DatabaseController {
    private static String sortAction = " birthday ";
    private static DatabaseController instance;
    private static Connection connect;

    private static Statement st;
    private static ResultSet rs;
    private static PreparedStatement ps;
    private static boolean sortAsk;

    private static Connection getConnect() {
        return connect;
    }

    private DatabaseController() {
        createConnect();
    }


    private void createConnect() {
        try {
            Class.forName(Constants.DB_Driver).getDeclaredConstructor().newInstance();
            try {
                connect = DriverManager.getConnection(Constants.DB_URL + Constants.DB_NAME);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void Instantiate() {
        instance = instance == null ?
                new DatabaseController() : instance;
    }


    public static boolean addHolidays(String dates, boolean everyYear) {

        if (everyYear) {
            for (int i = -20; i < 20; i++) {
                Date day = CalendarController.getDatePlusYear(dates, i);
                if (validateHoliday(day)) {
                    addHoliday(day);
                }
            }
            return true;
        } else {
            Date day = CalendarController.getDate(dates);
            if (validateHoliday(day)) {
                addHoliday(day);
                return true;
            }
        }

        return false;
    }

    public static boolean isWeekend(String a) {
        Date date = CalendarController.getDate(a);
        return CalendarController.isWeekend(a) || validateDayForPatient(date);
    }

    private static boolean validateHoliday(Date day) {
        try {
            st = getConnect().createStatement();
            String sql = "SELECT count(*) FROM holidays WHERE day = " + day.getTime();
            rs = st.executeQuery(sql);
            rs.next();
            return rs.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(rs, st);
        }
        return false;
    }

    private static void addHoliday(Date day) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            ps = getConnect().prepareStatement("INSERT INTO holidays(day) VALUES (?)");
            ps.setDate(1, day);
            ps.executeUpdate();
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT * FROM days_tables");
            while (rs.next()) {
                for (int i = 2; i <= 16; i++) {
                    if (rs.getDate(i).getTime() == day.getTime()) {
                        list.add(rs.getInt(1));
                    }
                }
            }
            for (Integer integer : list) {
                st = getConnect().createStatement();
                rs = st.executeQuery("SELECT patients.birthday, patients.discardday, id_address FROM patients LEFT JOIN addInfo aI on patients.id = aI.patient_id WHERE id = " + integer);
                rs.next();
                PatientForDelta patient = new PatientForDelta(integer, new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate(1).getTime()), new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate(2).getTime()), rs.getInt(3));
                ps = connect.prepareStatement("DELETE FROM days_tables WHERE patient_id = (?)");
                ps.setInt(1, patient.getId());
                ps.executeUpdate();
                addTableForPatient(patient.getId(), patient.getBirthday(), patient.getDiscardday(), patient.getId_adr());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }

    }

    public static boolean removeHolidays(String date) {
        try {
            Date day = CalendarController.getDate(date);
            ps = getConnect().prepareStatement("DELETE FROM holidays WHERE day = (?)");
            ps.setDate(1, day);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
        return false;
    }

    public static void addPatient(Patient patient) {
        try {
            closeStatement(rs, st, ps);
            ps = getConnect().prepareStatement("INSERT INTO patients(fullname, address, phone, birthday, discardday, isPresent, comment, weightB, heightB, weightD, gender, AddAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, patient.getFullName());
            ps.setString(2, patient.getAddress());
            ps.setString(3, patient.getPhone());
            ps.setDate(4, CalendarController.getDate(patient.getBirthday()));
            ps.setDate(5, CalendarController.getDate(patient.getDiscardDay()));
            ps.setBoolean(6, false);
            ps.setString(7, " ");
            ps.setString(8, patient.getWeightB());
            ps.setString(9, patient.getHeightB());
            ps.setString(10, patient.getWeightD());
            ps.setString(11, patient.getGender());
            ps.setString(12, patient.getKv());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                addInfoForPatient(id, patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addInfoForPatient(int id, Patient patient) {
        int id_adr;
        try {
            ps = getConnect().prepareStatement("SELECT number FROM addresses WHERE address = (?)");
            ps.setString(1, patient.getAddress());
            rs = ps.executeQuery();
            if (rs.next()) {
                id_adr = rs.getInt(1);
                ps = getConnect().prepareStatement("INSERT INTO addInfo(patient_id, dateNBO, dateAUDIO, " +
                        "dateBCJ, dateGEPATIT, isTUBER, roddom," +
                        " nameHelper, id_address, serialGEPATIT, " +
                        "serialBCJ, isSERTIFICATE, diagnose) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, id);
                ps.setString(2, patient.getDateNBO());
                ps.setString(3, patient.getDateAUDIO());
                ps.setString(4, patient.getDateBCJ());
                ps.setString(5, patient.getDateGEP());
                ps.setBoolean(6, patient.isTuber());
                ps.setString(7, patient.getRoddom());
                ps.setString(8, patient.getHelper());
                ps.setInt(9, id_adr);
                ps.setString(10, patient.getSerialGEP());
                ps.setString(11, patient.getSerialBCJ());
                ps.setBoolean(12, false);
                ps.setString(13, patient.getDiagnose());
                ps.executeUpdate();
                addTableForPatient(id, patient.getBirthday(), patient.getDiscardDay(), id_adr);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static void addTableForPatient(int id, String birthday, String discardDay, int id_adr) {
        try {
            closeStatement(rs, ps, st);
            Date[] days = CalendarController.getVisitDays(birthday, discardDay, id_adr);

            st = getConnect().createStatement();
            st.executeUpdate("INSERT INTO days_tables(" +
                    "PATIENT_ID, ONE_DAY, THREE_DAY, TWO_WEEKS, " +
                    "THREE_WEEKS, ONE_MONTH, TWO_MONTHS, THREE_MONTHS, " +
                    "FOUR_MONTHS, FIVE_MONTHS, SIX_MONTHS, SEVEN_MONTHS," +
                    " EIGHT_MONTHS, NINE_MONTHS, TEN_MONTHS, ELEVEN_MONTHS) VALUES (" +
                    id + ", " + days[0].getTime() + ", " + days[1].getTime() + ", " + days[2].getTime() +
                    ", " + days[3].getTime() + ", " + days[4].getTime() + ", " + days[5].getTime() + ", " + days[6].getTime() +
                    ", " + days[7].getTime() + ", " + days[8].getTime() + ", " + days[9].getTime() + ", " + days[10].getTime() + ", " + days[11].getTime() + ", "
                    + days[12].getTime() + ", " + days[13].getTime() + ", " + days[14].getTime() + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static boolean validateDayForPatient(Date date) {

        return !validateHoliday(date);
    }


    static boolean isDayCrowded(Date date, int id) {
        try {
            ps = getConnect().prepareStatement("SELECT count(days_tables.patient_id) FROM days_tables" +
                    " LEFT JOIN patients p on days_tables.patient_id = p.id" +
                    " LEFT JOIN addInfo aI on p.id = aI.patient_id" +
                    " WHERE aI.id_address = (?) AND (" +
                    "one_day = (?) OR three_day = (?) OR two_weeks = (?) OR " +
                    "three_weeks = (?) OR one_month = (?) OR two_months = (?) OR " +
                    "three_months = (?) OR four_months = (?) OR five_months = (?) OR " +
                    "six_months = (?) OR seven_months = (?) OR eight_months = (?) OR " +
                    "nine_months = (?) OR ten_months = (?) OR eleven_months = (?)) AND p.isPresent = false"
            );
            ps.setInt(1, id);
            for (int i = 0; i < 15; i++)
                ps.setDate(i + 2, CalendarController.getDate(date.toString()));
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) >= Constants.MAX_PATIENTS;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deletePatient(int patient_id) {
        try {
            ps = getConnect().prepareStatement("DELETE FROM patients  WHERE id  = (?)");
            ps.setInt(1, patient_id);
            ps.executeUpdate();
            ps = getConnect().prepareStatement("DELETE FROM days_tables WHERE patient_id  = (?)");
            ps.setInt(1, patient_id);
            ps.executeUpdate();
            ps = getConnect().prepareStatement("DELETE FROM addInfo WHERE patient_id  = (?)");
            ps.setInt(1, patient_id);
            ps.executeUpdate();
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT count(*) FROM patients");
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    ps = getConnect().prepareStatement("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'patients'");
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @SafeVarargs
    private static <T extends AutoCloseable> void closeStatement(T... state) {
        for (T t : state) {
            try {
                if (t != null) {
                    t.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updatePatientNameOnId(int id, String newNameValue) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET fullname = (?) WHERE patients.id = (?)");
            ps.setString(1, newNameValue);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    public static void updatePatientAddressOnId(int id, String address) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET address = (?) WHERE patients.id = (?)");
            ps.setString(1, address);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    public static void updatePatientPhoneOnId(int id, String phone) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET phone = (?) WHERE patients.id = (?)");
            ps.setString(1, phone);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    public static void updatePatientIsPresentOnId(int id, boolean isPresent) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET isPresent = (?) WHERE patients.id = (?)");
            ps.setBoolean(1, isPresent);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    public static void updatePatientCommentOnId(int id, String comment) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET comment = (?) WHERE patients.id = (?)");
            ps.setString(1, comment);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    public static ArrayList<PatientForPatronage> getPatientsForPatronage(int number) {

        ArrayList<PatientForPatronage> listPatient = new ArrayList<>();
        String type = isSortAsk() ? "DESC" : "ASC";
        try {
            ps = getConnect().prepareStatement("SELECT patients.*, days_tables.* FROM patients LEFT JOIN days_tables on patients.id = patient_id LEFT JOIN addresses a on patients.address = a.address WHERE number = (?) ORDER BY (?) " + type);
            ps.setInt(1, number);
            ps.setString(2, getSortAction());
            rs = ps.executeQuery();
            selectPatientForPatronage(listPatient);
        } catch (SQLException e) {
            e.printStackTrace();
            //addTableForPatients();
        } finally {
            closeStatement(rs, ps);
        }
        return listPatient;
    }

    private static void selectPatientForMagazine(ArrayList<PatientForMagazine> list) {
        try {
            while (rs.next()) {
                try {
                    list.add(new PatientForMagazine(
                            rs.getInt("id"),
                            rs.getString("fullname"),
                            rs.getDate("birthday").toString(),
                            rs.getDate("discardday").toString(),
                            rs.getString("address"),
                            rs.getString("AddAddress"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getBoolean("isPresent"),
                            rs.getString("comment"),
                            rs.getDate("one_day").toString(),
                            rs.getDate("three_day").toString(),
                            rs.getDate("two_weeks").toString(),
                            rs.getDate("three_weeks").toString(),
                            rs.getBoolean("isSERTIFICATE"),
                            rs.getString("weightB"),
                            rs.getString("heightB"),
                            rs.getString("weightD"),
                            rs.getString("diagnose"),
                            rs.getString("dateNBO"),
                            rs.getString("dateAUDIO"),
                            rs.getBoolean("isTUBER"),
                            rs.getString("dateBCJ"),
                            rs.getString("serialBCJ"),
                            rs.getString("dateGEPATIT"),
                            rs.getString("serialGEPATIT"),
                            rs.getString("roddom"),
                            rs.getString("nameHelper"),
                            rs.getInt("number")
                    ));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static void selectPatientForPatronage(ArrayList<PatientForPatronage> list) {

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                list.add(new PatientForPatronage(
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getDate("birthday").toString(),
                        rs.getDate("discardday").toString(),
                        rs.getString("address"),
                        rs.getString("AddAddress"),
                        rs.getString("phone"),
                        rs.getBoolean("isPresent"),
                        rs.getString("comment"),
                        rs.getDate("one_day").toString(),
                        rs.getDate("three_day").toString(),
                        rs.getDate("two_weeks").toString(),
                        rs.getDate("three_weeks").toString(),
                        rs.getDate("one_month").toString(),
                        rs.getDate("two_months").toString(),
                        rs.getDate("three_months").toString(),
                        rs.getDate("four_months").toString(),
                        rs.getDate("five_months").toString(),
                        rs.getDate("six_months").toString(),
                        rs.getDate("seven_months").toString(),
                        rs.getDate("eight_months").toString(),
                        rs.getDate("nine_months").toString(),
                        rs.getDate("ten_months").toString(),
                        rs.getDate("eleven_months").toString()
                ));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static boolean validateUser(int i, String text, String text1) {
        try {
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT * FROM domain");
            if (rs.next()) {
                if (rs.getInt(1) == i && rs.getString(2).equals(text) && rs.getInt(3) == text1.hashCode()) {
                    return true;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    static ArrayList<PatientForDiary> getDiaryForDocument(String date, int number) {
        ArrayList<PatientForDiary> list = new ArrayList<>();
        String[] reasons = {
                "one_day", "three_day", "two_weeks", "three_weeks", "one_month",
                "two_months", "three_months", "four_months", "five_months", "six_months",
                "seven_months", "eight_months", "nine_months", "ten_months", "eleven_months"};
        HashMap<Integer, PatientForDiary> patientsMap = new HashMap<>();
        for (String r : reasons) {
            try {
                ps = getConnect().prepareStatement("SELECT patients.id, patients.fullname, patients.phone, patients.address, patients.AddAddress, patients.birthday, days_tables." + r
                        + " FROM patients LEFT JOIN days_tables on patients.id = patient_id " +
                        "LEFT JOIN addresses a on patients.address = a.address" +
                        " WHERE " + r + " = (?) AND patients.isPresent = false AND number = " + number + " ORDER BY birthday");
                ps.setDate(1, CalendarController.getDate(date));
                rs = ps.executeQuery();
                while (rs.next()) {
                    PatientForDiary patient = new PatientForDiary(
                            rs.getInt("id"),
                            rs.getString("fullname"),
                            r,
                            rs.getString("address") + " кв. " + rs.getString("AddAddress"),
                            rs.getString("phone"),
                            rs.getDate("birthday").toString()
                    );

                    patientsMap.put(patient.getHash(), patient);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (!patientsMap.isEmpty()) {
            for (Map.Entry<Integer, PatientForDiary> patient : patientsMap.entrySet()) {
                list.add(patient.getValue());
            }

        }

        return list;
    }

    public static ArrayList<String> getAddresses() {
        ArrayList<String> adrs = new ArrayList<>();
        try {
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT address FROM addresses ORDER BY address");
            while (rs.next()) {
                adrs.add(rs.getString(1));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return adrs;
    }

    public static ArrayList<PatientForMagazine> getPatientsForMagazine() {

        ArrayList<PatientForMagazine> list = new ArrayList<>();
        try {
            st = getConnect().createStatement();
            rs = st.executeQuery(
                    "SELECT id, fullname, birthday, discardday, patients.address, AddAddress,phone, " +
                            "isPresent, comment, one_day, three_day, two_weeks, three_weeks, " +
                            "weightB, heightB, weightD, diagnose, gender, dateNBO, dateAUDIO, " +
                            "isSERTIFICATE, isTUBER, dateBCJ, serialBCJ, dateGEPATIT, serialGEPATIT," +
                            "roddom, nameHelper, number " +
                            "FROM patients LEFT JOIN days_tables dt on patients.id = dt.patient_id " +
                            "LEFT JOIN addInfo aI on patients.id = aI.patient_id " +
                            "LEFT JOIN addresses a on patients.address = a.address ORDER BY " + getSortAction());
            selectPatientForMagazine(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Integer> getAllNumber() {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT DISTINCT number FROM addresses ORDER BY number");
            while (rs.next()) {
                numbers.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    public static ArrayList<PatientForMagazine> getPatientsForMagazine(String s, boolean selected, String date, String date1) {
        String sql = getSqlString(s, selected, date, date1);
        ArrayList<PatientForMagazine> listPatient = new ArrayList<>();
        try {
            String type = isSortAsk() ? "DESC" : "ASC";
            ps = getConnect().prepareStatement("SELECT id, fullname, birthday, discardday, patients.address, AddAddress, phone, " +
                    "isPresent, comment, one_day, three_day, two_weeks, three_weeks, " +
                    "weightB, heightB, weightD, diagnose, gender, dateNBO, dateAUDIO, isSERTIFICATE, isTUBER," +
                    " dateBCJ, serialBCJ, " +
                    "dateGEPATIT, serialGEPATIT," +
                    "roddom, nameHelper, number " +
                    "FROM patients LEFT JOIN days_tables dt on patients.id = dt.patient_id " +
                    "LEFT JOIN addInfo aI on patients.id = aI.patient_id " +
                    "LEFT JOIN addresses a on patients.address = a.address " + sql + " ORDER BY " + getSortAction() + " " + type);
            setAction(s, date, date1);
            rs = ps.executeQuery();
            selectPatientForMagazine(listPatient);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return listPatient;
    }

    private static void setAction(String s, String date, String date1) {
        try {
            if (s.trim().length() < 2) {
                int i = 1;
                for (; i <= 15; i++) {
                    ps.setDate(i, CalendarController.getDate(date));
                }
                if (date1.length() > 6) {
                    for (; i <= 30; i++)
                        ps.setDate(i, CalendarController.getDate(date1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String getSqlString(String s, boolean selected, String date, String date1) {
        String act = selected ? " >= " : " = ";
        String sql = "";
        if (s.trim().length() < 2) {
            sql += " WHERE (one_day " + act + " (?) OR three_day " + act + " (?) OR two_weeks " + act + " (?) OR " +
                    "three_weeks " + act + " (?) OR one_month " + act + " (?) OR two_months " + act + " (?) OR " +
                    "three_months " + act + " (?) OR four_months " + act + " (?) OR five_months " + act + " (?) OR " +
                    "six_months " + act + " (?) OR seven_months " + act + " (?) OR eight_months " + act + " (?) OR " +
                    "nine_months " + act + " (?) OR ten_months " + act + " (?) OR eleven_months " + act + " (?)) ";
            if (date1.trim().length() > 5) {
                sql += " AND " +
                        "(one_day <= (?) OR three_day <= (?) OR two_weeks <= (?) OR " +
                        "three_weeks <= (?) OR one_month <= (?) OR two_months <= (?) OR " +
                        "three_months <= (?) OR four_months <= (?) OR five_months <= (?) OR " +
                        "six_months <= (?) OR seven_months <= (?) OR eight_months <= (?) OR " +
                        "nine_months <= (?) OR ten_months <= (?) OR eleven_months <= (?)) ";
            }
        } else {
            sql += " WHERE " + s + act + CalendarController.getDate(date).getTime();
            if (date1.trim().length() > 5) {
                sql += " AND " + s + " <= " + CalendarController.getDate(date1).getTime();
            }
        }
        return sql;
    }

    public static ArrayList<PatientForPatronage> getPatientsForPatronage(String s, boolean selected, String date, String date1, int number) {
        String sql = getSqlString(s, selected, date, date1);
        sql += " AND number = " + number;
        String type = isSortAsk() ? "DESC" : "ASC";
        ArrayList<PatientForPatronage> listPatient = new ArrayList<>();
        try {
            ps = getConnect().prepareStatement("SELECT patients.*, days_tables.* FROM patients LEFT JOIN days_tables on patients.id = patient_id left join addresses a on patients.address = a.address " + sql + " ORDER BY patients." + getSortAction() + " " + type);
            setAction(s, date, date1);
            rs = ps.executeQuery();
            selectPatientForPatronage(listPatient);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(rs, ps);
        }
        return listPatient;
    }

    public static boolean updateAddress(String oldAddress, String newAddress) {
        try {
            ps = getConnect().prepareStatement("SELECT count(*) FROM addresses WHERE address = (?)");
            ps.setString(1, newAddress);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    ps = getConnect().prepareStatement("UPDATE addresses SET address = (?) WHERE address = (?)");
                    ps.setString(1, newAddress);
                    ps.setString(2, oldAddress);
                    ps.executeUpdate();
                    ps = getConnect().prepareStatement("UPDATE patients SET address = (?) WHERE address = (?)");
                    ps.setString(1, newAddress);
                    ps.setString(2, oldAddress);
                    ps.executeUpdate();
                    return true;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateAddressNumber(String address, String number) {
        try {
            ps = getConnect().prepareStatement("UPDATE addresses SET number = (?) WHERE address = (?)");
            ps.setInt(1, Integer.parseInt(number.trim()));
            ps.setString(2, address);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (NumberFormatException e) {

            return false;
        }
        return false;
    }

    public static ArrayList<Address> getAllAddress() {
        ArrayList<Address> list = new ArrayList<>();
        try {
            st = getConnect().createStatement();
            rs = st.executeQuery("SELECT address, number FROM addresses ORDER BY address");
            while (rs.next()) {
                list.add(new Address(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }

    public static boolean updatePatientWeightB(int id, String weightB) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET weightB = (?) WHERE id = (?)");
            ps.setString(1, weightB);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientHeightB(int id, String heightB) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET heightB = (?) WHERE id = (?)");
            ps.setString(1, heightB);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientWeightD(int id, String weightD) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET weightD = (?) WHERE id = (?)");
            ps.setString(1, weightD);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientDiagnoseOnId(int id, String diagnose) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET diagnose = (?) WHERE patient_id = (?)");
            ps.setString(1, diagnose);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientDateNBO(int id, String dateNBO) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET dateNBO = (?) WHERE patient_id = (?)");
            ps.setString(1, dateNBO);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientDateAUDIO(int id, String dateAUDIO) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET dateAUDIO = (?) WHERE patient_id = (?)");
            ps.setString(1, dateAUDIO);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientDateBCJ(int id, String dateBCJ) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET dateBCJ = (?) WHERE patient_id = (?)");
            ps.setString(1, dateBCJ);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientSerialBCJ(int id, String serialBCJ) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET serialBCJ = (?) WHERE patient_id = (?)");
            ps.setString(1, serialBCJ);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientDateGEP(int id, String dateGEP) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET dateGEPATIT = (?) WHERE patient_id = (?)");
            ps.setString(1, dateGEP);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientSerialGEP(int id, String serialGEP) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET serialGEPATIT = (?) WHERE patient_id = (?)");
            ps.setString(1, serialGEP);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientRoddom(int id, String roddom) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET roddom = (?) WHERE patient_id = (?)");
            ps.setString(1, roddom);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientHelper(int id, String helper) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET nameHelper = (?) WHERE patient_id = (?)");
            ps.setString(1, helper);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientOneDay(int id, String oneDay) {
        try {
            ps = getConnect().prepareStatement("UPDATE days_tables SET one_day = (?) WHERE patient_id = (?)");
            ps.setDate(1, CalendarController.getDate(oneDay));
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean addAddress(String address, String number) {
        try {
            ps = getConnect().prepareStatement("INSERT INTO addresses(number, address) VALUES (?, ?)");
            ps.setInt(1, Integer.parseInt(number.trim()));
            ps.setString(2, address);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void deleteAddress(String address) {
        try {
            ps = getConnect().prepareStatement("DELETE FROM addresses WHERE address = (?)");
            ps.setString(1, address);
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static boolean updatePatientSertificateOnId(int id, boolean sertificate) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET isSERTIFICATE = (?) WHERE patient_id = (?)");
            ps.setBoolean(1, sertificate);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientTuberOnId(int id, boolean tuber) {
        try {
            ps = getConnect().prepareStatement("UPDATE addInfo SET isTUBER = (?) WHERE patient_id = (?)");
            ps.setBoolean(1, tuber);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static String getTextForTutu(String begin, String end) {
        String result = "Родилось - ";
        try {
            ArrayList<String> list = new ArrayList<>();
            ps = getConnect().prepareStatement("SELECT serialBCJ FROM addInfo aI LEFT JOIN patients  on  aI.patient_id  = patients.id WHERE birthday >= (?) AND birthday <= (?)");
            ps.setDate(1, CalendarController.getDate(begin));
            ps.setDate(2, CalendarController.getDate(end));
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1).trim());
            }
            result += list.size() + "\nпривито БЦЖ -";
            int max = list.size();
            int med = 0;
            int otk = 0;
            for (String s : list) {
                if (s.contains("ед") || s.trim().equalsIgnoreCase("") || s.contains("тк")) {
                    max--;
                    if (s.contains("ед")) {
                        med++;
                    }
                    if (s.trim().equalsIgnoreCase("") || s.contains("тк")) {
                        otk++;
                    }
                }
            }
            result += max + "\nМедотвод - " + med + "\nОтказ - " + otk;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean updatePatientGenderOnId(int id, String gender) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET gender = (?) WHERE id = (?)");
            ps.setString(1, gender);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientK(int id, String k) {
        try {
            ps = getConnect().prepareStatement("UPDATE patients SET AddAddress = (?) WHERE id = (?)");
            ps.setString(1, k);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientThreeDay(int id, String threeDay) {
        try {
            ps = getConnect().prepareStatement("UPDATE days_tables SET three_day = (?) WHERE patient_id = (?)");
            ps.setDate(1, CalendarController.getDate(threeDay));
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientTwoWeeks(int id, String twoWeeks) {
        try {
            ps = getConnect().prepareStatement("UPDATE days_tables SET two_weeks = (?) WHERE patient_id = (?)");
            ps.setDate(1, CalendarController.getDate(twoWeeks));
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePatientThreeWeeks(int id, String threeWeeks) {
        try {
            ps = getConnect().prepareStatement("UPDATE days_tables SET three_weeks = (?) WHERE patient_id = (?)");
            ps.setDate(1, CalendarController.getDate(threeWeeks));
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }


    public static String getSortAction() {
        return sortAction;
    }

    public static boolean isSortAsk() {
        return sortAsk;
    }

    public static void setSortAction(String sortAction, boolean isAskType) {

        DatabaseController.sortAction = sortAction;
        DatabaseController.sortAsk = isAskType;
    }
}
