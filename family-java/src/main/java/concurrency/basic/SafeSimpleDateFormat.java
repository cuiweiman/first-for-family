package concurrency.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: SimpleDateFormat 的非线程安全问题
 * @author: cuiweiman
 * @date: 2024/3/11 15:43
 */
public class SafeSimpleDateFormat {
    public static void main(String[] args) {
        // parse();
        // parseSafe();
        // format();
        formatSafe();
    }

    public static void parse() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    Date parse = null;
                    try {
                        parse = sdf.parse("20240311");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(parse);
                }
            }).start();
        }
    }

    public static void parseSafe() {
        ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(()
                -> new SimpleDateFormat("yyyyMMdd"));
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    Date parse = null;
                    try {
                        parse = sdf.get().parse("20240311");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(parse);
                }
            }).start();
        }
    }

    public static void format() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    String date = sdf.format(new Date());
                    System.out.println(date);
                }
            }).start();
        }
    }

    public static void formatSafe() {
        ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(()
                -> new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss'Z'"));
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    String date = sdf.get().format(new Date());
                    System.out.println(date);
                }
            }).start();
        }
    }
}
