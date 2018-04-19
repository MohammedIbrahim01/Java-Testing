package ABC;

import java.lang.management.ManagementFactory;

/**
 *
 * @author khalil2535
 */
public class StringVsStringBuilder {

    public static void main(String[] args) {
        int max = 100_000;
        System.out.println("*** Check contact " + max + " Times ***");
        System.out.println("String :-");
        measureUsage(() -> {
            String x = "";
            for (int i = 0; i < max; i++) {
                x += "x";
            }
        });
        System.out.println("StringBuilder :-");
        measureUsage(() -> {
            StringBuilder x = new StringBuilder();
            for (int i = 0; i < max; i++) {
                x.append("x");
            }
        });
        System.out.println("");
        System.out.println("*** Check declare " + max + " Times ***");

        System.out.println("String :-");
        measureUsage(() -> {
            String x;
            for (int i = 0; i < max; i++) {
                x = "";
            }
        });
        System.out.println("StringBuilder :-");
        measureUsage(() -> {
            StringBuilder x;
            for (int i = 0; i < max; i++) {
                x = new StringBuilder();
            }
        });

    }

    public static void measureUsage(Runnable x) {
        long x1 = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed() / 1000000;
        long x2 = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1000000;
        long time = System.currentTimeMillis();
        x.run();
        System.out.println("Time :" + (System.currentTimeMillis() - time) / 1_000 + " Seconds");
        System.out.println("Non Heap: " + ((ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed() / 1000000) - x1));
        System.out.println("Heap: " + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1000000 - x2));
    }
}
