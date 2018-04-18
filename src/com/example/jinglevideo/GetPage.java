package com.example.jinglevideo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPage {
    String filmName;

    public GetPage(String filmName) {
        // TODO Auto-generated constructor stub
        this.filmName = filmName;
    }

    public List<Map<String, String>> get_iqiyiLinks() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect(
                "http://so.iqiyi.com/so/q_" + filmName).get();

        Elements links = document.select("a.info_play_btn");
        Elements titles = document.select("a[data-playsrc-elem=title]");
        for (int i = 0; i < links.size(); i++) {
            String title = titles.get(i).attr("title");
            String link = links.get(i).attr("href");
            Map<String, String> map = new HashMap<>();
            map.put("title", title);
            map.put("url", link);
            linkList.add(map);
        }

        return linkList;
    }

    public List<Map<String, String>> get_leshiLinks() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect("http://so.le.com/s?wd=" + filmName)
                .get();

        Elements links = document.select("a.img-w180");
        links.remove(0);
        for (Element element : links) {
            String title = element.attr("title");
            String link = element.attr("href");
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title);
            map.put("url", link);
            linkList.add(map);
        }
        return linkList;
    }

    public List<Map<String, String>> get_youkuLinks() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect(
                "http://www.soku.com/search_video/q_" + filmName).get();

        Elements links = document.select("a.btn_play");
        for (Element element : links) {
            String title = element.attr("_log_title");
            String link = "http:" + element.attr("href");
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title);
            map.put("url", link);
            linkList.add(map);
        }
        return linkList;
    }

    public List<Map<String, String>> get_mangguoLinks() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect(
                "http://so.mgtv.com/so/k-" + filmName).get();

        Elements links = document.select("a[click-cid]:has(img)");
        for (Element element : links) {
            //aԪ���µĵ�һ��imgԪ�ص�alt
            String title = element.getElementsByTag("img").get(0).attr("alt");
            String link = element.attr("href");
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title);
            map.put("url", link);
            linkList.add(map);
        }
        return linkList;
    }

    public List<Map<String, String>> getLink() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect(
                "http://www.baocaibt.net/search/" + filmName).get();

        Elements links = document.select("a[href^='/hash']");
        for (Element element : links) {
            if (element.attr("title").contains(filmName)) {
                String hrefString = element.attr("href");
                int indexBegin = hrefString.lastIndexOf("/");
                //int indexEnd = hrefString.lastIndexOf(".");
                String link = hrefString.substring(indexBegin + 1);
                Map<String, String> map = new HashMap<String, String>();

                String title = element.attr("title");
                map.put("title", title);
                map.put("url", "magnet:?xt=urn:btih:" + link);
                linkList.add(map);
            }

        }
        return linkList;
    }

    public List<Map<String, String>> get_tencentLinks() throws IOException {
        List<Map<String, String>> linkList = new ArrayList<>();

        Document document = Jsoup.connect(
                "https://v.qq.com/x/search/?q=" + filmName).get();

        Elements links = document.select("div._infos");
        for (Element element : links) {
            Element child = element.child(0);
            Element grandChild = child.child(0);
            Element greatGrandChild = grandChild.child(0);
            String link = grandChild.absUrl("href");
            String title = greatGrandChild.attr("alt");
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title);
            map.put("url", link);
            linkList.add(map);
        }
        return linkList;

    }
}
