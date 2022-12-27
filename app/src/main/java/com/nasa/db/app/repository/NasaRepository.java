package com.nasa.db.app.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nasa.db.app.R;
import com.nasa.db.app.model.NasaDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NasaRepository {
    private final Application application;

    public NasaRepository(Application app) {
        application = app;
    }

    public MutableLiveData<ArrayList<NasaDTO>> getNasaGallery() {
        MutableLiveData<ArrayList<NasaDTO>> data = new MutableLiveData<>();
        InputStream is = application.getApplicationContext().getResources().openRawResource(R.raw.nasa);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonStr = writer.toString();
        Type type = new TypeToken<List<NasaDTO>>(){}.getType();
        data.setValue(new Gson().fromJson(jsonStr, type));

        return data;
    }
}
