package com.example.andrey.newtmpclient.network;

import java.util.UUID;

public class Token {
    UUID token;

    public Token(){
        this.token = UUID.randomUUID();
    }
}
