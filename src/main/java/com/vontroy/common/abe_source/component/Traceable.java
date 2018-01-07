package com.vontroy.common.abe_source.component;

import com.vontroy.common.abe_source.abe.Key;

public interface Traceable {

	boolean trace(Key secretKey, Key publicKey, String ID);
}
