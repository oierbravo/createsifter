package com.oierbravo.createsifter.foundation.util;

import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModLang extends Lang {
    public ModLang() {
        super();
    }
    public static LangBuilder builder() {
        return new LangBuilder(MODID);
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }

}
