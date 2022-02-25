package cn.ivan.futuredemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yanqi69
 * @date 2021/7/6
 */
@Slf4j
public class GroupByTest {

    public static void main(String[] args) {

        List<Item> list = new ArrayList<>();
        list.add(new Item(34L,"test"));
        list.add(new Item(21L,"test"));
        list.add(new Item(56L,"test1"));
        list.add(new Item(109L,"test1"));
        list.add(new Item(1L,"test1"));
        list.add(new Item(90L,"test1"));
        list.add(new Item(19087L,"test1"));
        list.add(new Item(3L,"test1"));
        list.add(new Item(2L,"test2"));
        Map<String, List<Item>> collect = list.stream()
                .sorted(Comparator.comparingLong(Item::getId))
                //.collect(Collectors.groupingBy(Item::getName, LinkedHashMap::new, Collectors.toList()));
                .collect(Collectors.groupingBy(Item::getName));
        collect.forEach((k,v)->{
            log.info(" k ==== {} v ======{}",k,v.stream().map(Item::getId).collect(Collectors.toList()));
        });
        String img = "https://jch-shop.yunxiu.com/app/jch-shop/img/2021816/119328_162909154575.jpg";
        log.info("length:{}",img.length());
    }

    @Data
    @AllArgsConstructor
    static class Item{
        Long id;

        String name;
    }
}
