package com.cryptorecommendation.utility;

import java.util.Arrays;
import java.util.Optional;

import static com.cryptorecommendation.utility.StringUtility.emptyIfBlank;

public class EnumUtility {

    /**
     * Tries to find the enum value by its string value. Returns empty if not found.
     */
    public static<T extends Enum<T> & EnumStringValue> Optional<T> findByString(Class<T> enumType, String value){
        return Arrays.stream(enumType.getEnumConstants())
                .filter(enumConst -> enumConst.getValue().equalsIgnoreCase(emptyIfBlank(value).trim()))
                .findFirst();
    }

    /**
     * Returns enum value by its string value. Throws IllegalArgumentException if not found
     */
    public static <T extends Enum<T> & EnumStringValue> T getByString(Class<T> enumType, String value){
        return findByString(enumType, value)
                .orElseThrow(() -> new IllegalArgumentException("Umknown value: " + value));
    }
}