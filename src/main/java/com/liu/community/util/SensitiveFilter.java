package com.liu.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: community
 * @Package: com.liu.community.util
 * @ClassName: SensitiveFilter
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/24 14:51
 * @Version: 1.0
 */
@Component
public class SensitiveFilter {
    private final static Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    private final static String REPLACEMENT = "**";

    private TrieNode rootNode = new TrieNode();//创建字典树的根节点；后面创建了整棵树；

    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
                ){
            String keyword;
            while ((keyword = reader.readLine()) != null){
                //添加到前缀树
                this.addKeyword(keyword);
            }

        }catch (IOException e){
            logger.error("加载敏感词失败："+e.getMessage());
        }
    }

    //过滤敏感词，输入的是待过滤的字符串；输出的是过滤后（替换）的字符串
    public String filter(String txt){
        if(StringUtils.isBlank(txt)){
            return null;
        }
        //
        int left = 0;
        int right = 0;

        TrieNode tempNode = rootNode;
        StringBuilder sb = new StringBuilder();

        while (right <= txt.length()-1){
            char c  = txt.charAt(right);
            if(isSymbol(c)){//跳过符号
                if(tempNode == rootNode){
                    sb.append(c);
                    left++;
                }
                right++;
                continue;
            }
            tempNode =tempNode.getSubNode(c);
            if(tempNode == null){
                sb.append(txt.charAt(left));
                left++;
                right = left;
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd()){
                sb.append(REPLACEMENT);//替换
                left = ++right;
                tempNode = rootNode;
            }else {//检查下一个字符
                right++;
            }
        }
        sb.append(txt.substring(left));
        return sb.toString();
    }

    //判断一个字符是否为符号
    public boolean isSymbol(Character c){
        //0x2E80~0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c<0x2E80 || c>0x9FFF);
    }

    public void addKeyword(String keyword){
        TrieNode tempNode = rootNode;//当前节点
        for(int i= 0; i<= keyword.length()-1; i++){//遍历将节点加入字典树中
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);//得到当前节点的下级节点
            if(subNode == null){//判断其是否为空；为空就新建一个；不为空就不用新建了
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }
            tempNode = subNode;//指向下一个节点

            if(i == keyword.length()-1){//最后一个字符了，设置最后的标志位为true
                tempNode.setKeywordEnd(true);
            }

        }
    }

    private class TrieNode{

        private boolean isKeywordEnd = false;
        //key为下一个字符；value为下一个节点
        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public void addSubNode(Character c, TrieNode node){
            subNodes.put(c,node);
        }
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }

    }
}
