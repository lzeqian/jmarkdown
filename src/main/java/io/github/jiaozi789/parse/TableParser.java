package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.StringUtils;
import io.github.jiaozi789.utils.SystemUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-26 17:14
 **/
public class TableParser extends SeriesMulLineParser {
    @Override
    public String startChar() {
        return "|";
    }

    @Override
    public int seriesCount() {
        return 2;
    }

    @Override
    public Map<Integer, String> regex() {
        Map<Integer, String> regexRule=new HashMap<>();
        regexRule.put(0,"(\\|.+)+\\|$");
        regexRule.put(1,"(\\|.+)+\\|$");
        return regexRule;
    }
    private int columnCount;
    @Override
    public boolean check(int index, MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            //表头行
            if(index==0){
                columnCount=StringUtils.findCount(string,"|")-1;
                return true;
                //对齐行
            }else if(index==1){
                int endIndex= StringUtils.indexOf(string,startChar(),columnCount+1);
                if(endIndex==-1){
                    return false;
                }else{
                    //每一个 ||之间必须要有 :开头或者结尾
                    int findCount=StringUtils.findCount(string,"|");
                    for(int i=1;i<=findCount;i++){
                        int startIndex1= StringUtils.indexOf(string,startChar(),i);
                        int endIndex1= StringUtils.indexOf(string,startChar(),i+1);
                        if(endIndex1!=-1){
                            if(!string.substring(startIndex1,endIndex1+1).contains("-")){
                                return false;
                            }
                        }
                    }

                    reader.replaceCurRow(string.substring(0,endIndex+1)+System.lineSeparator());
                    return true;
                }
            }else{
                //数据行
                int endIndex= StringUtils.indexOf(string,startChar(),columnCount+1);
                if(endIndex==-1){
                    int findCount=StringUtils.findCount(string,startChar());
                    for(int i=0;i<(columnCount+1-findCount);i++){
                        string=string+"|";
                    }
                    reader.replaceCurRow(string.substring(0,endIndex+1)+System.lineSeparator());
                }else{
                    reader.replaceCurRow(string.substring(0,endIndex+1)+System.lineSeparator());
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 没有: 替换成
     * <table>
     * <thead>
     * <tr>
     * <th>a</th>
     * <th>v</th>
     * </tr>
     * </thead>
     * <tbody></tbody>
     * </table>
     * 有左右中 加上
     * <td align="left">a</td>
     * <td align="right">b</td>
     * <td align="center">c</td>
     *
     *
     * @param reader 当前行的读取流
     * @return 替换后的字符串
     */
    @Override
    public String replace(MarkDownReader reader) {
        StringBuffer targetHtml=new StringBuffer("<table><thead>");
        try {
            String string = reader.readChar(super.blockStartIdx, super.blockEndIdx);
            MarkDownReader stringReader=new MarkDownReader(string);
            String line=null;
            while((line=stringReader.readMdLine())!=null){
                String line2=null;
                if(stringReader.getCurRowIdx()==0){
                    line2=stringReader.readMdLine();
                }
                int findCount=StringUtils.findCount(line,startChar());
                targetHtml.append("<tr>");

                //每一行找列
                for(int i=1;i<=findCount;i++){
                    int startIndex1= StringUtils.indexOf(line,startChar(),i);
                    int endIndex1= StringUtils.indexOf(line,startChar(),i+1);

                    if(endIndex1!=-1){
                        String tdHtml = line.substring(startIndex1+1, endIndex1).trim();
                        if(stringReader.getCurRowIdx()==1) {
                            targetHtml.append("<th ");
                            int startIndex2= StringUtils.indexOf(line2,startChar(),i);
                            int endIndex2= StringUtils.indexOf(line2,startChar(),i+1);
                            String tdHtml2= line2.substring(startIndex2+1, endIndex2).trim();
                            //左边对齐
                            if(tdHtml2.startsWith(":") && !tdHtml2.endsWith(":")){
                                targetHtml.append(" align=\"left\" ");
                            }
                            //右对齐
                            else if(!tdHtml2.startsWith(":") && tdHtml2.endsWith(":")){
                                targetHtml.append(" align=\"right\" ");
                            }else{
                                //居中对齐
                                targetHtml.append(" align=\"center\" ");
                            }
                            targetHtml.append(">" + tdHtml.trim() + "</th>");
                        }else{
                            targetHtml.append("<td>"+tdHtml.trim()+"</td> ");
                        }

                    }
                }
                targetHtml.append("<tr/>");
                if(stringReader.getCurRowIdx()==1){
                    targetHtml.append("</thead><tbody>");
                }

            }
            targetHtml.append("</tbody></table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetHtml.toString();
    }
}
