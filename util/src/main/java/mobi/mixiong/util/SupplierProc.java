package mobi.mixiong.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SupplierProc {


    @FunctionalInterface
    public interface IProc {
        void execute() throws Throwable;
    }

    @FunctionalInterface
    public interface IResultProc<T> {
        T execute() throws Throwable;
    }

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static CompletableFuture<Void> submitTask(String name, IProc function) {
        String traceId = MDC.get("traceId");
        return CompletableFuture.runAsync(() -> {
            MDC.put("traceId", traceId);
            try {
                function.execute();
            } catch (Throwable e) {
                log.error(name + " execute fail :", e);
            }
        }, executorService);
    }

    public static <T> CompletableFuture<T> submitTask(String name, IResultProc<T> function) {
        String traceId = MDC.get("traceId");
        return CompletableFuture.supplyAsync(() -> {
            MDC.put("traceId", traceId);
            try {
                return function.execute();
            } catch (Throwable e) {
                log.error(name + " execute fail :", e);
                return null;
            }
        }, executorService);
    }

    public static List<?> submitTasks(IResultProc<?>... functions) {
        String traceId = MDC.get("traceId");
        CompletableFuture<?>[] inputs = Stream.of(functions).map(function -> CompletableFuture.supplyAsync(() -> {
            try {
                MDC.put("traceId", traceId);
                return function.execute();
            } catch (Throwable e) {
                log.error("execute fail :", e);
                return null;
            }
        }, executorService)).collect(Collectors.toList()).toArray(new CompletableFuture<?>[]{});

        CompletableFuture resultFuture = CompletableFuture.allOf(inputs);
        try {
            resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("wait result catch exception.", e);
        } finally {
            resultFuture.cancel(true);
        }

        return Stream.of(inputs).map(x -> {
            try {
                return x.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("wait result catch exception.", e);
                return null;
            }
        }).collect(Collectors.toList());
    }

    public static void submitTasks(IProc... functions) {
        String traceId = MDC.get("traceId");
        CompletableFuture<Void>[] inputs = Stream.of(functions).map(function ->
                CompletableFuture.runAsync(() -> {
                    MDC.put("traceId", traceId);
                    try {
                        function.execute();
                    } catch (Throwable e) {
                        log.error("execute fail :", e);
                    }
                }, executorService))
                .collect(Collectors.toList()).toArray(new CompletableFuture[]{});

        CompletableFuture resultFuture = CompletableFuture.allOf(inputs);
        try {
            resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("wait result catch exception.", e);
        } finally {
            resultFuture.cancel(true);
        }
    }

    public static <T> CompletableFuture<T> submitTask(String name, IResultProc<T> function, ExecutorService
            executorService) {
        String traceId = MDC.get("traceId");
        return CompletableFuture.supplyAsync(() -> {
            MDC.put("traceId", traceId);
            try {
                return function.execute();
            } catch (Throwable e) {
                log.error(name + " execute fail :", e);
                return null;
            }
        }, executorService);
    }


    public static CompletableFuture<Void> submitTask(String name, IProc function, ExecutorService executorService) {
        String traceId = MDC.get("traceId");
        return CompletableFuture.runAsync(() -> {
            MDC.put("traceId", traceId);
            try {
                function.execute();
            } catch (Throwable e) {
                log.error(name + " execute fail :", e);
            }
        }, executorService);
    }

    public static void waitResult(CompletableFuture... inputs) {
        CompletableFuture resultFuture = CompletableFuture.allOf(inputs);
        try {
            resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("wait result catch exception.", e);
        } finally {
            resultFuture.cancel(true);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture one = SupplierProc.submitTask("one", () -> {
//            System.out.println("start");
//            Thread.sleep(1000);
//            System.out.println("pass");
//            return "1";
//        });
//
//        CompletableFuture two = SupplierProc.submitTask("one", () -> {
//            System.out.println("start");
//            Thread.sleep(1000);
//            System.out.println("pass");
//            return "2";
//        });
//
//        CompletableFuture three = SupplierProc.submitTask("one", () -> {
//            System.out.println("start");
//            Thread.sleep(4000);
//            System.out.println("pass");
//            return "3";
//        });
//
//        CompletableFuture four = SupplierProc.submitTask("one", () -> {
//            System.out.println("start");
//            Thread.sleep(1000);
//            System.out.println("pass");
//            return "4";
//        });
//
//        CompletableFuture<Void> five = SupplierProc.submitTask("one", () -> {
//            System.out.println("start");
//            Thread.sleep(4000);
//            System.out.println("pass");
//        });
//        System.out.println(one.get());
//
//        SupplierProc.waitResult(one, two, three, four, five);
//        System.out.println(one.get());
//        System.out.println(five.get());

        long start = System.currentTimeMillis();
        System.out.println("start-----" + start);
        List list = SupplierProc.submitTasks(() -> {
                    System.out.println("start");
                    Thread.sleep(1000);
                    System.out.println("pass");
                    return 1;
                }, () -> {
                    System.out.println("start");
                    Thread.sleep(1000);
                    System.out.println("pass");
                    return "2";
                }, () -> {
                    System.out.println("start");
                    Thread.sleep(1000);
                    System.out.println("pass");
                    return "3";
                }, () -> {
                    System.out.println("start");
                    Thread.sleep(4000);
                    System.out.println("pass");
                    return "4";
                },
                () -> {
                    System.out.println("start");
                    Thread.sleep(4000);
                    System.out.println("pass");
                    return null;
                }, () -> {
                    System.out.println("start");
                    Thread.sleep(4000);
                    System.out.println("pass");
                    throw new IllegalArgumentException();
                });
        Long a = (Long) list.get(4);
        System.out.println(a);
        long time = (System.currentTimeMillis() - start) / 1000;
        System.out.println("end--" + time);
    }
}
