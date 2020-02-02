package com.ivanonjava.ChildVisitCalculator.domains;

import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import com.ivanonjava.ChildVisitCalculator.pojo.Week;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarController {
    private static Calendar calendar = Calendar.getInstance();;


    public static Date getNow() {
        LocalDate localDate = LocalDate.now();
        String now = DateTimeFormatter.ofPattern(Constants.getInstance().DATE_PATTERN).format(localDate);
        return getDate(now);
    }

    static Date getDate(String day) {
        calendar.clear();
        int[] data = replaceDay(day);
        calendar.set(data[0], data[1], data[2]);
        return new Date(calendar.getTime().getTime());
    }


    private static int[] replaceDay(String day) {
        int[] data = new int[3];
        if (day.contains("-")) {
            String[] sDay = day.split("-");
            data[0] = Integer.parseInt(sDay[0]);
            data[1] = Integer.parseInt(sDay[1]) - 1;
            data[2] = Integer.parseInt(sDay[2]);
            sDay = null;
            return data;
        } else if (day.contains("/")) {
            String[] sDay = day.split("/");
            data[0] = Integer.parseInt(sDay[0]);
            data[1] = Integer.parseInt(sDay[1]) - 1;
            data[2] = Integer.parseInt(sDay[2]);
            return data;
        } else {
            String[] sDay = day.split("\\.");
            data[0] = Integer.parseInt(sDay[2]);
            data[1] = Integer.parseInt(sDay[1]) - 1;
            data[2] = Integer.parseInt(sDay[0]);
            return data;
        }
    }

    public static Date[] getVisitDays(String birthday, String discardDay, int id_table) {
        int[] birth = replaceDay(birthday);
        int[] disk = replaceDay(discardDay);
        java.sql.Date[] dates = new java.sql.Date[15];
        dates[0] = getDateForTable(disk, 1, Calendar.DAY_OF_YEAR, id_table);
        dates[1] = getDateForTable(disk, 3, Calendar.DAY_OF_YEAR, id_table);
        dates[2] = getDateForTable(birth, 13, Calendar.DAY_OF_YEAR, id_table);
        dates[3] = getDateForTable(birth, 20, Calendar.DAY_OF_YEAR, id_table);
        for (int i = 4; i < dates.length; i++) {
            dates[i] = getDateForTable(birth, i - 3, Calendar.MONTH, id_table);
        }
        return dates;
    }

    private static Date getDateForTable(int[] data, int count, int field, int id_table) {
        calendar.clear();
        calendar.set(data[0], data[1], data[2]);
        calendar.add(field, count);
        if (field == Calendar.MONTH) {
            calendar.add(Calendar.DAY_OF_YEAR, 15);
        }
        while (true) {
            if (DatabaseController.isDayCrowded(new Date(calendar.getTime().getTime()), id_table)) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            if (DatabaseController.validateDayForPatient(new Date(calendar.getTime().getTime()))) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                Calendar cal2 = Calendar.getInstance();
                cal2.clear();
                cal2.setTime(calendar.getTime());
                cal2.add(Calendar.DAY_OF_YEAR, -1);
                if (calendar.after(cal2))
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                else
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                continue;
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            return new Date(calendar.getTime().getTime());
        }
    }


    static ArrayList<java.util.Date> getDaysWithoutWeekend(String b_date, String e_date) {
        Calendar calendar = Calendar.getInstance();

        java.util.Date[] dates = getDays(b_date, e_date);
        ArrayList<java.util.Date> result = new ArrayList<>();
        for (java.util.Date date : dates) {
            calendar.clear();
            calendar.setTime(date);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                continue;
            }
            if (DatabaseController.validateDayForPatient(new Date(calendar.getTime().getTime()))) {
                continue;
            }
            result.add(date);
        }
        return result;
    }

    static Date[] getDays(String b_date, String e_date) {
        Date date = getDate(b_date);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.clear();
        cal2.clear();
        cal1.setTime(date);
        cal2.setTime(getDate(e_date));
        Date[] data = new Date[Math.abs(daysBetween(cal1.getTime(), cal2.getTime())) + 1];
        for (int i = 0; i < data.length; i++) {
            data[i] = getDayPlusDay(date, i);
        }
        return data;
    }

    private static int daysBetween(java.util.Date d2, java.util.Date d1) {
        int between = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

        return between;
    }

    private static Date getDayPlusDay(Date date, int i) {
        calendar.clear();
        ;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, i);
        return new Date(calendar.getTime().getTime());
    }

    public static ArrayList<Week> getDaysForMonth(int month, int year) {
        ArrayList<Week> list = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();

        cal1.clear();
        cal1.set(year, month, 1);
        while (cal1.get(Calendar.MONTH) == month) {
            Week week = new Week();
            Calendar cal2 = Calendar.getInstance();
            cal2.clear();
            cal2.setTime(cal1.getTime());
            while (cal2.get(Calendar.WEEK_OF_MONTH) == cal1.get(Calendar.WEEK_OF_MONTH)) {
                int i1 = cal1.get(Calendar.DAY_OF_WEEK);
                if (i1 == Calendar.MONDAY) {
                    week.setMonday(cal1.get(Calendar.DAY_OF_MONTH));
                } else if (i1 == Calendar.TUESDAY) {
                    week.setTuesday(cal1.get(Calendar.DAY_OF_MONTH));
                } else if (i1 == Calendar.WEDNESDAY) {
                    week.setWednesday(cal1.get(Calendar.DAY_OF_MONTH));
                } else if (i1 == Calendar.THURSDAY) {
                    week.setThursday(cal1.get(Calendar.DAY_OF_MONTH));
                } else if (i1 == Calendar.FRIDAY) {
                    week.setFriday(cal1.get(Calendar.DAY_OF_MONTH));
                }
                if (i1 == Calendar.SATURDAY) {
                    week.setSaturday(cal1.get(Calendar.DAY_OF_MONTH));
                } else if (i1 == Calendar.SUNDAY) {
                    week.setSunday(cal1.get(Calendar.DAY_OF_MONTH));
                }
                cal1.add(Calendar.DAY_OF_YEAR, 1);
            }
            list.add(week);
        }
        return list;
    }

    public static boolean isWeekend(String day) {
        calendar.clear();
        int[] data = replaceDay(day);
        calendar.set(data[0], data[1], data[2]);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
}
