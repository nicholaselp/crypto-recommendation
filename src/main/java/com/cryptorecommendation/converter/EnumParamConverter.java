package com.cryptorecommendation.converter;

import com.cryptorecommendation.utility.EnumStringValue;
import com.cryptorecommendation.utility.EnumUtility;
import org.springframework.core.convert.converter.Converter;

public class EnumParamConverter<T extends Enum<T> & EnumStringValue> implements Converter<String, T> {
    private final Class<T> clizz;
    public EnumParamConverter(Class<T> clizz){ this.clizz = clizz; }
    @Override
    public T convert(String source) {
        return EnumUtility.getByString(clizz, source);
    }
}
