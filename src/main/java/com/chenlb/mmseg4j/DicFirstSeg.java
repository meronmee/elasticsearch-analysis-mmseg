package com.chenlb.mmseg4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 词典优先分词
 * 在最多分词方式的基础上优先使用自定义的words-first.dic词库.
 * 
 * @author meron 2017-07-31 14:44:18
 */
public class DicFirstSeg extends MaxWordSeg {	
	FirstDictionary firstDic = null;//words-first.dic词库
	
	public DicFirstSeg(Dictionary dic) {
		super(dic);		
		firstDic = FirstDictionary.getInstance(dic.getDicPath());;
	}

	/**
	 * 使用扩展的算法对句子 sen 进行分词.
	 * @param sen 整句经标点符号等初步分割后的小句子
	 */
	@Override
	public Chunk fullSenSeg(Sentence sen) {		
		//没有words-first.dic词库，直接使用MaxWordSeg结果
		if(firstDic == null || !firstDic.isValid()){
			return null;
		}

		List<Word> cks = new ArrayList<Word>();
		
		//基于words-first.dic词库分词	
		int maxWordLen = firstDic.getMaxWordLen();//词库中最大词长
		int senStartOffset = sen.getStartOffset();
		char[] chs = sen.getText();	
		//System.out.print("[");System.out.print(chs);System.out.println("]");
		
		//以chs中每一个char做为开头，向后匹配
		for(int offset=0; offset<chs.length-1; offset++){
			for(int len=1; len<(chs.length-offset) && len<maxWordLen; len++){
				String thisSen = subStr(chs, offset, len);
				if(firstDic.match(thisSen)){
					Word word = new Word(chs, senStartOffset, offset, len+1);
					//System.out.println("hit:"+thisSen+", word:"+word.getString());
					cks.add(word);
				}				
			}
		}
		
		Chunk chunk = new Chunk();		
		chunk.words = cks.toArray(new Word[cks.size()]);
		chunk.count = cks.size();
				
		return chunk;
	}
	
	/**从字符数组chs中offset位置开始截取len长度的字符串*/
	private String subStr(char[] chs, int offset, int len){
		int maxLen  = chs.length - 1 - offset;
		len = len>maxLen ? maxLen : len;

		StringBuilder sb = new StringBuilder().append(chs[offset]);
		for(int i=1; i<len+1; i++){
			sb.append(chs[offset+i]);
		}
		return sb.toString();
	}

}
