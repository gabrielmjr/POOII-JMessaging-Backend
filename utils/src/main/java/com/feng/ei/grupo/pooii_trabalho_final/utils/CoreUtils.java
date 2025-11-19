package com.feng.ei.grupo.pooii_trabalho_final.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CoreUtils {
    @Contract(" -> new")
    @NotNull
    public static Date fifteenDaysFromNow() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
    }

    public static long diffInDays(@NotNull OffsetDateTime time) {
        long diff = ChronoUnit.DAYS.between(OffsetDateTime.now(), time.plusDays(15));
        if (diff == 0) return 1;
        else return diff;
    }
}
