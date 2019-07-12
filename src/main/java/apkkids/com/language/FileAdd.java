package apkkids.com.language;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 遍历文件夹，并修改文件内容
 */
public class FileAdd {
    public static void main(String[] args) {
        int fileNum = 0, folderNum = 0;
        File file = new File(args[0]);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    fileNum++;
                    System.out.println("文件:" + file2.getAbsolutePath()+",添加了内容");
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        fileNum++;
                        addContent(file2);
                        System.out.println("文件:" + file2.getAbsolutePath()+",添加了内容");
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    }

    /**
     * @param file2
     */
    private static void addContent(File file2) {
        try {
            List<String> result = new ArrayList<String>();
            FileInputStream is = new FileInputStream(file2);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String content = null;
            while ((content = reader.readLine()) != null) {
                result.add(content);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter( new FileWriter(file2));

            //写入第一行
            writer.write("在头部添加一行文字"+"\r\n");

            //写入列表，列表是原来读入的文件
            for(String line:result) {
                writer.write(line);
                writer.write("\r\n");//写入换行符
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
