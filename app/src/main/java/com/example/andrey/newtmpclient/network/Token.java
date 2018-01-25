package com.example.andrey.newtmpclient.network;

import java.util.UUID;

public class Token {
    private UUID token;

    public Token(){
        this.token = UUID.randomUUID();
    }
}
