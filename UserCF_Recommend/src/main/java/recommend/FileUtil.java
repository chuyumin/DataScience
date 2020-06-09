package recommend;

import java.io.*;

public class FileUtil {
    public static String readJsonFile(String file_path) {
        String jsonstr = "";
        File jsonfile = new File(file_path);
        try {
            FileReader fileReader = new FileReader(jsonfile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonfile) , "utf-8");
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while((ch = reader.read())!= -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonstr = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonstr;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(readJsonFile("D:\\ChromeCoreDownloads\\sample.json"));
    }

}
