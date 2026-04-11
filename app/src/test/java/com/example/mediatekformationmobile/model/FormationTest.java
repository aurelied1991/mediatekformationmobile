package com.example.mediatekformationmobile.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class FormationTest {
    private Formation formation;
    private Date dateTest;

    @Before
    public void setUp() {
        dateTest = new Date();
        formation = new Formation(
                1,
                10,
                dateTest,
                "JUnit Android",
                "Formation de test",
                "abc123"
        );
    }

    @Test
    public void testGetId() {
        assertEquals(1, formation.getId());
    }

    @Test
    public void testGetPlaylistId() {
        assertEquals(10, formation.getPlaylistId());
    }

    @Test
    public void testGetPublishedAt() {
        assertEquals(dateTest, formation.getPublishedAt());
    }

    @Test
    public void testGetTitle() {
        assertEquals("JUnit Android", formation.getTitle());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Formation de test", formation.getDescription());
    }

    @Test
    public void testGetVideoId() {
        assertEquals("abc123", formation.getVideoId());
    }

    @Test
    public void testGetMiniature() {
        assertEquals(
                "https://i.ytimg.com/vi/abc123/default.jpg",
                formation.getMiniature()
        );
    }

    @Test
    public void testGetPicture() {
        assertEquals(
                "https://i.ytimg.com/vi/abc123/mqdefault.jpg",
                formation.getPicture()
        );
    }
}
