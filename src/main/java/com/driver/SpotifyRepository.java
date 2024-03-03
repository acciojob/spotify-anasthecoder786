package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user = new User(name, mobile);
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist artist = new Artist(artistName);
        Album album = new Album(title);
        albums.add(album);
        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception {
        Album album = new Album(albumName);
        Song song = new Song(title, length);
        songs.add(song);
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user = new User("Mayank", mobile);
        Playlist playlist = new Playlist(title);
        playlists.add(playlist);
        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user = new User("Bilal", mobile);
        Playlist playlist = new Playlist(title);
        playlists.add(playlist);
        return playlist;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user = new User("Anas", mobile);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(playlistTitle)) {
                playlist.setTitle(playlist.getTitle());
                return playlist;
            }
        }
        throw new Exception("Playlist not found");
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        User user = new User("Ashish", mobile);
        for (Song song : songs) {
            if (song.getTitle().equals(songTitle)) {
                songLikeMap.computeIfAbsent(song, k -> new ArrayList<>()).add(user);
                return song;
            }
        }
        throw new Exception("Song not found");
    }

    public String mostPopularArtist() {
        Map.Entry<Artist, List<Album>> mostPopularEntry = null;
        int maxAlbums = 0;

        for (Map.Entry<Artist, List<Album>> entry : artistAlbumMap.entrySet()) {
            if (entry.getValue().size() > maxAlbums) {
                maxAlbums = entry.getValue().size();
                mostPopularEntry = entry;
            }
        }

        return mostPopularEntry != null ? mostPopularEntry.getKey().getName() : "";
    }

    public String mostPopularSong() {
        Map.Entry<Song, List<User>> mostPopularEntry = null;
        int maxLikes = 0;

        for (Map.Entry<Song, List<User>> entry : songLikeMap.entrySet()) {
            if (entry.getValue().size() > maxLikes) {
                maxLikes = entry.getValue().size();
                mostPopularEntry = entry;
            }
        }

        return mostPopularEntry != null ? mostPopularEntry.getKey().getTitle() : "";
    }
}

