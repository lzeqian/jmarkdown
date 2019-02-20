package io.github.jiaozi789.obj;
import lombok.Data;
import lombok.Data;

/**
 * 所有md对象拥有的公共属性
 * @Author 廖敏
 * @Date 2019-02-20 10:21
 **/
@Data
public class MdObj {
    /**
     * 开始的索引下标，包含当前索引
     */
    private int startOffset;
    /**
     * 结束的索引下标，不包含当前索引
     * startOffset<=当前字符位置范围<endOffset
     */
    private int endOffset;
}