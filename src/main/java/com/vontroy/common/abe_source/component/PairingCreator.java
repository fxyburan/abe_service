package com.vontroy.common.abe_source.component;

import com.vontroy.common.abe_source.utils.PairingManager;

import java.io.InputStream;

public class PairingCreator {

    public static void init() {
        InputStream is = PairingCreator.class.getResourceAsStream("/abe/a.properties");
        PairingManager.createPairing(is);
    }
}
