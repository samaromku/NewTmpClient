package com.example.andrey.newtmpclient.managers;

import com.example.andrey.newtmpclient.network.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private Token token;
//    private boolean isFireBaseInShared = false;


//    public void setFireBaseInShared(boolean fireBaseInShared) {
//        isFireBaseInShared = fireBaseInShared;
//    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static final TokenManager instance = new TokenManager();

    private TokenManager(){
    }

}

