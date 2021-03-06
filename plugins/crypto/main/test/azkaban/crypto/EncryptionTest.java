/*
 * Copyright (C) 2016 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */
package azkaban.crypto;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import junit.framework.Assert;

public class EncryptionTest {

  @Test
  public void testEncryption() {
    String plainText = "test";
    String passphrase = "test1234";

    ICrypto crypto = new Crypto();

    for (Version ver : Version.values()) {
      String cipheredText = crypto.encrypt(plainText, passphrase, ver);
      Assert.assertEquals(plainText, crypto.decrypt(cipheredText, passphrase));
    }
  }

  @Test
  public void testInvalidParams() {
    ICrypto crypto = new Crypto();
    String[] args = {"", null, "test"};
    for (Version ver : Version.values()) {
        for (String plaintext : args) {
          for (String passphrase : args) {
            try {
              if (!StringUtils.isEmpty(plaintext) && !StringUtils.isEmpty(passphrase)) {
                String cipheredText = crypto.encrypt(plaintext, passphrase, ver);
                Assert.assertEquals(plaintext, crypto.decrypt(cipheredText, passphrase));
              } else {
                crypto.encrypt(plaintext, passphrase, ver);
                Assert.fail("Encyption should have failed with invalid parameters. plaintext: "
                             + plaintext + " , passphrase: " + passphrase);
              }
            } catch (Exception e) {
              Assert.assertTrue(e instanceof IllegalArgumentException);
            }

          }
        }
    }
  }

}