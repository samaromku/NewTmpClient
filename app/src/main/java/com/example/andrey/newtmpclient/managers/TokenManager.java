package com.example.andrey.newtmpclient.managers;

import com.example.andrey.newtmpclient.network.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private Token token;
    private List<Token> tokens;
    boolean isFireBaseInShared = false;

    public boolean isFireBaseInShared() {
        return isFireBaseInShared;
    }

    public void setFireBaseInShared(boolean fireBaseInShared) {
        isFireBaseInShared = fireBaseInShared;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static final TokenManager instance = new TokenManager();

    private TokenManager(){
        this.tokens = new ArrayList<>();
    }

    public void addToken(Token token){
        tokens.add(token);
    }

    public void removeToken(Token token){
        tokens.remove(token);
    }
}

