package com.apptrovertscube.manojprabahar.myapplication.model;

import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;

/**
 * Created by Artem Kholodnyi on 9/6/16.
 */
public class TrackParseFile implements Comparable<TrackParseFile>, Serializable {
    private String trackName;
    private ParseFile album;
    private String artist;

    public TrackParseFile(String trackName, String artist,  ParseFile album) {
        this.trackName = trackName;
        this.artist = artist;
        this.album = album;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public ParseFile getAlbum() {
        return album;
    }

    public void setAlbum(ParseFile album) {
        this.album = album;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
//        stream.defaultWriteObject();
        if(getAlbum()!=null){
            String url = "";
            url = getAlbum().getUrl();
            Uri imageuri  = Uri.parse(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis;
            try {
                fis = new FileInputStream(new File(imageuri.getPath()));
                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf)))
                    baos.write(buf, 0, n);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] bbytes = baos.toByteArray();
            stream.writeObject(bbytes);
        }

    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
        byte[] image = (byte[]) stream.readObject();
        if(image != null && image.length > 0) {
            album = new ParseFile("image.jpg", image);
        }
            //album = (ParseFile) stream.readObject();
    }

    @Override
    public int compareTo(TrackParseFile track) {
        return trackName.compareTo(track.trackName);
    }
//https://stackoverflow.com/questions/8955034/how-to-fix-a-java-io-notserializableexception-android-graphics-bitmap
    //https://stackoverflow.com/questions/12597613/onactivityresult-uri-to-byte-array
}
