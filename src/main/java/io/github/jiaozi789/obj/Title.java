package io.github.jiaozi789.obj;

import lombok.Data;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:21
 **/
@Data
public class Title extends MdObj {
    private int level;
    private String innerText;
}