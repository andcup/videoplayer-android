package com.qioq.android.lib.video.plugins.subtitle.core;


import com.qioq.android.lib.video.plugins.subtitle.model.SubtitleEntry;

import java.io.*;
import java.util.TreeMap;

/**
 * Created by Amos on 14-11-14.
 */
public class SubtitleParser {

    public static TreeMap<Integer, SubtitleEntry> parseSrt(String srtPath) {

        TreeMap<Integer, SubtitleEntry> srt_map = null;
        FileInputStream inputStream = null;
        String encoding = judgeFileEncoding(srtPath);
        try {
            inputStream = new FileInputStream(srtPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, encoding));
            String line = null;
            srt_map = new TreeMap<Integer, SubtitleEntry>();
            StringBuffer sb = new StringBuffer();
            int key = 0;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    sb.append(line).append("@");
                    continue;
                }

                String[] parseStrs = sb.toString().split("@");
                // 该if为了适应一开始就有空行以及其他不符格式的空行情况
                if (parseStrs.length < 3) {
                    sb.delete(0, sb.length());// 清空，否则影响下一个字幕元素的解析
                    continue;
                }

                SubtitleEntry srt = new SubtitleEntry();
                // 解析开始和结束时间
                String timeTotime = parseStrs[1];
                int begin_hour = Integer.parseInt(timeTotime.substring(0, 2));
                int begin_mintue = Integer.parseInt(timeTotime.substring(3, 5));
                int begin_scend = Integer.parseInt(timeTotime.substring(6, 8));
                int begin_milli = Integer.parseInt(timeTotime.substring(9, 12));
                int beginTime = (begin_hour * 3600 + begin_mintue * 60 + begin_scend) * 1000 + begin_milli;
                int end_hour = Integer.parseInt(timeTotime.substring(17, 19));
                int end_mintue = Integer.parseInt(timeTotime.substring(20, 22));
                int end_scend = Integer.parseInt(timeTotime.substring(23, 25));
                int end_milli = Integer.parseInt(timeTotime.substring(26, 29));
                int endTime = (end_hour * 3600 + end_mintue * 60 + end_scend) * 1000 + end_milli;
                String srtBody = "";
                for (int i = 2; i < parseStrs.length; i++) {
                    srtBody += parseStrs[i] + "\n";
                }
                srtBody = srtBody.substring(0, srtBody.length() - 1);
                srt.setBeginTime(beginTime);
                srt.setEndTime(endTime);
                srt.setSrtBody(new String(srtBody.getBytes(), "UTF-8"));
                srt_map.put(key, srt);
                key++;
                sb.delete(0, sb.length());// 清空，否则影响下一个字幕元素的解析
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srt_map;
    }

    public static String judgeFileEncoding(String path){
        FileInputStream inputStream;
        String encoding = "UTF-8";
        try {
            inputStream = new FileInputStream(path);
            BufferedInputStream bin = new BufferedInputStream(inputStream);
            int p = (bin.read() << 8) + bin.read();
            switch (p) {
                case 0xfffe:
                    encoding = "Unicode";
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return encoding;
    }
}
