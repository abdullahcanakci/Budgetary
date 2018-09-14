package com.example.abdullah.budgetary;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Xml;

import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.utilities.AppExecutors;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class DrawableXmlHandler {
    MutableLiveData<ArrayList<Icon>> drawables = new MutableLiveData<ArrayList<Icon>>();
    private int version;

    public DrawableXmlHandler(Context context, int drawablesVersion) {
        if(isParsingRequired(drawablesVersion)) {
            AppExecutors.getInstance().diskIO().execute(() -> {
                parseXml(context);
            });
        }
    }

    public MutableLiveData<ArrayList<Icon>> getDrawables() {
        return drawables;
    }

    private boolean isParsingRequired(int drawablesVersion) {
        return true;
        //TODO parse drawables file version from xml and check against provided one.
        //TODO if file is newer return true else false
    }

    private void parseXml(Context context) {

        InputStream stream = getFileStream(context);

        try {
            if(stream != null) {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();
                readFeed(parser);
            }
        }catch (XmlPullParserException | IOException ex){
            ex.printStackTrace();
        } finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "drawables");
        ArrayList<Icon> icons = new ArrayList<>();
        while(parser.next() != XmlPullParser.END_DOCUMENT){
            if(parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if(name.equals("icon")) {
                icons.add(readIcon(parser));
            }else{
                skip(parser);
            }
        }
        drawables.postValue(icons);

    }

    private Icon readIcon(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "icon");

        String name = null;
        String description = null;
        int id = -1;

        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String n = parser.getName();
            if(n.equals("name")){
                name = readName(parser);
            } else if (n.equals("description")){
                description = readDescription(parser);
            } else if(n.equals("id")){
                id  = readId(parser);
            } else{
                skip(parser);
            }
        }
        return new Icon(id, name, description);
    }

    private String readName(XmlPullParser parser)   throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "name");
        return name;
    }

    private String readDescription(XmlPullParser parser)   throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "description");
        return description;
    }

    private int readId(XmlPullParser parser)  throws  IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "id");
        int id = readInt(parser);
        parser.require(XmlPullParser.END_TAG, null, "id");
        return id;
    }

    private String readText(XmlPullParser parser) throws  IOException, XmlPullParserException {
        String result = "";
        if(parser.next() == XmlPullParser.TEXT){
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private int readInt(XmlPullParser parser) throws  IOException, XmlPullParserException {
        int result = -1;
        if(parser.next() == XmlPullParser.TEXT){
            result = Integer.parseInt(parser.getText());
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws  IOException, XmlPullParserException {
        if(parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private InputStream getFileStream(Context c) {
        InputStream stream = null;
        try {
            stream = c.getAssets().open("drawables.xml");
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return stream;
    }
}
