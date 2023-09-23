package com.oierbravo.createsifter.foundation.util;

import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.foundation.utility.LangBuilder;

public class ModLang extends com.simibubi.create.foundation.utility.Lang {
    public ModLang() {
        super();
    }
    public static LangBuilder builder() {
        return new LangBuilder(CreateSifter.MODID);
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }

}
