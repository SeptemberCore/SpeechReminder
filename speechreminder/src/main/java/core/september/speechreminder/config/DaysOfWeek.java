package core.september.speechreminder.config;

import java.util.ArrayList;

/**
 * Created by christian on 13/03/14.
 */
public enum DaysOfWeek {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(4),
    WEDNESDAY(8),
    THURSDAY(16),
    FRIDAY(32),
    SATURDAY(64);

    private int repeat;
     DaysOfWeek(int i) {
         this.repeat = i;
    }

    public static ArrayList<DaysOfWeek> getRepeating(int input) {
        ArrayList<DaysOfWeek> recurringDays = new ArrayList();
        int level = level(input);
        switch (level) {
            case 64:
                recurringDays.add(DaysOfWeek.SATURDAY);
                recurringDays.addAll(getRepeating(input-level));
            break;
            case 32:
                recurringDays.add(DaysOfWeek.FRIDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;
            case 16:
                recurringDays.add(DaysOfWeek.THURSDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;
            case 8:
                recurringDays.add(DaysOfWeek.WEDNESDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;
            case 4:
                recurringDays.add(DaysOfWeek.TUESDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;
            case 2:
                recurringDays.add(DaysOfWeek.MONDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;
            case 1:
                recurringDays.add(DaysOfWeek.SUNDAY);
                recurringDays.addAll(getRepeating(input-level));
                break;

        }

        return recurringDays;
    }

    private static int level(int input) {
        return input >= 64 ? 64 :
                input >= 32 ? 32 :
                        input >= 16 ? 16 :
                                input >= 8 ? 8 :
                                        input >= 4 ? 4 :
                                                input >= 2 ? 2 :
                                                        input >= 1 ? 1 : 0;
    }
}
