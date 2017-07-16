package com.example.andrey.newtmpclient.managers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class TasksManagerTest {
    @Before
    public void init() {

    }

    TasksManager tasksManager = TasksManager.INSTANCE;

    @Test
    public void addUnique() throws Exception {


    }

    @Test
    public void result(){
        int result = tasksManager.result(20, 30);
        assertEquals(600, result);
    }
}