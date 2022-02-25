package cn.ivan.futuredemo;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @date 2021/5/20
 */
@Slf4j
public class Test {


    public static void main(String[] args) {
        TicketLock2 lock2 = new TicketLock2();

        log.info(ClassLayout.parseInstance(lock2).toPrintable());


        lock2.lock();

        log.info("get lock");

        lock2.unLock();

        Class<Test> testClass = Test.class;

        List<? super Number> a = new ArrayList<>();

        a.add(1);

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(17);

        objectObjectHashMap.put(1,2);

        log.info("{}",0&0);

        Object obj = new Object();

        log.info(ClassLayout.parseInstance(obj).toPrintable());

        MyItem myItem = new MyItem();

        log.info(ClassLayout.parseInstance(myItem).toPrintable());


        Request build = new Request.Builder().url("http://wwww.baiud.com?q=123").build();
        HttpUrl url = build.url();

        HttpUrl www = url.newBuilder().addQueryParameter("www", String.valueOf(obj)).build();


        log.info(www.toString());

        log.info(new Request.Builder().url(www).build().url().toString());

        int[] arr = {6,9,0,18,1};
        bulk(arr);
        log.info(Arrays.toString(arr));

        int s = 18;
        int binary = binary(arr, s);
        if(binary == -1){
            log.info("not found {} from arr{}",s,Arrays.toString(arr));
        }else {
            log.info("found {} from arr {}",arr[binary],Arrays.toString(arr));
        }


        log.info("======================{}",calc(1,System.currentTimeMillis()));
        log.info("======================{}",calc(2,System.currentTimeMillis()));

       long time = System.currentTimeMillis();

       int i = 0;
       while((time | 1) != 1){
           i ++;
           time = time >>> 1;
       }
        log.info("bit ====={}",i);




    }

    public static long calc(long a , long b){
        return  a << 41 | b;
    }


    static class MyItem{
        TicketLock2 lock2 = new TicketLock2();
    }

    public static void bulk(int[] arr){

        for(int i = 0; i< arr.length; i++){
            for(int j = 0;j<arr.length-i -1; j++){
                if(arr[j+1] < arr[j]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static int binary(int[] arr,int s){

        int len = arr.length;

        int min = 0;
        int max = len -1;

        int mid = 0;
        while(min <= max){
            mid = (min + max) / 2;
            if(s == arr[mid]){
                return mid;
            }
            if(s < arr[mid]){
                max = mid -1;

            }
            if(s > arr[mid]){
                min = mid + 1;
            }
        }
        return -1;
    }

    public static void quickSort(int arr[]){


    }

    public static int sort(int[] arr ,int left ,int right){
        // 基准值
        int mid = arr[left];

        while(left < right){
            // 右边比基准值小的问题
            while(left < right && arr[right] >= mid){
                right --;
            }
            // 找到了比基准值小的，将right与基准值交换
            if(left < right){
                arr[left] = arr[right];
            }
            while (left < right && arr[left] <= mid){
                left ++;
            }
            if(left < right){
                arr[right] = mid;
            }
        }
        arr[left] = mid;
        return left;
    }

}
