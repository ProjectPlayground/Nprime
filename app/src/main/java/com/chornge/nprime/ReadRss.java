package com.chornge.nprime;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class ReadRss extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private String webAddressForRssFeed = "http://namchouston.org/feed";
    private URL url;

    ReadRss(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(getData());
        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            ArrayList<FeedItem> feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);

                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemChilds = currentChild.getChildNodes();

                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);

                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        }
                    }

                    feedItems.add(item);
                    Log.d("itemTitle", item.getTitle());
                    Log.d("itemDescription", item.getDescription());
                    Log.d("itemPubDate", item.getPubDate());
                    Log.d("itemLink", item.getLink());

                }
            }
        }
    }

    private Document getData() {
        try {
            url = new URL(webAddressForRssFeed);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder.parse(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
