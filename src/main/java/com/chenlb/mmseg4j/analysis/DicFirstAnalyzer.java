package com.chenlb.mmseg4j.analysis;

import java.io.File;

import com.chenlb.mmseg4j.DicFirstSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.Seg;


/**
 * 词典优先分词
 * 在最多分词方式的基础上优先使用自定义的words-first.dic词库.
 * 
 * @author meron 2017-07-31 14:44:18
 */
public class DicFirstAnalyzer extends MMSegAnalyzer {

	public DicFirstAnalyzer() {
		super();
	}

	public DicFirstAnalyzer(String path) {
		super(path);
	}

	public DicFirstAnalyzer(Dictionary dic) {
		super(dic);
	}

	public DicFirstAnalyzer(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new DicFirstSeg(dic);
	}
}
